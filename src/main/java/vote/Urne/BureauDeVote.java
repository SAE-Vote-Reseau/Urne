package vote.Urne;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;

import vote.Urne.Requetes.RequeteClient.Requete;
import vote.Urne.etats.EtatBureauDeVote;
import vote.Urne.etats.SansSondageEtat;
import vote.Urne.metier.Employe;
import vote.Urne.metier.Sondage;
import vote.Urne.metier.VoteManager;
import vote.crypto.Message;
import vote.crypto.VerifiedMessage;
import vote.crypto.ElGamal;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.util.concurrent.*;

/**
 * Classe centrale de l'application "Urne"
 *
 * <p>
 * Cette classe permet l'execution de notre application
 * Elle ecoute et execute toutes nos {@link Requete Requetes}
 * Bureau de Vote va aussi avoir plusieurs {@link EtatBureauDeVote Etats}
 * en vue que cette application implemente le state pattern
 * Bureau de Vote va pouvoir donc, avec l'aide de ses {@link EtatBureauDeVote Etats }
 * <ul>
 *     <li>Récolter des votes</li>
 *     <li>Publier des Resultats</li>
 * </ul>
 * et fermer l'application de manière sécurisée
 * <p>Cette application utilise les {@link ServerSocket sockets} afin de pouvoir se connecter
 * a un Scrutateur
 *</p>
 * </p>
 *<p>
 *     Elle permet le multithreading avec la generalisation de {@link Thread Thread}
 *</p>
 *
 * Pour l'instantier on devrait faire comme suit:
 *
 *<p>
 *     <i>BureauDeVote b = new BureauDeVote(port, addrScrut, portScrut); </i>
 *</p>
 */
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
     *
     * <p>
     * Ce constructeur va initializer notre socket
     * Ainsi que faire la gestion de ces parametres
     * Il va aussi instantier un {@link Scrutateur Scrutateur}
     * </p>
     * @throws IOException
     */
    public BureauDeVote(int port, String addrScrut, int portScrut) throws IOException {
        etat = new SansSondageEtat(this);
        signalArret = false;
        SSLContext context = SSLContextConf.getInstance().getSSLContext();
        SSLServerSocketFactory factory = context.getServerSocketFactory();

        this.serveur = ((SSLServerSocket) factory.createServerSocket(port));
        this.serveur.setSoTimeout(500);

        this.scrutateur = new Scrutateur(addrScrut,portScrut);


        System.out.println("Serveur configuré sur le port " + port);
        System.out.println("Scrutateur configuré sur le port " + scrutateur.getPort() + " et l'adresse " + scrutateur.getAddr());
    }

    /**
     *Cette méthode c'est la méthode qui permet le bon focntionnement
     * de notre state pattern, celle-ci possède un fonctionnement assez simple.
     * On met en paramétre {@link EtatBureauDeVote l'état} qu'on veut que notre classe, et plus spécifiquement
     * notre attribut aie.
     * Pour ainsi utilizer les méthodes uniques a chaque classe {@link EtatBureauDeVote etat}
     * (spécifié dans la documentation d'Etat)
     *
     * @param etat
     */
    public void changeState(EtatBureauDeVote etat){
        this.etat = etat;
    }

    public void creerSondage(String consigne, String choix1, String choix2, String createur){
        etat.creerSondage(this,consigne,choix1,choix2,createur);
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

    public boolean ajouterVoteChiffre(VerifiedMessage vm, Employe e){
        if(!VoteManager.getInstance().aDejaVoter(e.getEmail(),sondage.getUuid().toString())) {
            try {
                if(!ElGamal.proofIsValid(vm, sondage.getPublicKeyInfo())){
                    return false; //si le vote n'est pas valide, on arrete
                }
            } catch(NoSuchAlgorithmException err){
                System.out.println("Erreur : " + err.getMessage());
                return false;
            }

            Message voteChiffre = vm.getM();
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
                socket.close();
            } catch (IOException e) {
                System.out.println("Error while read bytes: " + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }


    @Override
    public void run(){
        ExecutorService executor = Executors.newFixedThreadPool(10);
        class Gerer implements Runnable {
            private final Socket s;
            public Gerer(Socket s){
                super();
                this.s = s;
            }
            @Override
            public void run() {
                gererConnexion(s);
            }
        }

        System.out.println("Serveur en ecoute");
        while (!signalArret) {
            Socket socket = null;
            try {
                socket = serveur.accept();
                //System.out.println("Un client s'est connecté");
                executor.execute(new Gerer(socket));
                //gererConnexion(socket);

            } catch (SocketTimeoutException e) { //Ca permet d'eviter qu'on demande la fermeture du serveur et que le thread reste bloqué sur le accept()
            } catch (IOException e) {
                System.out.println("Error while accepting connection :" + e);
            }
        }
        executor.shutdown();
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
