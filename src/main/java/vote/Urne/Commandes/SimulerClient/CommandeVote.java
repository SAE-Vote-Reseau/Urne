package vote.Urne.Commandes.SimulerClient;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Commandes.SimulerClient.CommandeSimulerClient;
import vote.Urne.Requete.RequeteClient.RequeteUtilisateur.RequeteVote;
import vote.crypto.Message;

import java.math.BigInteger;

import vote.crypto.ElGamal;

public  class CommandeVote extends CommandeSimulerClient {
    public CommandeVote(String commandeBrut, BureauDeVote urne) throws ParsingException, ExecutionFailedException {
        super(null, urne);
        Message voteChiffre = parseVote(commandeBrut);
        String ssid = parseSSID(commandeBrut);

        System.out.println(voteChiffre);
        setRequete(new RequeteVote(voteChiffre,ssid));
    }

    private String[] getParameters(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <2){
            throw new ParsingException("Pas assez d'arguments");
        }

        return parts;
    }

    private String parseSSID(String commandeBrut) throws ParsingException {
        return getParameters(commandeBrut)[2];
    }

    private Message parseVote(String commandeBrut) throws ParsingException,ExecutionFailedException {

        String[] parts = getParameters(commandeBrut);

        try {
            BigInteger vote = new BigInteger(parts[1]);
            if(vote.compareTo(BigInteger.valueOf(0)) < 0 || vote.compareTo(BigInteger.valueOf(1)) > 0){
                throw new ParsingException("Arguments invalides");
            }

            if(!getUrne().isVoteOuvert()){
                throw  new ExecutionFailedException("Le serveur n'a pas d'information de clé");
            }

            return ElGamal.encrypt(vote,getUrne().getSondage().getPublicKeyInfo());
        }
        catch (NumberFormatException e){
            throw new ParsingException("le vote n'est pas un nombre");
        }
    }

}