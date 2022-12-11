package vote.Urne;

import java.util.ArrayList;

public class RecolteEtat implements EtatBureauDeVote{

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
