package vote.Urne.Commandes.CommandeUrne;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;

public class CommandeArreterSondage extends Commande {
    public CommandeArreterSondage(BureauDeVote urne){
        super(urne);
    }
    @Override
    public void executer() throws ExecutionFailedException {
        getUrne().finirSondage();
        System.out.println("Sondage fermé");
    }
}