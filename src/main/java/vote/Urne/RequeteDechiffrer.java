package vote.Urne;

import vote.crypto.Message;

public class RequeteDechiffrer extends RequeteScrutateur {
    private final Message m;
    public RequeteDechiffrer(Message m){
        super("dechiffrer");
        this.m = m;
    }
}
