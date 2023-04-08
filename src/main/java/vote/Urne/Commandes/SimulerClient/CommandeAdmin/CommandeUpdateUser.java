package vote.Urne.Commandes.SimulerClient.CommandeAdmin;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Commandes.SimulerClient.CommandeSimulerClient;
import vote.Urne.Requetes.RequeteClient.Requete;
import vote.Urne.Requetes.RequeteClient.RequeteAdmin.RequeteUpdateUser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandeUpdateUser extends CommandeSimulerClient {
    public CommandeUpdateUser(BureauDeVote urne, String raw) throws ParsingException {
        super(null, urne);
        List<String> p = parsingStringBetweenQuote(raw);
        if(p.size() < 6){
            throw new ParsingException("Pas assez d'arguments");
        }
        Requete r = new RequeteUpdateUser(p.get(0),p.get(2),p.get(1),p.get(3),Boolean.parseBoolean(p.get(4)),p.get(5));
        super.setRequete(r);
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
