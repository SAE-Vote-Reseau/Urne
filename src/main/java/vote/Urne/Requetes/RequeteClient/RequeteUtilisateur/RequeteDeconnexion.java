package vote.Urne.Requetes.RequeteClient.RequeteUtilisateur;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requetes.RequeteClient.Requete;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteDeconnexion extends Requete {
    private static final long serialVersionUID = 1248828963788962307L;
    private String ssid;

    public RequeteDeconnexion(String ssid){
        super("deconect");
        this.ssid = ssid;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        ConnexionsHandler.getInstance().disconnect(ssid);
        out.writeObject("OK");
    }
}
