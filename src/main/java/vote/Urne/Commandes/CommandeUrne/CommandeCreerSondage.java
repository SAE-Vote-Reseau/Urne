package vote.Urne.Commandes.CommandeUrne;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cette Commande va permettre de creer un Sondage au sein de notre {@link BureauDeVote}
 * Au contraire de {@link CommandeArreterSondage} cette commande analyse une valeur
 * et l'execute si elle est correcte.
 */
public class CommandeCreerSondage extends Commande {
    private String consigne;
    private String choix1;
    private String choix2;


    public CommandeCreerSondage(String commandeBrute, BureauDeVote urne) throws ParsingException {
        super(urne);

        ArrayList<String> parameters = parsingStringBetweenQuote(commandeBrute);

        if (parameters.size() < 3){
            throw new ParsingException("Il n'y a pas assez d'arguments");
        }

        consigne = parameters.get(0);
        choix1 = parameters.get(1);
        choix2 = parameters.get(2);
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
        getUrne().creerSondage(consigne,choix1,choix2,"Admin");
    }
}
