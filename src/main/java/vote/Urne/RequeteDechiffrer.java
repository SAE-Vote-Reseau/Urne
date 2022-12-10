package vote.Urne;

import vote.crypto.Message;


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
