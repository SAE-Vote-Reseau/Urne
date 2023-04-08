package vote.Urne.Requetes.RequeteClient.RequeteUtilisateur;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requetes.RequeteClient.Requete;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteGetSondage extends Requete {
    private static final long serialVersionUID = -880529969933408134L;
    private String ssId;
    public RequeteGetSondage(String ssid){
        super("getSondage");
        this.ssId = ssid;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        out.writeObject(bureau.aSondageEnCours() && ConnexionsHandler.getInstance().isConnected(ssId) ? bureau.getSondage():null);
        out.flush();
    }
}
