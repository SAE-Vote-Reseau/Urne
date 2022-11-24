package vote.Urne;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteVote extends Requete{
    private String voteChiffre;
    private static final long serialVersionUID = -4214054064882241130L;

    public RequeteVote(String voteChiffre){
        super("vote");
        this.voteChiffre = voteChiffre;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(bureau.isVoteOuvert()){
            bureau.ajouterVoteChiffre(voteChiffre);
            out.writeObject("OK");
        }
        else {
            out.writeObject("Erreur: il n'est pas possible de voter");
        }
        out.flush();
    }
}
