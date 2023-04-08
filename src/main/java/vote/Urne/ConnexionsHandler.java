package vote.Urne;

import vote.Urne.Requetes.RequeteClient.RequeteUtilisateur.ConnexionReponse;
import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;
import vote.crypto.Hash;

import java.util.*;

/**
 * Cette classe forme partie de l'implementation d'une Base de Données
 * dans notre application
 * <p>
 *     Cette classe fait la gestion de connexion d'un utilisateur avec l'aide des données de notre
 *     Base de Donnees
 * </p>
 */
public class ConnexionsHandler {
    private static ConnexionsHandler instance = null;
    private HashMap<String,Employe> mapSessionId;//devra etre Utilisateur tout court plus tard

    private ConnexionsHandler(){
        mapSessionId = new HashMap<>();
    }

    public ConnexionReponse connect(String email, String motDePasse){
        EmployeManager manager = EmployeManager.getInstance();
        Employe e = manager.getEmploye(email);

        if(e == null || !Hash.checkPasswordFor(email,motDePasse)){
            return null;
        }
        String sessionId = UUID.randomUUID().toString();
        mapSessionId.put(sessionId,e);

        return new ConnexionReponse(sessionId,e);
    }


    public void disconnect(String sessionId){
        mapSessionId.remove(sessionId);
    }

    public void disconnectIfConnected(Employe e){
        List<String> ssid = getSSIDEmploye(e);
        for (String connexion: ssid){
            mapSessionId.remove(connexion);
        }
    }

    public List<String> getSSIDEmploye(Employe e){
        ArrayList<String> ssid = new ArrayList<>();
        if(mapSessionId.containsValue(e)){
            for(Map.Entry<String,Employe> set : mapSessionId.entrySet()){
                if(set.getValue().equals(e)){
                    ssid.add(set.getKey());
                }
            }
        }
        return ssid;
    }

    public boolean isConnected(String sessionId){
        return mapSessionId.containsKey(sessionId);
    }

    public Employe getEmploye(String ssid){
        return mapSessionId.get(ssid);
    }


    public static ConnexionsHandler getInstance(){
        if (instance == null){
            instance = new ConnexionsHandler();
        }
        return instance;
    }

}
