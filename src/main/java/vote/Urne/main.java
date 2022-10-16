package vote.Urne;

import java.net.Socket;
import java.util.ArrayList;

import static java.lang.System.out;

public class main {
    public static String questionSondage="l'Israel est un pays ou pas ?";
    public static String choix1 = "NON";
    public static String choix2 = "OUI";
    public static String[] VOTEPACKAGE = new String[]{questionSondage,choix1,choix2};
    static int i=0;





    //reçois le vote du serveur
    public static void main(String[] args) {
        try{
            //création d'un socket client
            java.net.ServerSocket Serversocket = new java.net.ServerSocket(5565);
            Socket ClientSocket = Serversocket.accept();
            //création d'un flux d'entrée
            java.io.DataInputStream in = new java.io.DataInputStream(ClientSocket.getInputStream());
            //lecture du flux d'entrée

            String InputValue = (in.readUTF());
            if(InputValue.equals("0")||InputValue.equals("1")) {
                out.println("vote recu : " + InputValue);
            }
            else if (InputValue.equals("getSondage")){
                out.println("demande de sondage recu");
                java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(ClientSocket.getOutputStream());
                out.writeObject(VOTEPACKAGE);
                out.close();
            }
            else{
                out.println("erreur de vote");
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
        main(args);

    }


}
