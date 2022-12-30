package vote.Urne.Requete.RequeteClient.RequeteUtilisateur;

import vote.Urne.AuthentifactionCodeManager;
import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requete.RequeteClient.Requete;
import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;
import vote.crypto.Hash;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteChangerMotDePasseOublie extends Requete {
    private static final long serialVersionUID = -3874105142477244763L;
    private String id;
    private String password;

    public RequeteChangerMotDePasseOublie(String id,String password){
        super("changeForgetPassword");
        this.id = id;
        this.password = password;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        String email = AuthentifactionCodeManager.getInstance().validate(id);
        if(email != null) {
            Employe e = EmployeManager.getInstance().getEmploye(email);
            ConnexionsHandler.getInstance().disconnectIfConnected(e);

            byte[][] hash = Hash.hashPassword(password);
            EmployeManager.getInstance().mettreAJourEmploye(new Employe(e.getEmail(), e.getNom(), e.getPrenom(), hash[0], hash[1], e.getIsAdmin()));
            out.writeObject("OK");
        }
        else {
            out.writeObject("Erreur: Code invalide");
        }
    }
}
