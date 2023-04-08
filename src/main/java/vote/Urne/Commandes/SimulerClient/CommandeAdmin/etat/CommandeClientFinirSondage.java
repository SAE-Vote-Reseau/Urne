package vote.Urne.Commandes.SimulerClient.CommandeAdmin.etat;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Commandes.SimulerClient.CommandeSimulerClient;
import vote.Urne.Requetes.RequeteClient.RequeteAdmin.RequeteEtat.RequeteArreterSondage;

public class CommandeClientFinirSondage extends CommandeSimulerClient {
    public CommandeClientFinirSondage(BureauDeVote urne, String commandeBrut) throws ParsingException{
        super(new RequeteArreterSondage(parseSSID(commandeBrut)),urne);
    }

    private static String parseSSID(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <2){
            throw new ParsingException("Pas assez d'arguments");
        }

        return parts[1];
    }
}