package vote.Urne.Commandes.SimulerClient.CommandeAdmin.etat;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Commandes.SimulerClient.CommandeSimulerClient;
import vote.Urne.Requetes.RequeteClient.Requete;
import vote.Urne.Requetes.RequeteClient.RequeteAdmin.RequeteEtat.RequeteCreerSondage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandeClientCreerSondage extends CommandeSimulerClient {
    public CommandeClientCreerSondage(BureauDeVote urne,String raw) throws ParsingException {
        super(null, urne);
        List<String> l = parsingStringBetweenQuote(raw);

        if(l.size() < 4){
            throw new ParsingException("Pas assez d'arguments");
        }
        Requete r = new RequeteCreerSondage(l.get(0),l.get(1),l.get(2),l.get(3));
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
