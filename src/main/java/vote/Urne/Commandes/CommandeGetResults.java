package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.RequeteGetResults;
import vote.Urne.main;
    public class CommandeGetResults extends CommandeSimulerClient {
        public CommandeGetResults(BureauDeVote urne){
            super(new RequeteGetResults(),urne);
        }
    }

