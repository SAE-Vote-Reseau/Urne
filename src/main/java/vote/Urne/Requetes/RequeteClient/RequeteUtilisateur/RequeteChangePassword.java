package vote.Urne.Requetes.RequeteClient.RequeteUtilisateur;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requetes.RequeteClient.Requete;
import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;
import vote.crypto.Hash;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteChangePassword extends Requete {
    private static final long serialVersionUID = -8228560866132091777L;
    private String ssid;
    private String newPassword;
    //cela deconnectera la personne
    public RequeteChangePassword(String newPassword, String ssid) {
        super("changerMdp");
        this.ssid = ssid;
        this.newPassword = newPassword;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid)){
            Employe e = ConnexionsHandler.getInstance().getEmploye(ssid);
            byte[][] hash = Hash.hashPassword(newPassword);
            Employe m = new Employe(e.getEmail(),e.getNom(),e.getPrenom(),hash[0],hash[1], e.getIsAdmin());
            EmployeManager.getInstance().mettreAJourEmploye(m);
            out.writeObject("OK");
        }else {
            out.writeObject("Pas les droits");
        }
        out.flush();
    }
}
