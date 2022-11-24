package vote.Urne;

import vote.crypto.ElGamal;
import vote.crypto.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteDechiffrer extends RequeteScrutateur {
    private static final long serialVersionUID = 4859966778053979196L;
    private final Message m;
    private final int nbParticipant;

    public RequeteDechiffrer(Message m,int nbParticipant){
        super("dechiffrer");
        this.m = m;
        this.nbParticipant = nbParticipant;
    }

}
