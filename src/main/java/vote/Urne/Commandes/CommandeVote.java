package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.RequeteVote;
import vote.Urne.main;

public  class CommandeVote extends CommandeSimulerClient {
    public CommandeVote(String commandeBrut, BureauDeVote urne) throws ParsingException {
        super(null, urne);
        String voteChiffre = parseVote(commandeBrut);
        System.out.println(voteChiffre);
        setRequete(new RequeteVote(voteChiffre));
    }

    private String parseVote(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <2){
            throw new ParsingException("Pas assez d'arguments");
        }
        return parts[1];
    }

}