package vote.Urne.Commandes.SimulerClient;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Requete.RequeteClient.Requete;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class CommandeSimulerClient extends Commande {
    private Requete requete;

    public CommandeSimulerClient(Requete requete, BureauDeVote urne){
        super(urne);
        this.requete = requete;
    }

    public void setRequete(Requete requete){
        this.requete = requete;
    }

    @Override
    public void executer() throws ExecutionFailedException {
        try {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            Socket clientSimuler = (SSLSocket) socketFactory.createSocket("127.0.0.1", 5565);

            ObjectOutputStream outputData = new ObjectOutputStream(clientSimuler.getOutputStream());
            ObjectInputStream inputData = new ObjectInputStream(clientSimuler.getInputStream());
            outputData.writeObject(requete);
            outputData.flush();

            Object reponse = inputData.readObject();
            System.out.println("Reponse du serveur: " + reponse);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new ExecutionFailedException(e.toString());
        }
    }
}