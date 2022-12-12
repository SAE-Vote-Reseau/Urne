package vote.Urne.etats;

import vote.Urne.BureauDeVote;
import vote.Urne.etats.EtatBureauDeVote;
import vote.Urne.etats.SansSondageEtat;
import vote.Urne.metier.SondageManager;

public class TermineEtat implements EtatBureauDeVote {

    public TermineEtat(BureauDeVote bureau, Integer resultat){
        System.out.println("Resultat dechiffré: " + resultat);
        bureau.getSondage().setResultat(resultat);
        SondageManager.getInstance().mettreAJourSondage(bureau.getSondage());
        bureau.setVoteOuvert(false);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, String consigne, String choix1, String choix2,int nbBits,String createur) {

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
    @Override
    public String toString(){
        return "Fini, resultat disponible";
    }
}
