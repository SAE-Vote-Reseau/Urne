package vote.Urne.Commandes;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Sondage;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandeCreerSondage extends Commande {
    private Sondage sondage;

    public CommandeCreerSondage(String commandeBrute, BureauDeVote urne) throws ParsingException {
        super(urne);

        ArrayList<String> parameters = parsingStringBetweenQuote(commandeBrute);

        if (parameters.size() < 3){
            throw new ParsingException("Il n'y a pas assez d'arguments");
        }

        sondage = new Sondage(parameters.get(0),parameters.get(1),parameters.get(2));
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



    @Override
    public void executer() throws ExecutionFailedException {
        getUrne().creerSondage(sondage);
        System.out.println("Sondage créé");
    }
}
