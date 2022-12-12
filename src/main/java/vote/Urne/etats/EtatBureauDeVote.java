package vote.Urne.etats;

import vote.Urne.*;

/**
 * Interface Etat Bureau de Vote
 * <p>Implementé par :
 * <ol>
 *     <li>{@link EnAttentePublicationEtat EnAttentePublicationEtat}</li>
 *     <li>{@link RecolteEtat Recolte Etat }</li>
 *     <li>{@link SansSondageEtat SansSondageEtat}</li>
 *     <li>{@link TermineEtat TermineEtat}</li>
 * </ol>
 * </p>
 * <h3>Cette Inteface possède 4 méthodes à implementer</h3>
 * <p>Chaque classe
 * doit implementer ces methodes de manière coherente par rapport à leur utilité
 * On considére possible le fait renvoyer un message simple pour une méthode
 * qui n'est pas coherente avec la classe en question</p>
 */

public interface EtatBureauDeVote {
    void creerSondage(BureauDeVote traitement, String consigne, String choix1, String choix2, int nbBits, String createur);
    void arreterRecolte(BureauDeVote traitement);
    void publicationResultat(BureauDeVote traitement);
    void finirSondage(BureauDeVote traitement);

}
