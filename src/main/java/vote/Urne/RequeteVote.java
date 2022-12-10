package vote.Urne;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import vote.crypto.Message;

public class RequeteVote extends Requete{
    private final Message voteChiffre;
    private static final long serialVersionUID = -4214054064882241130L;

    public RequeteVote(Message voteChiffre){
        super("vote");
        this.voteChiffre = voteChiffre;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(bureau.isVoteOuvert()){
            System.out.println("vote chiffré: " + voteChiffre);
            bureau.ajouterVoteChiffre(voteChiffre);
            out.writeObject("OK");
        }
        else {
            out.writeObject("Erreur: il n'est pas possible de voter");
        }
        out.flush();
    }
}
