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
   /* public static String questionSondage="l'Israel est un pays ou pas ?"; //?????
    public static String choix1 = "NON";
    public static String choix2 = "OUI";
    public static String[] VOTEPACKAGE = new String[]{questionSondage,choix1,choix2};
    static int i=0;*/

    //reçois le vote du serveur
    /*public static void recevoirVote(){
        try{
            //création d'un socket client
            java.net.ServerSocket Serversocket = new java.net.ServerSocket(5565);
            Socket ClientSocket = Serversocket.accept();
            //création d'un flux d'entrée
            java.io.DataInputStream in = new java.io.DataInputStream(ClientSocket.getInputStream());
            //lecture du flux d'entrée

            String InputValue = (in.readUTF());
            if(InputValue.equals("0")||InputValue.equals("1")) {
                System.out.println("vote recu : " + InputValue);
            }
            else if (InputValue.equals("getSondage")){
                System.out.println("demande de sondage recu");
                java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(ClientSocket.getOutputStream());
                out.writeObject(VOTEPACKAGE);
                out.close();
            }
            else{
                System.out.println("erreur de vote");
            }

            //fermeture du flux d'entrée
            in.close();
            //fermeture du socket
            ClientSocket.close();
            Serversocket.close();
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
        i++;
        recevoirVote();
    }*/

    static Urne urne = new Urne(5565);

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

        private ArrayList<String> parsingStringBetweenQuote(String raw) throws ParsingException {
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
            try {
                urne.lancerNouveauSondage(sondage);
            }
            catch (Urne.UrneException e){
                throw new ExecutionFailedException(e.toString());
            }

        }

    }

    private static class CommandeFermerSondage implements Commande {

        @Override
        public void executer() throws ExecutionFailedException {
                try {
                    urne.arreterSondage();
                }
                catch (Urne.UrneException e){
                    throw new ExecutionFailedException(e.toString());
                }
        }
    }

    private static class CommandeSimulerClient implements Commande {
        private String requete;

        public CommandeSimulerClient(String commandeBrute) throws ParsingException{
            int sperateur = commandeBrute.indexOf(" ");
            if(sperateur == -1){
                throw new ParsingException("Il n'y a pas assez d'arguments");
            }
            this.requete = commandeBrute.substring(sperateur + 1);
        }

        @Override
        public void executer() throws ExecutionFailedException{
            try {
                Socket clientSimuler = new Socket("127.0.0.1", 5565);

                ObjectOutputStream outputData = new ObjectOutputStream(clientSimuler.getOutputStream());
                ObjectInputStream inputData = new ObjectInputStream(clientSimuler.getInputStream());

                outputData.writeUTF(requete);
                outputData.flush();

                Object reponse = inputData.readObject();
                System.out.println("Reponse du serveur: " + reponse);
            }
            catch (Exception e){
                 throw new ExecutionFailedException(e.toString());
            }
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
                case "simuler_client":
                    return new CommandeSimulerClient(commandeBrut);
            }
        }
        return null;
    }




    public static void main(String[] args) { //Le main est temporaire, il peut etre modifier plus tard
        System.out.println("Action possible:\ncreer \"[consigne]\" \"[choix1]\" \"[choix2]\"\nfermer_recolte\nsimuler_client [requete] (a des fin de tests, pas besoin de \"\")");
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
