package vote.Urne.Commandes.SimulerClient.CommandeAdmin.etat;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Commandes.SimulerClient.CommandeSimulerClient;
import vote.Urne.Requete.RequeteClient.RequeteAdmin.RequeteEtat.RequeteFermerRecolte;

public class CommandeClientFermerRecolte extends CommandeSimulerClient {
    public CommandeClientFermerRecolte(BureauDeVote urne, String commandeBrut) throws ParsingException{
        super(new RequeteFermerRecolte(parseSSID(commandeBrut)),urne);
    }

    private static String parseSSID(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <2){
            throw new ParsingException("Pas assez d'arguments");
        }

        return parts[1];
    }
}