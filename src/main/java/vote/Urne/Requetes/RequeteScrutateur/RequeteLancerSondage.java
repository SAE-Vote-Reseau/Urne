package vote.Urne.Requetes.RequeteScrutateur;

import vote.Urne.metier.Sondage;

/**
 *Cette requete à comme objectif de lancer un sondage et de retourner une clé
 * Neanmoins elle ne va pas pouvoir l'executer elle seule.
 * <p>
 *     Avec l'aide de {@link vote.Urne.Scrutateur Scrutateur} cette tache pourra être effectué
 * </p>
 */
public class RequeteLancerSondage extends RequeteScrutateur{ // devrait extends Requete une fois qu'on aura un truc commun, je ne peux pas a cause de Repondre pour le moment
    private static final long serialVersionUID = 5591047666560502694L;
    private Sondage sondage;

    public RequeteLancerSondage(Sondage sondage){
        super("getKey");
        this.sondage = sondage;
    }
}
