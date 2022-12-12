package vote.Urne;

import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteChangePassword extends Requete{
    private static final long serialVersionUID = -8228560866132091777L;
    private String ssid;
    private String newPassword;
    //cela deconnectera la personne
    public RequeteChangePassword(String newPassword, String ssid) {
        super("op");
        this.ssid = ssid;
        this.newPassword = newPassword;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid)){
            Employe e = ConnexionsHandler.getInstance().getEmploye(ssid);
            Employe m = new Employe(e.getEmail(),e.getNom(),e.getPrenom(),EmployeManager.getInstance().hashSHA256(newPassword), e.getIsAdmin());
            EmployeManager.getInstance().mettreAJourEmploye(m);
            ConnexionsHandler.getInstance().disconnectIfConnected(m);
            out.writeObject("OK");
        }else {
            out.writeObject("Pas les droits");
        }
        out.flush();
    }
}
