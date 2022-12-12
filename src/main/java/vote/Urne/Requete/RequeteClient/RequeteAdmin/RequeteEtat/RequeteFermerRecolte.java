package vote.Urne.Requete.RequeteClient.RequeteAdmin.RequeteEtat;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requete.RequeteClient.Requete;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteFermerRecolte extends Requete {
    private static final long serialVersionUID = -5556137299633680909L;
    private String ssid;

    public RequeteFermerRecolte(String ssid) {
        super("fermer_recolte");
        this.ssid = ssid;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid) && ConnexionsHandler.getInstance().getEmploye(ssid).getIsAdmin()){
            bureau.arreterRecolte();
            out.writeObject("OK");
        }
        else {
            out.writeObject("Pas les droits");
        }
        out.flush();
    }
}
