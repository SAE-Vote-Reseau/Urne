package vote.Urne.Commandes.SimulerClient;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Requete.RequeteClient.RequeteUtilisateur.RequeteGetSondage;
import vote.Urne.Requete.RequeteClient.RequeteUtilisateur.RequeteHistory;

public class CommandeGetHistory extends CommandeSimulerClient {
    public CommandeGetHistory(BureauDeVote urne, String commandeBrut) throws ParsingException{
        super(new RequeteHistory(),urne);
    }

    private static String parseSSID(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <2){
            throw new ParsingException("Pas assez d'arguments");
        }

        return parts[1];
    }
}