package vote.Urne.Requete.RequeteClient.RequeteUtilisateur;

import vote.Urne.BureauDeVote;
import vote.Urne.Requete.RequeteClient.Requete;
import vote.Urne.metier.Sondage;
import vote.Urne.metier.SondageManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RequeteHistory extends Requete {
    private static final long serialVersionUID = -763580206821742400L;

    public RequeteHistory(){
        super("history");
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        List<Sondage> sondages = SondageManager.getInstance().getHistory();
        out.writeObject(sondages);
    }
}
