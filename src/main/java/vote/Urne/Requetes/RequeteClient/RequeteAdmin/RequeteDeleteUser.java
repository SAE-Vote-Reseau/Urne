package vote.Urne.Requetes.RequeteClient.RequeteAdmin;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requetes.RequeteClient.Requete;
import vote.Urne.metier.EmployeManager;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteDeleteUser extends Requete {
    private static final long serialVersionUID = -5975297827427502321L;
    private String ssid;
    private String email;

    public RequeteDeleteUser(String email,String ssid) {
        super("delete_user");
        this.ssid = ssid;
        this.email = email;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid) && ConnexionsHandler.getInstance().getEmploye(ssid).getIsAdmin()){
            ConnexionsHandler.getInstance().disconnectIfConnected(EmployeManager.getInstance().getEmploye(email));
            EmployeManager.getInstance().supprimerEmploye(email);
            out.writeObject("OK");
        }
        else {
            out.writeObject("Pas les droits");
        }
    }
}
