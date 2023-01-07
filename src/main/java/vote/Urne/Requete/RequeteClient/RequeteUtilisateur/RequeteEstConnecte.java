package vote.Urne.Requete.RequeteClient.RequeteUtilisateur;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requete.RequeteClient.Requete;
import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;
import vote.crypto.Hash;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteEstConnecte extends Requete {
    private static final long serialVersionUID = -730437664822465754L;
    private String ssid;
    public RequeteEstConnecte(String ssid) {
        super("est connecte");
        this.ssid = ssid;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid)){
            out.writeObject(true);
        }else {
            out.writeObject(false);
        }
        out.flush();
    }
}
