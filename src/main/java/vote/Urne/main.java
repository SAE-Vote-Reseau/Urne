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
import vote.Urne.Commandes.Exceptions.*;


public class main {
    static BureauDeVote urne;

    private static Commande parse(String commandeBrut) throws ParsingException,ExecutionFailedException {
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
                    return new CommandeGetSondage(urne,commandeBrut);
                case "vote":
                    return new CommandeVote(commandeBrut,urne);
                case "exit":
                    return new CommandeExit(urne);
                case "arreter_sondage":
                    return new CommandeArreterSondage(urne);
                case "publier_resultat":
                    return new CommandePublierResultat(urne);
                case "connect":
                    return new CommandeConnect(urne,commandeBrut);
                case "creer_utilisateur":
                    return new CommandeCreerUtilisateur(urne,commandeBrut);
                case "creer_sondage":
                    return new CommandeClientCreerSondage(urne,commandeBrut);
                case "update_user":
                    return new CommandeUpdateUser(urne,commandeBrut);
                case "set_admin":
                    return new CommandeSetAdmin(urne,commandeBrut);
                case "change_password":
                    return new CommandeChangePassword(urne,commandeBrut);
                case "getAllUsers":
                    return new CommandeGetAllUsers(commandeBrut,urne);
                case "creer_utilisateur_user":
                    return new CommandeClientCreerUtilisateur(urne,commandeBrut);
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

        System.out.println("Action possible:\n---General---\ncreer \"[consigne]\" \"[choix1]\" \"[choix2]\" \"nbBitsKeys\"\npublier_resultat\nfermer_recolte\narreter_sondage \nexit");
        System.out.println("---Simulation Client---\ngetSondage [ssid]\nvote [1 ou 0] [ssid]\nconnect \"[email]\" \"[password\"]\nchange_password \"[new_password]\" \"[ssid]\"\n");
        System.out.println("---Simulation Admin---\ncreer_sondage \"[consigne]\" \"[choix1]\" \"[choix2]\" \"[nbBits]\" \"[sessionid]\"\nupdate_user \"[email]\" \"[nom]\" \"[prenom]\" \"[mdp]\" \"[true/false: admin]\" \"[sessionId]\"\nset_admin \"[email]\" \"[true/false :setAdmin]\" \"[sessionId]\"\ngetAllUsers [ssid]\ncreer_utilisateur_user \"[email]\" \"[prenom]\" \"[nom]\" \"[mdp]\" \"[true/false: est admin]\" \"[ssid]\"\n");
        System.out.println("---Utilisateurs---\ncreer_utilisateur \"[email]\" \"[prenom]\" \"[nom]\" \"[mdp]\" \"[true/false: est admin]\"\n");
        try {
            urne = new BureauDeVote(5565, "127.0.0.1", 6656);
            urne.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
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
