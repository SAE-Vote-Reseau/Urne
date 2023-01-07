package vote.Urne.metier;

import vote.Urne.metier.Stockage.Stockage;
import vote.Urne.metier.Stockage.StockageVoteBdd;

import java.util.List;

public class VoteManager {
    private Stockage<Vote,String[]> stockage = StockageVoteBdd.getInstance();
    private static VoteManager instance = null;

    private VoteManager(){

    }

    public static VoteManager getInstance() {
        if(instance == null){
            instance = new VoteManager();
        }
        return instance;
    }


    public Vote creerVote(String email, String referundumUUID){
        Vote v = new Vote(email,referundumUUID);
        stockage.enregistrer(v);
        return v;
    }

    public void mettreAJourVote(Vote v){
        stockage.mettreAJour(v);
    }

    public List<Vote> getAll(){
        return stockage.getAll();
    }

    public Vote getVote(String[] primaryKeys){
        return stockage.get(primaryKeys);
    }

    public void supprimerVote(String[] primaryKeys){
        stockage.supprimer(primaryKeys);
    }

    public boolean aDejaVoter(String email, String uuid){
        return stockage.get(new String[]{uuid,email}) != null;
    }

}
