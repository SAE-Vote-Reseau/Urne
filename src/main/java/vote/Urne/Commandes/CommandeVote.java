package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.RequeteVote;
import vote.Urne.main;

import java.math.BigInteger;
import java.util.Arrays;

public  class CommandeVote extends CommandeSimulerClient {
    public CommandeVote(String commandeBrut, BureauDeVote urne) throws ParsingException {
        super(null, urne);
        BigInteger[] voteChiffre = parseVote(commandeBrut);
        System.out.println(Arrays.toString(voteChiffre));
        setRequete(new RequeteVote(voteChiffre));
    }

    private BigInteger[] parseVote(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <3){
            throw new ParsingException("Pas assez d'arguments");
        }

        BigInteger c1 = new BigInteger(parts[1]);
        BigInteger c2 = new BigInteger(parts[2]);
        return new BigInteger[]{c1,c2};
    }

}