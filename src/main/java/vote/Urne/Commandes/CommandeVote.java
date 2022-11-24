package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.RequeteVote;
import vote.Urne.main;
import vote.crypto.Message;

import java.math.BigInteger;
import java.util.Arrays;

import vote.crypto.ElGamal;

public  class CommandeVote extends CommandeSimulerClient {
    public CommandeVote(String commandeBrut, BureauDeVote urne) throws ParsingException, ExecutionFailedException {
        super(null, urne);
        Message voteChiffre = parseVote(commandeBrut);
        System.out.println(voteChiffre);
        setRequete(new RequeteVote(voteChiffre));
    }

    private Message parseVote(String commandeBrut) throws ParsingException,ExecutionFailedException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <2){
            throw new ParsingException("Pas assez d'arguments");
        }

        BigInteger vote = new BigInteger(parts[1]);
        if(vote.compareTo(BigInteger.valueOf(0)) < 0 || vote.compareTo(BigInteger.valueOf(1)) > 0){
            throw new ParsingException("Arguments invalides");
        }

        if(getUrne().getPublicKeyInfo() == null){
            throw  new ExecutionFailedException("Le serveur n'a pas d'information de clé");
        }

        return ElGamal.encrypt(new BigInteger(parts[1]),getUrne().getPublicKeyInfo());
    }

}