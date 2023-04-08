package vote.Urne.Requetes.RequeteClient.RequeteAdmin.RequeteEtat;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requetes.RequeteClient.Requete;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequetePublierResultat extends Requete {
    private static final long serialVersionUID = 8398689807929469595L;
    private String ssid;

    public RequetePublierResultat(String ssid) {
        super("publier_resultat");
        this.ssid = ssid;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid) && ConnexionsHandler.getInstance().getEmploye(ssid).getIsAdmin()){
            bureau.publicationResultat();
            out.writeObject("OK");
        }
        else {
            out.writeObject("Pas les droits");
        }
        out.flush();
    }
}
