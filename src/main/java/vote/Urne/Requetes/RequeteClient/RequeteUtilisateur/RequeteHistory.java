package vote.Urne.Requetes.RequeteClient.RequeteUtilisateur;

import vote.Urne.BureauDeVote;
import vote.Urne.Requetes.RequeteClient.Requete;
import vote.Urne.metier.Sondage;
import vote.Urne.metier.SondageManager;
import vote.Urne.metier.Vote;
import vote.Urne.metier.VoteManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class RequeteHistory extends Requete {
    private static final long serialVersionUID = -763580206821742400L;

    public RequeteHistory(){
        super("history");
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        List<Sondage> sondages = SondageManager.getInstance().getHistory();
            for (Vote v : VoteManager.getInstance().getAll()) {
                for(Sondage s:sondages){
                    if(v.getUuidReferundum().equals(s.getUuid().toString())){
                        s.setNbVotant(s.getNbVotant() + 1);
                    }
                }
            }
        out.writeObject(sondages);
    }
}
