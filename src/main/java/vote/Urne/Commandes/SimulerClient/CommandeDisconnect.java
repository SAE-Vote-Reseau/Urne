package vote.Urne.Commandes.SimulerClient;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Requetes.RequeteClient.RequeteUtilisateur.RequeteDeconnexion;

public  class CommandeDisconnect extends CommandeSimulerClient {
    public CommandeDisconnect(String commandeBrut, BureauDeVote urne) throws ParsingException, ExecutionFailedException {
        super(null, urne);
        String ssid = parseSSID(commandeBrut);
        setRequete(new RequeteDeconnexion(ssid));
    }

    private String[] getParameters(String commandeBrut) throws ParsingException {
        String[] parts = commandeBrut.split(" ");

        if (parts.length <1){
            throw new ParsingException("Pas assez d'arguments");
        }

        return parts;
    }

    private String parseSSID(String commandeBrut) throws ParsingException {
        return getParameters(commandeBrut)[1];
    }
}