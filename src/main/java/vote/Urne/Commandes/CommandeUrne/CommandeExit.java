package vote.Urne.Commandes.CommandeUrne;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.main;

public class CommandeExit extends Commande {
    public CommandeExit(BureauDeVote urne){
        super(urne);
    }

    @Override
    public void executer() throws ExecutionFailedException {
        getUrne().fermerBureau();
        try {
            getUrne().join();
        }
        catch (InterruptedException e) {
            throw new ExecutionFailedException(e.toString());
        }

        System.exit(0);
    }
}
