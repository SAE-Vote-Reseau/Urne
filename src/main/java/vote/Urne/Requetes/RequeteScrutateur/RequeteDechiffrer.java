package vote.Urne.Requetes.RequeteScrutateur;

import vote.Urne.metier.Sondage;
import vote.crypto.Message;

/**
 * Une des fontionnalités souhaitées dans Scrutateur
 * est celle de déchiffrer les messages qui arrivent dans l'Urne
 * cette classe permet cela
 *
 * <p>
 *     Notre motivation pour structurer notre code en le séparant par classe
 *     ,était afin de rendre plus compréhensible la logique du programme
 * </p>
 */
public class RequeteDechiffrer extends RequeteScrutateur {
    private static final long serialVersionUID = 4859966778053979196L;
    private final Message m;
    private final Sondage sondageConcerne;
    private final int nbParticipant;

    public RequeteDechiffrer(Message m,int nbParticipant,Sondage sondageConcerne){
        super("dechiffrer");
        this.m = m;
        this.nbParticipant = nbParticipant;
        this.sondageConcerne = sondageConcerne;
    }

}
