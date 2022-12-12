package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Requete;
import vote.Urne.RequeteUpdateUser;
import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandeUpdateUser extends CommandeSimulerClient{
    public CommandeUpdateUser(BureauDeVote urne, String raw) throws ParsingException {
        super(null, urne);
        List<String> p = parsingStringBetweenQuote(raw);
        if(p.size() < 5){
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
