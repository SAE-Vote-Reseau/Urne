package vote.Urne.Commandes.SimulerClient.CommandeAdmin;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Commandes.SimulerClient.CommandeSimulerClient;
import vote.Urne.Requete.RequeteClient.RequeteAdmin.RequeteDeleteUser;
import vote.Urne.Requete.RequeteClient.RequeteAdmin.RequeteGetAllUsers;

public  class CommandeDeleteUser extends CommandeSimulerClient {
    public CommandeDeleteUser(String commandeBrut, BureauDeVote urne) throws ParsingException, ExecutionFailedException {
        super(null, urne);
        String ssid = parseSSID(commandeBrut);
        String email = parseEmail(commandeBrut);

        setRequete(new RequeteDeleteUser(email,ssid));
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

    private String parseEmail(String commandeBrut) throws ParsingException {
        return getParameters(commandeBrut)[1];
    }

}