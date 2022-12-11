package vote.Urne;

public interface EtatBureauDeVote {
    void creerSondage(BureauDeVote traitement, String consigne, String choix1, String choix2,int nbBits,String createur);
    void arreterRecolte(BureauDeVote traitement);
    void publicationResultat(BureauDeVote traitement);
    void finirSondage(BureauDeVote traitement);

}
