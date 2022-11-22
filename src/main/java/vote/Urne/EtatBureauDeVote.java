package vote.Urne;

public interface EtatBureauDeVote {
    void creerSondage(BureauDeVote traitement, Sondage sondage);
    void arreterRecolte(BureauDeVote traitement);
    void publicationResultat(BureauDeVote traitement);
    void finirSondage(BureauDeVote traitement);

}
