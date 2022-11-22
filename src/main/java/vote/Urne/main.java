package vote.Urne;

import org.junit.platform.commons.util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    static BureauDeVote urne;
    static boolean exit = false;

    private static class ParsingException extends Exception {
        public ParsingException(String str){
            super(str);
        }
    }

    private static class ExecutionFailedException extends Exception {
        public ExecutionFailedException(String str){
            super(str);
        }
    }

    private interface Commande {
        void executer() throws ExecutionFailedException;
    }

    private static class CommandeCreerSondage implements Commande {
        private Sondage sondage;

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

        public CommandeCreerSondage(String commandeBrute) throws ParsingException {
            ArrayList<String> parameters = parsingStringBetweenQuote(commandeBrute);

            if (parameters.size() < 3){
                throw new ParsingException("Il n'y a pas assez d'arguments");
            }

            sondage = new Sondage(parameters.get(0),parameters.get(1),parameters.get(2));
        }

        @Override
        public void executer() throws ExecutionFailedException {
                urne.creerSondage(sondage);
                System.out.println("Sondage créé");
        }

    }

    private static class CommandeFermerSondage implements Commande {
        @Override
        public void executer() throws ExecutionFailedException {
            urne.arreterRecolte();
            System.out.println("Recolte fermé");
        }
    }


    private abstract static class CommandeSimulerClient implements Commande {
        private Requete requete;

        public CommandeSimulerClient(Requete requete){
            this.requete = requete;
        }

        public void setRequete(Requete requete){
            this.requete = requete;
        }

        @Override
        public void executer() throws ExecutionFailedException{
            try {
                Socket clientSimuler = new Socket("127.0.0.1", 5565);

                ObjectOutputStream outputData = new ObjectOutputStream(clientSimuler.getOutputStream());
                ObjectInputStream inputData = new ObjectInputStream(clientSimuler.getInputStream());
                outputData.writeObject(requete); //Semble poser probleme
                outputData.flush();

                Object reponse = inputData.readObject();
                System.out.println("Reponse du serveur: " + reponse);
            }
            catch (Exception e){
                e.printStackTrace();
                 throw new ExecutionFailedException(e.toString());
            }
        }
    }

    private static class CommandeGetSondage extends CommandeSimulerClient{
        public CommandeGetSondage(){
            super(new RequeteGetSondage());
        }
    }

    private static class CommandeGetResults extends CommandeSimulerClient{
        public CommandeGetResults(){
            super(new RequeteGetResults());
        }
    }

    private static class CommandeVote extends CommandeSimulerClient{
        public CommandeVote(String commandeBrut) throws ParsingException{
            super(null);
            String voteChiffre = parseVote(commandeBrut);
            System.out.println(voteChiffre);
            setRequete(new RequeteVote(voteChiffre));
        }

        private String parseVote(String commandeBrut) throws ParsingException{
            String[] parts = commandeBrut.split(" ");

            if (parts.length <2){
                throw new ParsingException("Pas assez d'arguments");
            }
            return parts[1];
        }

    }

    private static class CommandeExit implements Commande {
        @Override
        public void executer() throws ExecutionFailedException {
            urne.fermerBureau();
            try {
                urne.join();
            }
            catch (InterruptedException e) {
                throw new ExecutionFailedException(e.toString());
            }

            exit = true;
        }
    }

    private static Commande parse(String commandeBrut) throws ParsingException{
        int firstSpace = commandeBrut.indexOf(" ");
        String firstWord = commandeBrut;
        if(firstSpace != -1){
            firstWord = commandeBrut.substring(0,firstSpace);
        }


        if(firstWord.length() > 0) {
            switch (firstWord) {
                case "creer":
                    return new CommandeCreerSondage(commandeBrut);
                case "fermer_recolte":
                    return new CommandeFermerSondage();
                case "getSondage":
                    return new CommandeGetSondage();
                case "getResults":
                    return new CommandeGetResults();
                case "vote":
                    return new CommandeVote(commandeBrut);
                case "exit":
                    return new CommandeExit();
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

        while(!exit) {
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
