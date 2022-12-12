package vote.Urne.Commandes.CommandeUrne;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;

/**
 * Cette commande exploite la fonctionalité de BureauDeVote:
 * <p>
 *     Le état Pattern
 * </p>
 * <p>
 *     Qui permet juste d'appeler une méthode et elle sera executé en fonction de l'etat
 *     actuel de BureauDeVote
 * </p>
 */
public class CommandePublierResultat extends Commande {
    public CommandePublierResultat(BureauDeVote urne){
        super(urne);
    }
    @Override
    public void executer() throws ExecutionFailedException {
        getUrne().publicationResultat();
    }
}