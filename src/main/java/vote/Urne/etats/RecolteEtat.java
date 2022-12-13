package vote.Urne.etats;

import vote.Urne.BureauDeVote;
import vote.Urne.metier.Sondage;

/**
 *      Classe qui effectue les fonctionnalités de bureau de Vote dans l'etat:
 * <h2>
 *  Recolte
 * </h2>
 *
 *  <p>
 *      Cette classe va être utilisée dans le étapes prémières
 *      de notre Bureau De Vote
 *      Elle va ouvrir la possibilité de voter avec l'aide de notre Bureau de Vote
 *      Pour ensuite pouvoir recolter les votes qui arrivent depuis un autre programme
 *      Dans notre cas on sait que c'est un {@link vote.Urne.Scrutateur Scrutateur}
 *      <i>La recollection des votes ce fait au moment ou is sont envoyées par le {@link vote.Urne.Scrutateur Scrutateur} </i>
 *
 *  </p>
 */
public class RecolteEtat implements EtatBureauDeVote {

    public RecolteEtat(BureauDeVote bureau, Sondage sondage){
        bureau.setSondage(sondage);
        bureau.setVoteOuvert(true);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, String consigne, String choix1, String choix2,int nbBits,String createur) {
    }

    @Override
    public void arreterRecolte(BureauDeVote traitement) {
        traitement.changeState(new EnAttentePublicationEtat(traitement));
    }

    @Override
    public void publicationResultat(BureauDeVote traitement) {

    }

    /**
     * Tout état doit avoir la possibilité de finir le sondage
     * Pour donner fin a notre Sondage on va change l'etat de
     * Bureau de Vote a un état qui soit capable de finalizer le BureauDeVote
     *
     * @param traitement
     */
    @Override
    public void finirSondage(BureauDeVote traitement) {
        System.out.println("Sondage annulé");
        traitement.changeState(new SansSondageEtat(traitement));
    }

    @Override
    public String toString(){
        return "En Recolte";
    }
}
