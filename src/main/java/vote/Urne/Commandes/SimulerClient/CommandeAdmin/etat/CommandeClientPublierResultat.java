package vote.Urne.Commandes.SimulerClient.CommandeAdmin.etat;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Commandes.SimulerClient.CommandeSimulerClient;
import vote.Urne.Requete.RequeteClient.RequeteAdmin.RequeteEtat.RequetePublierResultat;

public class CommandeClientPublierResultat extends CommandeSimulerClient {
    public CommandeClientPublierResultat(BureauDeVote urne, String commandeBrut) throws ParsingException{
        super(new RequetePublierResultat(parseSSID(commandeBrut)),urne);
    }

    private static String parseSSID(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <2){
            throw new ParsingException("Pas assez d'arguments");
        }

        return parts[1];
    }
}