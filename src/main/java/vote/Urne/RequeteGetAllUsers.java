package vote.Urne;

import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class RequeteGetAllUsers extends Requete{
    private static final long serialVersionUID = -1005850150066274606L;
    private String ssid;

    public RequeteGetAllUsers(String ssid) {
        super("getAll");
        this.ssid = ssid;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid) && ConnexionsHandler.getInstance().getEmploye(ssid).getIsAdmin()){
            List<Employe> e = EmployeManager.getInstance().getAll();
            out.writeObject(e);
        }
        else {
            out.writeObject("Pas les droits");
        }
    }
}
