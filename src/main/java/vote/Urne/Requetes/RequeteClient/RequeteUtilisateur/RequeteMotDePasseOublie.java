package vote.Urne.Requetes.RequeteClient.RequeteUtilisateur;

import vote.Urne.AuthentifactionCodeManager;
import vote.Urne.BureauDeVote;
import vote.Urne.Requetes.RequeteClient.Requete;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteMotDePasseOublie extends Requete {
    private static final long serialVersionUID = -785501772790984993L;
    private String email;

    public RequeteMotDePasseOublie(String email){
        super("forgetPassword");
        this.email = email;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        AuthentifactionCodeManager.getInstance().sendCode(email);
        out.writeObject("OK");
    }
}
