package vote.Urne;

import java.math.BigInteger;

public class TermineEtat implements EtatBureauDeVote{

    public TermineEtat(BureauDeVote bureau){
        bureau.setResultat(new BigInteger[2]); //provisoire
        bureau.setVoteOuvert(true);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, Sondage sondage) {

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
