package vote.Urne.Commandes.CommandeUrne;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.main;

/**
 * Cette commande est la connexion entre la commande envoyé par l'Utilisateur
 * et les diverses fonctionalités introduites dans ce programme
 * grâce aux état pattern de BureauDeVote
 */
public class CommandeFermerSondage extends Commande {
    public CommandeFermerSondage(BureauDeVote urne){
        super(urne);
    }

    /**
     * Cette méthode va appeller a ça fois une méthode de {@link BureauDeVote}
     *
     * On utilise cette structuration dans notre code
     * en vue du état pattern implementé dans BureauDeVote
     */
    @Override
    public void executer() throws ExecutionFailedException {
        getUrne().arreterRecolte();
        System.out.println("Recolte fermé");
    }
}