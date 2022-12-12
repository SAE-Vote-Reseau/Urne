package vote.Urne.Commandes.CommandeUrne;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;

/**
 * Cette commande a comme seul objectif de fermer le sondage
 * Elle le fait a l'aide des méthodes de BureauDeVote qui à sa fois exploite
 * la fonctionalité des états dans notre classe
 * plus spécifiquement l'etat {@link vote.Urne.etats.TermineEtat Terminer}.
 */
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