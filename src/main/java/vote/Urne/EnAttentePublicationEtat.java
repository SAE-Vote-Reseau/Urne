package vote.Urne;

import java.io.IOException;

public class EnAttentePublicationEtat implements EtatBureauDeVote {

    public EnAttentePublicationEtat(BureauDeVote bureau){
        bureau.setVoteOuvert(false);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, String consigne, String choix1, String choix2,int nbBits) {

    }

    @Override
    public void arreterRecolte(BureauDeVote traitement) {

    }

    @Override
    public void publicationResultat(BureauDeVote traitement) {
        try {
            if(traitement.getSondage().getNbVotant() > 0) {
                int nbChoix1 = traitement.getScrutateur().getDechifrer(traitement.getVotesChiffres(), traitement.getSondage().getNbVotant(),traitement.getSondage());
                if(nbChoix1 == -2){
                    System.out.println("Le scrutateur ne reconnait pas le sondage, annulation du sondage");
                    traitement.changeState(new SansSondageEtat(traitement));
                }
                else {
                    traitement.changeState(new TermineEtat(traitement, nbChoix1));
                }
            }
            else {
                System.out.println("Aucun votant, sondage annulé");
                traitement.changeState(new SansSondageEtat(traitement));
            }

        } catch (IOException e){
            System.out.println("Erreur:" + e);

            System.out.println("Sondage annulé");
            traitement.changeState(new SansSondageEtat(traitement));
        }
        catch (ClassNotFoundException e){
            System.out.println("Resultat illisible: " + e);

            System.out.println("Sondage annulé");
            traitement.changeState(new SansSondageEtat(traitement));
        }
    }

    @Override
    public void finirSondage(BureauDeVote traitement) {
        System.out.println("Sondage annulé");
        traitement.changeState(new SansSondageEtat(traitement));
    }
    @Override
    public String toString(){
        return "En attente publication";
    }
}
