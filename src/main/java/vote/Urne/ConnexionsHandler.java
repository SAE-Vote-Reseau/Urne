package vote.Urne;

import org.mindrot.jbcrypt.BCrypt;
import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;
import vote.crypto.Hash;
import vote.crypto.Message;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ConnexionsHandler {
    private static ConnexionsHandler instance = null;
    private HashMap<String,Employe> mapSessionId;//devra etre Utilisateur tout court plus tard

    private ConnexionsHandler(){
        mapSessionId = new HashMap<>();
    }

    public ConnexionReponse connect(String email,String motDePasse){
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
