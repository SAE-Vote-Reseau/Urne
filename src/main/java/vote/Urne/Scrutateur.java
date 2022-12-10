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

    public KeyInfo getKeyInfo(Sondage s,int nbBits) throws IOException,ClassNotFoundException{
        return (KeyInfo) faireRequete(new RequeteLancerSondage(s,nbBits));
    }

    public Integer getDechifrer(Message m, int nbParticipant, Sondage s) throws IOException,ClassNotFoundException{
        return (Integer) faireRequete(new RequeteDechiffrer(m,nbParticipant,s));
    }

    public String getAddr() {
        return addr;
    }

    public int getPort() {
        return port;
    }

}
