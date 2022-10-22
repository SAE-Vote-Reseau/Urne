package vote.Urne;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class UrneTest {
    @Test
    void getSondageEnCoursConsigneEstUneConsigne() {
        String consigne = "Une consigne";
        Sondage sondageRecu = null;

        Sondage sondage = new Sondage(consigne,"choix 1", "choix 2");
        Urne urne = new Urne(5565);
        try {
            urne.lancerNouveauSondage(sondage);
            Socket clientSimuler = new Socket("127.0.0.1",5565);
            ObjectOutputStream outputData = new ObjectOutputStream(clientSimuler.getOutputStream());
            ObjectInputStream inputData = new ObjectInputStream(clientSimuler.getInputStream());

            outputData.writeUTF("getSondage");
            outputData.flush();

            sondageRecu = (Sondage) inputData.readObject();

            urne.arreterSondage();
        }
        catch (Exception e){
            System.out.println("error catched" + e);
        }

        assert sondageRecu != null;
        System.out.println("Consigne recu: " + sondageRecu.getConsigne());
        assertEquals(consigne,sondageRecu.getConsigne());
    }

}