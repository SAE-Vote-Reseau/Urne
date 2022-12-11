package vote.Urne;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

import vote.Urne.metier.EmployeManager;
import vote.crypto.Message;

public class RequeteVote extends Requete{
    private final Message voteChiffre;
    private final String ssId;
    private static final long serialVersionUID = -4214054064882241130L;

    public RequeteVote(Message voteChiffre, String ssId){
        super("vote");
        this.voteChiffre = voteChiffre;
        this.ssId = ssId;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(bureau.isVoteOuvert() && ConnexionsHandler.getInstance().isConnected(ssId)){
            System.out.println("vote chiffré: " + voteChiffre);
            boolean votePrisEnCompte = bureau.ajouterVoteChiffre(voteChiffre, ConnexionsHandler.getInstance().getEmploye(ssId));
            out.writeObject(votePrisEnCompte ? "OK": "Erreur: Deja voté !");
        }
        else {
            out.writeObject("Erreur: il n'est pas possible de voter");
        }
        out.flush();
    }
}
