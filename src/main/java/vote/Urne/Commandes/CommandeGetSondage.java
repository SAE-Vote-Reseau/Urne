package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.RequeteGetSondage;
import vote.Urne.main;
import vote.crypto.ElGamal;

import java.math.BigInteger;

public class CommandeGetSondage extends CommandeSimulerClient {
    public CommandeGetSondage(BureauDeVote urne, String commandeBrut) throws ParsingException{
        super(new RequeteGetSondage(parseSSID(commandeBrut)),urne);
    }

    private static String parseSSID(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <2){
            throw new ParsingException("Pas assez d'arguments");
        }

        return parts[1];
    }
}