package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.RequeteArreterSondage;
import vote.Urne.RequetePublierResultat;

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