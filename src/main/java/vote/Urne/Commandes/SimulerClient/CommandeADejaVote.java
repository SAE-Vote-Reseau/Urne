package vote.Urne.Commandes.SimulerClient;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Requete.RequeteClient.RequeteUtilisateur.RequeteGetADejaVote;
import vote.Urne.Requete.RequeteClient.RequeteUtilisateur.RequeteGetSondage;

public class CommandeADejaVote extends CommandeSimulerClient {
    public CommandeADejaVote(BureauDeVote urne, String commandeBrut) throws ParsingException{
        super(new RequeteGetADejaVote(parseSSID(commandeBrut)),urne);
    }

    private static String parseSSID(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <2){
            throw new ParsingException("Pas assez d'arguments");
        }

        return parts[1];
    }
}