package vote.Urne.Commandes.SimulerClient.CommandeAdmin;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Commandes.SimulerClient.CommandeSimulerClient;
import vote.Urne.Requetes.RequeteClient.RequeteAdmin.RequeteAddUser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandeClientCreerUtilisateur extends CommandeSimulerClient {

    private String email;
    private String prenom;
    private String nom;
    private String motDePasse;
    private boolean estAdmin;
    private String ssid;

    public CommandeClientCreerUtilisateur(BureauDeVote urne, String commandeBrut) throws ParsingException{
        super(null,urne);

        ArrayList<String> parameters = parsingStringBetweenQuote(commandeBrut);

        if(parameters.size() < 6){
            throw new ParsingException("Pas assez d'arguments");
        }

        email = parameters.get(0);
        prenom = parameters.get(1);
        nom = parameters.get(2);
        motDePasse = parameters.get(3);
        estAdmin = Boolean.parseBoolean(parameters.get(4));
        this.ssid = parameters.get(5);
        super.setRequete(new RequeteAddUser(email,prenom,nom,motDePasse,estAdmin,ssid));
    }

    private ArrayList<String> parsingStringBetweenQuote(String raw) {
        ArrayList<String> parameters = new ArrayList<>();

        Pattern pattern = Pattern.compile("\"[^\"]+\"");
        Matcher matcher = pattern.matcher(raw);

        while (matcher.find()){
            String parameter = matcher.group();
            parameters.add(parameter.substring(1, parameter.length()-1));
        }

        return parameters;
    }

}
