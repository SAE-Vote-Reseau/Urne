package vote.Urne;

import java.math.BigInteger;

public class TermineEtat implements EtatBureauDeVote{

    public TermineEtat(BureauDeVote bureau,Integer resultat){
        bureau.getSondage().setResultat(resultat);
        bureau.setVoteOuvert(false);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, String consigne, String choix1, String choix2) {

    }

    @Override
    public void arreterRecolte(BureauDeVote traitement) {

    }

    @Override
    public void publicationResultat(BureauDeVote traitement) {

    }

    @Override
    public void finirSondage(BureauDeVote traitement) {
        traitement.changeState(new SansSondageEtat(traitement));
    }
}
