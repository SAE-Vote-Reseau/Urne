package vote.Urne;

import vote.Urne.Commandes.Commande;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vote.Urne.Commandes.*;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;

public class main {
    static BureauDeVote urne;

    private static Commande parse(String commandeBrut) throws ParsingException {
        int firstSpace = commandeBrut.indexOf(" ");
        String firstWord = commandeBrut;
        if(firstSpace != -1){
            firstWord = commandeBrut.substring(0,firstSpace);
        }


        if(firstWord.length() > 0) {
            switch (firstWord) {
                case "creer":
                    return new CommandeCreerSondage(commandeBrut,urne);
                case "fermer_recolte":
                    return new CommandeFermerSondage(urne);
                case "getSondage":
                    return new CommandeGetSondage(urne);
                case "getResults":
                    return new CommandeGetResults(urne);
                case "vote":
                    return new CommandeVote(commandeBrut,urne);
                case "exit":
                    return new CommandeExit(urne);
            }
        }
        return null;
    }



    public static void main(String[] args) { //Le main est temporaire, il peut etre modifier plus tard
        System.out.println("  ____     _   _      _       ____      _  __    ____    U  ___ u   _       _      \n" +
                " / __\"| u |'| |'| U  /\"\\  uU |  _\"\\ u  |\"|/ /  U|  _\"\\ u  \\/\"_ \\/  |\"|     |\"|     \n" +
                "<\\___ \\/ /| |_| |\\ \\/ _ \\/  \\| |_) |/  | ' /   \\| |_) |/  | | | |U | | u U | | u   \n" +
                " u___) | U|  _  |u / ___ \\   |  _ <  U/| . \\\\u  |  __/.-,_| |_| | \\| |/__ \\| |/__  \n" +
                " |____/>> |_| |_| /_/   \\_\\  |_| \\_\\   |_|\\_\\   |_|    \\_)-\\___/   |_____| |_____| \n" +
                "  )(  (__)//   \\\\  \\\\    >>  //   \\\\_,-,>> \\\\,-.||>>_       \\\\     //  \\\\  //  \\\\  \n" +
                " (__)    (_\") (\"_)(__)  (__)(__)  (__)\\.)   (_/(__)__)     (__)   (_\")(\"_)(_\")(\"_) ");

        System.out.println("Action possible:\n---General---\ncreer \"[consigne]\" \"[choix1]\" \"[choix2]\"\nfermer_recolte\nexit");
        System.out.println("---Simulation Client---\ngetSondage\nvote [voteChiffres]\ngetResults\n");
        try {
            urne = new BureauDeVote(5565);
            urne.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            Scanner input = new Scanner(System.in);
            String rawCommand = input.nextLine();

            try {
                Commande commande = parse(rawCommand);
                if (commande != null){
                    commande.executer();
                }
                else{
                    System.out.println("La commande n'existe pas, ressayez");
                }
            }
            catch (ParsingException e){
                System.out.println("Impossible de parser la commande : " + e);
            }
            catch (ExecutionFailedException e){
                System.out.println("Impossible d'executer la commande : " + e);
            }
        }
    }
}
