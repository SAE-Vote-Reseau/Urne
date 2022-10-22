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
    private Thread serveurTraitement;

    private class Traitement implements Runnable{
        private final Sondage sondageEnCours;
        private final ServerSocket serveur;

        public Traitement(Sondage sondage, int port) throws IOException{
            this.sondageEnCours = sondage;
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

        private void handleConnection(Socket socket){
                try {
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                    System.out.println("lecture");

                    String input = in.readUTF();

                    System.out.println("recu: " + input);

                    if(!recolteFiniFlag) { //il faudra faire la recolte des votes ici
                        if (input.equals("getSondage")) {
                            System.out.println("Demande du sondage actuel recu");
                            out.writeObject(sondageEnCours);
                        }
                        else {
                            System.out.println("Unknow request");
                        }
                    }
                    else {
                        //devra repondre quand le client demande le resultat du sondage, on pourrait faire un getState ou quoi
                        System.out.println("Unknow request");
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

    public Urne(int port) {
        this.port = port;
        serveurTraitement = null;
        recolteFiniFlag = false;
        stopTraitementFlag = false;
    }

    public void lancerNouveauSondage(Sondage sondage) throws UrneException {

        try {
            if (serveurTraitement == null) {
                serveurTraitement = new Thread(new Traitement(sondage, port));
                recolteFiniFlag = false;
                stopTraitementFlag = false;

                System.out.println("Demarrage du serveur");
                serveurTraitement.start();
            } else {
                throw new UrneException("Le serveur est deja en marche");
            }
        }
        catch (IOException e){
            throw new UrneException(e.toString());
        }
    }

    public void arreterRecolte(){
        recolteFiniFlag = true;
        //a poursuivre avec le scrutateur
    }

    public void arreterSondage() throws UrneException{
        if(serveurTraitement != null) {
            stopTraitementFlag = true;
            try {
                serveurTraitement.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new UrneException("Il n'y a pas de sondage en cours");
        }
    }
}
