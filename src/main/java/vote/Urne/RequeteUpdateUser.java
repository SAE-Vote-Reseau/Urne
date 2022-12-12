package vote.Urne;

import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;
import vote.crypto.Hash;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteUpdateUser extends Requete{
    private String email;
    private String prenom;
    private String nom;
    private String motDePasse;
    private boolean estAdmin;

    private String ssid;
    private static final long serialVersionUID = 6751544680743635897L;

    /* l'email n'est pas modiable, car c'est sa clé primaire*/
    public RequeteUpdateUser(String email, String prenom, String nom, String motDePasse,Boolean estAdmin, String sessionId) {
        super("update");

        this.email = email;
        this.prenom = prenom;
        this.nom = nom;
        this.motDePasse = motDePasse;
        this.estAdmin = estAdmin;

        this.ssid = sessionId;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(ssid) && ConnexionsHandler.getInstance().getEmploye(ssid).getIsAdmin()){
            byte[][] hash = Hash.hashPassword(motDePasse);
            Employe update = new Employe(email,nom,prenom,hash[0],hash[1],estAdmin);
            EmployeManager.getInstance().mettreAJourEmploye(update);
            ConnexionsHandler.getInstance().disconnectIfConnected(update); //obliger pour mettre a jour
            out.writeObject("OK");
        }
        else {
            out.writeObject("Pas les droits");
        }
        out.flush();
    }
}
