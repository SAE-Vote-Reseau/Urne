package vote.Urne;

import java.io.IOException;

public class EnAttentePublicationEtat implements EtatBureauDeVote {

    public EnAttentePublicationEtat(BureauDeVote bureau){
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
        try {
            int nbChoix1 = traitement.getScrutateur().getDechifrer(traitement.getVotesChiffres());
            traitement.changeState(new TermineEtat(traitement,nbChoix1));
        } catch (IOException e){
            System.out.println("Erreur:" + e);
        }
        catch (ClassNotFoundException e){
            System.out.println("Resultat illisible: " + e);
        }

        System.out.println("Sondage annulé");
        traitement.changeState(new SansSondageEtat(traitement));
    }

    @Override
    public void finirSondage(BureauDeVote traitement) {

    }
}
