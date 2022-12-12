package vote.Urne.Commandes.SimulerClient;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Commandes.SimulerClient.CommandeSimulerClient;
import vote.Urne.Requete.RequeteClient.Requete;
import vote.Urne.Requete.RequeteClient.RequeteAdmin.RequeteChangePassword;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandeChangePassword extends CommandeSimulerClient {
    public CommandeChangePassword(BureauDeVote urne, String raw) throws ParsingException {
        super(null, urne);
        List<String> p = parsingStringBetweenQuote(raw);
        if(p.size() < 2){
            throw new ParsingException("Pas assez d'arguments");
        }
        Requete r = new RequeteChangePassword(p.get(0),p.get(1));
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
