package vote.Urne;

import vote.Urne.metier.EmployeManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class AuthentifactionCodeManager {
    private static AuthentifactionCodeManager instance = null;
    private Map<String,String> codesEnCours = new HashMap<>(); // on pourrait mettre un timeout mais bon zzzz

    private AuthentifactionCodeManager(){

    }

    public void sendCode(String email){
        UUID id = UUID.randomUUID();
        codesEnCours.put(id.toString(),email);

        if(EmployeManager.getInstance().getEmploye(email) != null) {
            MailManager.getInstance().sendMail(email, "Changer son mot de passe", "Le code a indiqué pour changer le mot de passe est: " + id);
            System.out.println("code " + id + " envoyé par email");
        }
    }

    public String validate(String id){
        return codesEnCours.remove(id);
    }

    public static AuthentifactionCodeManager getInstance(){
        if(instance == null){
            instance = new AuthentifactionCodeManager();
        }
        return instance;
    }
}
