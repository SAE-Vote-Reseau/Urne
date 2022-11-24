package vote.Urne;

import vote.crypto.KeyInfo;

import java.io.IOException;

public class SansSondageEtat implements EtatBureauDeVote {

    public SansSondageEtat(BureauDeVote bureau){
        bureau.setSondage(null);
        bureau.setVoteOuvert(false);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, String consigne, String choix1, String choix2) {
        try {
            KeyInfo publicKey = traitement.getScrutateur().getKeyInfo();
            Sondage sondage = new Sondage(consigne,choix1,choix2,publicKey);
            traitement.changeState(new RecolteEtat(traitement, sondage));
            System.out.println("Sondage créé");
        }
        catch (IOException e){
            System.out.println("Erreur: " + e);
        }
        catch (ClassNotFoundException e){
            System.out.println("Reponse illisible: " + e);
        }
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
