package vote.Urne.Requetes.RequeteClient.RequeteUtilisateur;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requetes.RequeteClient.Requete;
import vote.Urne.metier.VoteManager;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteGetADejaVote extends Requete {
    private static final long serialVersionUID = -726741156287474558L;
    private String ssId;
    public RequeteGetADejaVote(String ssid){
        super("getADejaVote");
        this.ssId = ssid;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssId)) {
            out.writeObject(VoteManager.getInstance().aDejaVoter(ConnexionsHandler.getInstance().getEmploye(ssId).getEmail(), bureau.getSondage().getUuid().toString()));
        }
        else{
            out.writeObject(false);
        }
        out.flush();
    }
}
