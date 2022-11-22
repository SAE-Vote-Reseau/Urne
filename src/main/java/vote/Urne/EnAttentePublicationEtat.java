package vote.Urne;

public class EnAttentePublicationEtat implements EtatBureauDeVote {

    public EnAttentePublicationEtat(BureauDeVote bureau){
        bureau.setResultat(null);
        bureau.setVoteOuvert(false);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, Sondage sondage) {

    }

    @Override
    public void arreterRecolte(BureauDeVote traitement) {

    }

    @Override
    public void publicationResultat(BureauDeVote traitement) {
        traitement.changeState(new TermineEtat(traitement));
    }

    @Override
    public void finirSondage(BureauDeVote traitement) {

    }
}
