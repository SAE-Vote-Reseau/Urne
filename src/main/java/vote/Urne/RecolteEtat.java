package vote.Urne;

import java.util.ArrayList;

public class RecolteEtat implements EtatBureauDeVote{
    private ArrayList<String> votesChiffres;

    public RecolteEtat(BureauDeVote bureau, Sondage sondage){
        this.votesChiffres = new ArrayList<>();
        bureau.setSondage(sondage);
        bureau.setResultat(null);
        bureau.setVoteOuvert(true);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, Sondage sondage) {
    }

    @Override
    public void arreterRecolte(BureauDeVote traitement) {
        traitement.changeState(new EnAttentePublicationEtat(traitement));
    }

    @Override
    public void publicationResultat(BureauDeVote traitement) {

    }

    @Override
    public void finirSondage(BureauDeVote traitement) {

    }
}
