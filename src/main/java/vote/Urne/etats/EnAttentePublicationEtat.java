package vote.Urne.etats;

import vote.Urne.BureauDeVote;

import java.io.IOException;

/**
 * Classe qui effectue les fonctionnalités de bureau de Vote dans l'etat:
 * <h2>
 *     En Attente Publication
 * </h2>
 *
 * <p>
 *     Cette classe va ouvrir va effectuer 2 des 4 fonctionnalités qu'un Bureau de Vote devrait faire
 *     Cette classe va faire donc tout ce que Bureau de Vote devrait faire dans l'etat en Attente de Publication
 *     Ce qui veut dire :
 *     <ul>
 *         <li>Attendre</li>
 *         <li>Publiquer le resultat </li>
 *         <li>Finir Le Sondage</li>
 *     </ul>
 * </p>
 */
public class EnAttentePublicationEtat implements EtatBureauDeVote {
    /**
     *
     * @param bureau
     * Notre BureauDeVote mis en paramettre va pouvoir effectuer certaines fonctionnalités
     * Comme par exemple : Publiquer
     */
    public EnAttentePublicationEtat(BureauDeVote bureau){
        bureau.setVoteOuvert(false);
    }

    @Override
    public void creerSondage(BureauDeVote traitement, String consigne, String choix1, String choix2,int nbBits,String createur) {

    }

    @Override
    public void arreterRecolte(BureauDeVote traitement) {

    }

    /**
     *       Cette methode verifie si le déchiffrement s'est effectué correctement
     *       S'ils existent siffisament de votants
     *       Pour ensuite publier le résultat
     * @param traitement
     *Pour publier un résultat, la classe EnAttente cette méthode va prendre BureauDeVote comme
     *variable pour pouvoir ainsi effectuer des modifications dans la même.
     */
    @Override
    public void publicationResultat(BureauDeVote traitement) {
        try {
            if(traitement.getSondage().getNbVotant() > 0) {
                int nbChoix1 = traitement.getScrutateur()
                        .getDechifrer(traitement.getVotesChiffres(), traitement.getSondage()
                                .getNbVotant(),traitement.getSondage());
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

    /**
     *
     * @param traitement
     * Tout état doit avoir la possibilité de finir le sondage
     * Pour donner fin a notre Sondage on va change l'etat
     * à un état qui soit capable de finalizer le BureauDeVote
     */

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
