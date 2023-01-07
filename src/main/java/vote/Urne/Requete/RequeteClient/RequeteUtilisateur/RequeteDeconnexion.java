package vote.Urne.Requete.RequeteClient.RequeteUtilisateur;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requete.RequeteClient.Requete;
import vote.Urne.metier.Sondage;
import vote.Urne.metier.SondageManager;
import vote.Urne.metier.Vote;
import vote.Urne.metier.VoteManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class RequeteDeconnexion extends Requete {
    private static final long serialVersionUID = -763580206821742400L;
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
