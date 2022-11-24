package vote.Urne;

import vote.crypto.KeyInfo;
import vote.crypto.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Scrutateur {
    private String addr;
    private int port;

    public Scrutateur(String addr, int port){
        this.addr = addr;
        this.port = port;
    }


    private Object faireRequete(RequeteScrutateur rq) throws IOException,ClassNotFoundException{
        Socket clientSocket = new Socket(addr,port);

        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        out.writeObject(rq);
        out.flush();

        return in.readObject();
    }

    public KeyInfo getKeyInfo() throws IOException,ClassNotFoundException{
        return (KeyInfo) faireRequete(new RequeteGetKey());
    }

    public int getDechifrer(Message m, int nbParticipant) throws IOException,ClassNotFoundException{
        return (int) faireRequete(new RequeteDechiffrer(m,nbParticipant));
    }

    public String getAddr() {
        return addr;
    }

    public int getPort() {
        return port;
    }

}
