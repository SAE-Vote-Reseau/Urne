package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Requete;
import vote.Urne.RequeteConnexion;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandeConnect extends CommandeSimulerClient{

    public CommandeConnect(BureauDeVote urne, String raw) throws ParsingException {
        super(null, urne);
        ArrayList<String> p = parsingStringBetweenQuote(raw);

        if (p.size() < 1){
            throw new ParsingException("pas assez de parametres");
        }

        RequeteConnexion r = new RequeteConnexion(p.get(0),p.get(1));

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
