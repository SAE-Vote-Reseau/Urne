package vote.Urne;

import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteUpdateUser extends Requete{
    private Employe employe;
    private String ssid;

    /* l'email n'est pas modiable, car c'est sa clé primaire*/
    public RequeteUpdateUser(Employe employe,String sessionId) {
        super("update");

        this.employe = employe;
        this.ssid = sessionId;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid) && ConnexionsHandler.getInstance().getEmploye(ssid).getIsAdmin()){
            EmployeManager.getInstance().mettreAJourEmploye(employe);
            ConnexionsHandler.getInstance().disconnectIfConnected(employe); //obliger pour mettre a jour
            out.writeObject("OK");
        }
        else {
            out.writeObject("Pas les droits");
        }
        out.flush();
    }
}
