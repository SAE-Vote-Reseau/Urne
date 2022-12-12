package vote.Urne;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;

import vote.Urne.metier.Employe;
import vote.Urne.metier.VoteManager;
import vote.crypto.Message;
import vote.crypto.ElGamal;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;


public class BureauDeVote extends Thread{
    private volatile EtatBureauDeVote etat;
    private volatile boolean signalArret;
    private ServerSocket serveur;
    private Sondage sondage;

    private boolean voteOuvert;
    private Scrutateur scrutateur;

    private Message votesChiffres;

    /**
     * @param port
     * Le variable port est définie comme le port de notre Bureau de Vote
     * @param addrScrut
     * L'adresse IP du scrutateur auxquelle on souhaite se connecter
     * @param portScrut
     *Le port de notre Scrutateur
     * Ce constructeur va
     * @throws IOException
     */
    public BureauDeVote(int port, String addrScrut, int portScrut) throws IOException {
        etat = new SansSondageEtat(this);
        signalArret = false;
        //SSLContext sslContext = SSLContextConf.setSystemPropertySSLContextServer("SAEKeyStore.jks","auuuugh");
        this.serveur = new ServerSocket(port);
        this.serveur.setSoTimeout(500);

        this.scrutateur = new Scrutateur(addrScrut,portScrut);


        System.out.println("Serveur configuré sur le port " + port);
        System.out.println("Scrutateur configuré sur le port " + scrutateur.getPort() + " et l'adresse " + scrutateur.getAddr());
    }

    public void changeState(EtatBureauDeVote etat){
        this.etat = etat;
    }

    public void creerSondage(String consigne, String choix1, String choix2,int nbBits, String createur){
        etat.creerSondage(this,consigne,choix1,choix2,nbBits,createur);
    }
    public void arreterRecolte(){
        etat.arreterRecolte(this);
    }

    public void publicationResultat(){
        etat.publicationResultat(this);
    }

    public void finirSondage(){
        etat.finirSondage(this);
    }

    public boolean aSondageEnCours(){
        return sondage != null;
    }

    public boolean resultatDisponible(){
        return getSondage() != null && getSondage().getResultat() != null;
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

    public boolean ajouterVoteChiffre(Message voteChiffre, Employe e){
        if(!VoteManager.getInstance().aDejaVoter(e.getEmail(),sondage.getUuid().toString())) {
            if (sondage.getNbVotant() > 0) {
                votesChiffres = ElGamal.Agreger(votesChiffres, voteChiffre, sondage.getPublicKeyInfo().getP());
            } else {
                votesChiffres = voteChiffre;
            }
            sondage.setNbVotant(sondage.getNbVotant() + 1);

            VoteManager.getInstance().creerVote(e.getEmail(),sondage.getUuid().toString());
            return true;
        }
        return false;
    }

    private void gererConnexion(Socket socket){
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
                gererConnexion(socket);
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

    public Scrutateur getScrutateur (){
        return scrutateur;
    }

    public void setSondage(Sondage sondage) {
        this.sondage = sondage;
    }

    public Message getVotesChiffres(){
        return votesChiffres;
    }

    public void setVotesChiffres(Message m){
        this.votesChiffres = m;
    }

    public EtatBureauDeVote getEtat(){
        return  this.etat;
    }

}
