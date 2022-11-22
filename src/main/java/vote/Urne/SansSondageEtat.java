package vote.Urne;

public class SansSondageEtat implements EtatBureauDeVote {

    public SansSondageEtat(BureauDeVote bureau){
        bureau.setSondage(null);
        bureau.setResultat(null);
        bureau.setVoteOuvert(false);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, Sondage sondage) {
        traitement.changeState(new RecolteEtat(traitement,sondage));
    }

    @Override
    public void arreterRecolte(BureauDeVote traitement) {

    }

    @Override
    public void publicationResultat(BureauDeVote traitement) {

    }

    @Override
    public void finirSondage(BureauDeVote traitement) {

    }
}
