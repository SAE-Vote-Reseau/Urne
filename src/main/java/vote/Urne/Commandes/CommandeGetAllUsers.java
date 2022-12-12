package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.RequeteGetAllUsers;
import vote.Urne.RequeteVote;
import vote.crypto.ElGamal;
import vote.crypto.Message;

import java.math.BigInteger;

public  class CommandeGetAllUsers extends CommandeSimulerClient {
    public CommandeGetAllUsers(String commandeBrut, BureauDeVote urne) throws ParsingException, ExecutionFailedException {
        super(null, urne);
        String ssid = parseSSID(commandeBrut);

       setRequete(new RequeteGetAllUsers(ssid));
    }

    private String[] getParameters(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <2){
            throw new ParsingException("Pas assez d'arguments");
        }

        return parts;
    }

    private String parseSSID(String commandeBrut) throws ParsingException {
        return getParameters(commandeBrut)[1];
    }

}