package vote.Urne.Commandes.SimulerClient;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Requetes.RequeteClient.RequeteUtilisateur.RequeteVote;
import vote.crypto.Message;
import vote.crypto.VerifiedMessage;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import vote.crypto.ElGamal;

public  class CommandeVote extends CommandeSimulerClient {
    public CommandeVote(String commandeBrut, BureauDeVote urne) throws ParsingException, ExecutionFailedException {
        super(null, urne);
        VerifiedMessage voteChiffre;
        try{
            voteChiffre = parseVote(commandeBrut);
        } catch(NoSuchAlgorithmException e){
            throw new ExecutionFailedException("L'algo demandé n'est pas disponible" + e.getMessage());
        }
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

    private VerifiedMessage parseVote(String commandeBrut) throws ParsingException,ExecutionFailedException,  NoSuchAlgorithmException {

        String[] parts = getParameters(commandeBrut);

        try {
            BigInteger vote = new BigInteger(parts[1]);

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