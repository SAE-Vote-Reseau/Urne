package vote.Urne.Commandes.CommandeUrne;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.main;

public class CommandeFermerSondage extends Commande {
    public CommandeFermerSondage(BureauDeVote urne){
        super(urne);
    }
    @Override
    public void executer() throws ExecutionFailedException {
        getUrne().arreterRecolte();
        System.out.println("Recolte fermé");
    }
}