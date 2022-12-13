package vote.Urne.Commandes.CommandeUrne;

import vote.Urne.BureauDeVote;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cette commande va creer un utilisateur avec une chaine de caractères prise en parametre
 * L'Utilisateur crée va ensuite être ajoute a notre Base de Données avec l'aide de {@link EmployeManager}.
 *
 */
public class CommandeCreerUtilisateur extends Commande {

    private String email;
    private String prenom;
    private String nom;
    private String motDePasse;
    private boolean estAdmin;

    public CommandeCreerUtilisateur(BureauDeVote urne, String commandeBrut) throws ParsingException{
        super(urne);

        ArrayList<String> parameters = parsingStringBetweenQuote(commandeBrut);

        if(parameters.size() < 5){
            throw new ParsingException("Pas assez d'arguments");
        }

        email = parameters.get(0);
        prenom = parameters.get(1);
        nom = parameters.get(2);
        motDePasse = parameters.get(3);
        estAdmin = Boolean.parseBoolean(parameters.get(4));
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
        EmployeManager.getInstance().creerEmploye(email,nom,prenom,motDePasse,estAdmin);
        for(Employe e: EmployeManager.getInstance().getAll()){
            System.out.println(e);
        }
    }
}
