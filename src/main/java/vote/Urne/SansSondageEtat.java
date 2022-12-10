package vote.Urne;

import vote.crypto.KeyInfo;
import vote.crypto.Message;

import java.io.IOException;

public class SansSondageEtat implements EtatBureauDeVote {

    public SansSondageEtat(BureauDeVote bureau){
        bureau.setSondage(null);
        bureau.setVoteOuvert(false);
        bureau.setVotesChiffres(null);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, String consigne, String choix1, String choix2,int nbBits) {
        try {
            Sondage sondage = new Sondage(consigne,choix1,choix2);
            KeyInfo publicKey = traitement.getScrutateur().getKeyInfo(sondage,nbBits);
            if (publicKey == null){
                System.out.println("Ce sondage est deja considéré comme en cours...");
                return;
            }
            sondage.setPublicKeyInfo(publicKey);
            System.out.println("pub key: " + sondage.getPublicKeyInfo().getKey().toString());
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

    @Override
    public String toString(){
        return "Sans etat";
    }
}
