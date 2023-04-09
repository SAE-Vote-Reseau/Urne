package vote.Urne.Requetes.RequeteScrutateur;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

import javax.xml.crypto.dsig.keyinfo.KeyInfo;

import vote.crypto.VerifiedMessage;


public class RequeteVerification extends RequeteScrutateur {

    private VerifiedMessage message;
    private vote.crypto.KeyInfo publicKeyInfo;
    private static final long serialVersionUID = 1311514095806626001L;


    public RequeteVerification(VerifiedMessage message, vote.crypto.KeyInfo pkInfo) {
        super("check");

        this.message = message;
        this.publicKeyInfo = pkInfo;
        
    }
    
}
