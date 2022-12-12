package vote.Urne;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteArreterSondage extends Requete {
    private static final long serialVersionUID = 2406585980453091613L;
    private String ssid;

    public RequeteArreterSondage(String ssid) {
        super("arreter_sondage");
        this.ssid = ssid;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid) && ConnexionsHandler.getInstance().getEmploye(ssid).getIsAdmin()){
            bureau.finirSondage();
            out.writeObject("OK");
        }
        else {
            out.writeObject("Pas les droits");
        }
        out.flush();
    }
}
