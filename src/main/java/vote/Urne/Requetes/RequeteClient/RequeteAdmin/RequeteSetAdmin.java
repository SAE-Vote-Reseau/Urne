package vote.Urne.Requetes.RequeteClient.RequeteAdmin;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requetes.RequeteClient.Requete;
import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteSetAdmin extends Requete {
    private static final long serialVersionUID = 551198149729236073L;
    private String email;
    private String ssid;
    private boolean setAdmin;

    public RequeteSetAdmin(String email,boolean setAdmin, String ssid) {
        super("op");
        this.email = email;
        this.ssid = ssid;
        this.setAdmin = setAdmin;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid) && ConnexionsHandler.getInstance().getEmploye(ssid).getIsAdmin()){
            Employe e = EmployeManager.getInstance().getEmploye(email);
            e.setIsAdmin(setAdmin);
            EmployeManager.getInstance().mettreAJourEmploye(e);
            ConnexionsHandler.getInstance().disconnectIfConnected(e);
            out.writeObject("OK");
        }else {
            out.writeObject("Pas les droits");
        }
        out.flush();
    }
}
