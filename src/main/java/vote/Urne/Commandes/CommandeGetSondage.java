package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.RequeteGetSondage;
import vote.Urne.main;

public class CommandeGetSondage extends CommandeSimulerClient {
    public CommandeGetSondage(BureauDeVote urne){
        super(new RequeteGetSondage(),urne);
    }
}