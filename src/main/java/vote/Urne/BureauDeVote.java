package vote.Urne;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class BureauDeVote extends Thread{
    private volatile EtatBureauDeVote etat;
    private volatile boolean signalArret;
    private ServerSocket serveur;
    private Sondage sondage;
    private Float[] resultat;
    private boolean voteOuvert;
    private ArrayList<String> votesChiffres;

    public BureauDeVote(int port) throws IOException {
        etat = new SansSondageEtat(this);
        signalArret = false;
        votesChiffres = new ArrayList<>();
        this.serveur = new ServerSocket(port);
        this.serveur.setSoTimeout(500);

        System.out.println("Serveur configuré sur le port " + port);
    }

    public void changeState(EtatBureauDeVote etat){
        this.etat = etat;
    }

    void creerSondage(Sondage sondage){
        etat.creerSondage(this,sondage);
    }
    void arreterRecolte(){
        etat.arreterRecolte(this);
    }

    void publicationResultat(){
        etat.publicationResultat(this);
    }

    void finirSondage(){
        etat.finirSondage(this);
    }

    public boolean aSondageEnCours(){
        return sondage != null;
    }

    public boolean resultatDisponible(){
        return resultat != null;
    }

    public ServerSocket getServeur(){
        return serveur;
    }

    public boolean isVoteOuvert() {
        return voteOuvert;
    }

    public void setVoteOuvert(boolean voteOuvert) {
        this.voteOuvert = voteOuvert;
    }


    public void fermerBureau(){
        signalArret = true;
    }

    public void ajouterVoteChiffre(String voteChiffre){
        votesChiffres.add(voteChiffre);
    }

    private void gerer_connexion(Socket socket){
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                Requete input = (Requete) in.readObject();

                input.repondre(this,out);

            } catch (IOException e) {
                System.out.println("Error while read bytes: " + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }


    @Override
    public void run(){
        System.out.println("Serveur en ecoute");
        while (!signalArret) {
            Socket socket = null;
            try {
                socket = serveur.accept();
                System.out.println("Un client s'est connecté");
                gerer_connexion(socket);
                socket.close();
            } catch (SocketTimeoutException e) { //Ca permet d'eviter qu'on demande la fermeture du serveur et que le thread reste bloqué sur le accept()
            } catch (IOException e) {
                System.out.println("Error while accepting connection :" + e);
            }
        }
    }

    public Sondage getSondage() {
        return sondage;
    }

    public void setSondage(Sondage sondage) {
        this.sondage = sondage;
    }

    public Float[] getResultat() {
        return resultat;
    }

    public void setResultat(Float[] resultat) {
        this.resultat = resultat;
    }
}
