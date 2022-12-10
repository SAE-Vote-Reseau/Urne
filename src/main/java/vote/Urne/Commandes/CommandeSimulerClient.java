package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Requete;
import vote.Urne.main;

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
            Socket clientSimuler = new Socket("127.0.0.1", 5565);

            ObjectOutputStream outputData = new ObjectOutputStream(clientSimuler.getOutputStream());
            ObjectInputStream inputData = new ObjectInputStream(clientSimuler.getInputStream());
            outputData.writeObject(requete); //Semble poser probleme
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