package vote.Urne.metier;

import org.mindrot.jbcrypt.BCrypt;
import vote.Urne.metier.Stockage.Stockage;
import vote.Urne.metier.Stockage.StockageEmployeBdd;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class EmployeManager {
    private Stockage<Employe,String> stockage = StockageEmployeBdd.getInstance();
    private static EmployeManager instance = null;

    private EmployeManager(){

    }

    public static EmployeManager getInstance() {
        if(instance == null){
            instance = new EmployeManager();
        }
        return instance;
    }

    public Employe creerEmploye(String email, String nom, String prenom, String motDePasse, boolean estAdmin){
        byte[][] hash = hashPassword(motDePasse);
        Employe e = new Employe(email,nom,prenom,hash[0],hash[1],estAdmin);
        stockage.enregistrer(e);

        return e;
    }

    public byte[][] hashPassword(String toHash){
        String salt = BCrypt.gensalt(12);
        return new byte[][]{BCrypt.hashpw(toHash,salt).getBytes(StandardCharsets.UTF_8),salt.getBytes(StandardCharsets.UTF_8)};
    }



    public void mettreAJourEmploye(Employe e){
        stockage.mettreAJour(e);
    }

    public List<Employe> getAll(){
        return stockage.getAll();
    }

    public Employe getEmploye(String email){
        return stockage.get(email);
    }

    public void supprimerEmploye(String email){
        stockage.supprimer(email);
    }

}
