package vote.Urne;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class Urne{
    private final int port;
    private volatile boolean recolteFiniFlag; //des flags qui defini l'etat du sondage, c'est utile mais peut etre qu'on pourrait implementer le design pattern etat a la place
    private volatile boolean stopTraitementFlag;
    //private volatile boolean resultatDisponible;
    private volatile Sondage sondageEnCours;
    private Thread serveurTraitement;

    private class Traitement implements Runnable{
        private final ServerSocket serveur;
        private String[] resultat;

        public Traitement() throws IOException{
            this.serveur = new ServerSocket(port);
            serveur.setSoTimeout(1000);
        }

        @Override
        public void run() {
            System.out.println("Demarrage du traitement");
            while (!stopTraitementFlag) {
                Socket socket = null;
                try {
                    socket = serveur.accept();
                    System.out.println("Un client s'est connecté");
                }
                catch (SocketTimeoutException e){ //Ca permet d'eviter qu'on demande la fermeture du serveur et que le thread reste bloqué sur le accept()
                    continue;
                }
                catch (IOException e) {
                    System.out.println("Error while accepting connection :" + e);
                    continue;
                }

                handleConnection(socket);

            }
            System.out.println("Fermeture du serveur");
        }

        private void handleConnection(Socket socket){ //to refactor
                try {
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                    System.out.println("lecture");

                    String input = in.readUTF();


                    System.out.println("recu: " + input);

                    if (input.equals("getSondage")) {
                        System.out.println("Demande du sondage actuel recu");
                        out.writeObject(sondageEnCours);
                        out.flush();
                    }
                    else if(input.startsWith("vote")) {
                            if (sondageEnCours == null) {
                                out.writeUTF("Erreur: Aucun sondage en cours");
                                out.flush();
                            }
                            else if (recolteFiniFlag) {
                                out.writeUTF("Erreur: Recolte fini");
                            }
                            else {
                                String[] splitted = input.split(" ");
                                if (splitted.length == 2) {
                                    System.out.println("Vote recu : " + splitted[1]);
                                    out.writeUTF("OK");
                                    out.flush();
                                } else {
                                    out.writeUTF("Erreur: Mauvais format");
                                    out.flush();
                                }
                            }
                        }

                        //devra repondre quand le client demande le resultat du sondage, on pourrait faire un getState ou quoi
                        else if(input.equals("getResults")) {// faire en sorte que cette commande ne soit pas disponible tant que la recolte n'est pas fini
                            System.out.println("Demande du resultat recu");
                            if(recolteFiniFlag) {
                                this.resultat = new String[]{sondageEnCours.getConsigne(), sondageEnCours.getChoix1(), sondageEnCours.getChoix2(), "1", "0"};
                                out.writeObject(resultat);
                                out.flush();
                            }
                            else {
                                out.writeObject(null);
                            }
                        }
                        else {
                            System.out.println("Requete Inconnu");
                        }

                } catch (IOException e) {
                    System.out.println("Error while read bytes: " + e);
                }
            }
        }

    public static class UrneException extends Exception {
        public UrneException(String str){
            super(str);
        }
    }

    public Urne(int port){
        this.port = port;
    }

    public void demarrerServeur() throws UrneException{
        if(serveurTraitement != null){
            throw new UrneException("Le serveur est déja demarré");
        }

        recolteFiniFlag = false;
        stopTraitementFlag = false;
        sondageEnCours = null;

        try {
            serveurTraitement = new Thread(new Traitement());

            System.out.println("Demarrage du serveur");
            serveurTraitement.start();
        }
        catch (IOException e){
            throw new UrneException(e.toString());
        }
    }

    public void lancerNouveauSondage(Sondage sondage) throws UrneException {
        if(serveurTraitement == null){
            throw new UrneException("Le serveur n'est pas allumé");
        }

        if(sondageEnCours == null) {
            sondageEnCours = sondage;
        }
        else {
            throw new UrneException("Sondage déja en cours");
        }
    }

    public void arreterRecolte() throws UrneException{
        if(serveurTraitement == null){
            throw new UrneException("Le serveur n'est pas allumé");
        }

        if(sondageEnCours == null){
            throw new UrneException("Aucun sondage en cours");
        }

        recolteFiniFlag = true;
        //a poursuivre avec le scrutateur
    }

    public void arreterServeur() throws UrneException{
        if(serveurTraitement != null) {
            stopTraitementFlag = true;
            try {
                serveurTraitement.join();
                serveurTraitement = null;
                sondageEnCours = null;
                stopTraitementFlag = false;
                recolteFiniFlag = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new UrneException("Il n'y a pas de serveur demmar");
        }
    }
}
