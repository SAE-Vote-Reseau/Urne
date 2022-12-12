package vote.Urne;

import vote.Urne.BureauDeVote;
import vote.Urne.Requete;
import vote.Urne.metier.EmployeManager;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteAddUser extends Requete {
    private static final long serialVersionUID = -4206407180651779866L;
    private String email;
    private String prenom;
    private String nom;
    private String motDePasse;
    private boolean estAdmin;
    private String ssid;

    public RequeteAddUser(String email,String prenom, String nom, String motDePasse, boolean estAdmin, String ssid) {
        super("addUser");

        this.email = email;
        this.prenom = prenom;
        this.nom = nom;
        this.motDePasse = motDePasse;
        this.estAdmin = estAdmin;
        this.ssid = ssid;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if (ConnexionsHandler.getInstance().isConnected(ssid) && ConnexionsHandler.getInstance().getEmploye(ssid).getIsAdmin()) {
            EmployeManager.getInstance().creerEmploye(email,nom,prenom,motDePasse,estAdmin);
            out.writeObject("OK");
        } else {
            out.writeObject("Pas les droits");
        }
        out.flush();
    }
}
