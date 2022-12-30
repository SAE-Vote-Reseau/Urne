package vote.Urne.metier;

import vote.Urne.metier.Stockage.Stockage;
import vote.Urne.metier.Stockage.StockageReferundumBdd;
import vote.Urne.metier.Stockage.StockageVoteBdd;

import java.util.List;
import java.util.stream.Collectors;

public class SondageManager {

    private StockageReferundumBdd stockage = StockageReferundumBdd.getInstance();
    private static SondageManager instance = null;

    private SondageManager(){

    }

    public static SondageManager getInstance() {
        if(instance == null){
            instance = new SondageManager();
        }
        return instance;
    }

    public Sondage creerSondage(String consigne, String choix1, String choix2,String createur){
        Sondage s = new Sondage(consigne,choix1,choix2,createur);
        stockage.enregistrer(s);
        return s;
    }

    public List<Sondage> getHistory(){
        List<Sondage> history = stockage.getAllSorted();
        return history.stream().filter((sondage -> sondage.getResultat() != null)).collect(Collectors.toList());
    }
    public void mettreAJourSondage(Sondage s){
        stockage.mettreAJour(s);
    }

    public List<Sondage> getAll(){
        return stockage.getAll();
    }

    public Sondage getSondage(String uuid){
        return stockage.get(uuid);
    }

    public void supprimerSondage(String uuid){
        stockage.supprimer(uuid);
    }

}
