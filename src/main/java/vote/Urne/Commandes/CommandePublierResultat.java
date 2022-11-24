package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;

public class CommandePublierResultat extends Commande {
    public CommandePublierResultat(BureauDeVote urne){
        super(urne);
    }
    @Override
    public void executer() throws ExecutionFailedException {
        getUrne().publicationResultat();
    }
}