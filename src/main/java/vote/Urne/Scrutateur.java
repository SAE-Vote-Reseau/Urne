package vote.Urne;

import vote.Urne.Requetes.RequeteScrutateur.RequeteDechiffrer;
import vote.Urne.Requetes.RequeteScrutateur.RequeteLancerSondage;
import vote.Urne.Requetes.RequeteScrutateur.RequeteScrutateur;
import vote.Urne.metier.Sondage;
import vote.crypto.KeyInfo;
import vote.crypto.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Classe qui represente le Scrutateur dans notre application
 * L'application Urne forme partie d'une application encore plus grand qui a pour nom:
 * <h3>Vote-Resseau</h3>
 * <p>
 *     Scrutateur represente la connexion entre le scrutateur, un autre composant de notre application Vote-Resseau
 *     et notre application Urne.
 * </p>
 * <p>
 *     Cela veut dire que toutes les fonctionnalités de Scrutateur qui seront d'utilité pour urne
 *     seront représentées dans cette classe.
 * </p>
 */
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

    /**
     *
     */
    public KeyInfo getKeyInfo(Sondage s) throws IOException,ClassNotFoundException{
        return (KeyInfo) faireRequete(new RequeteLancerSondage(s));
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
