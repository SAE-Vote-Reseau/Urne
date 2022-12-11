package vote.Urne;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteGetSondage extends Requete{
    private static final long serialVersionUID = -880529969933408134L;
    private String ssId;
    public RequeteGetSondage(String ssid){
        super("getSondage");
        this.ssId = ssid;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        System.out.println("Demande sondage actuel recu");
        out.writeObject(bureau.aSondageEnCours() && ConnexionsHandler.getInstance().isConnected(ssId) ? bureau.getSondage():null);
        out.flush();
    }
}
