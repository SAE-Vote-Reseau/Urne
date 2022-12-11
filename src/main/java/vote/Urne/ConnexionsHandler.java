package vote.Urne;

import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;
import vote.crypto.Message;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class ConnexionsHandler {
    private static ConnexionsHandler instance = null;
    private HashMap<String,Employe> mapSessionId;//devra etre Utilisateur tout court plus tard

    private ConnexionsHandler(){
        mapSessionId = new HashMap<>();
    }

    public ConnexionReponse connect(String email,String motDePasse){
        EmployeManager manager = EmployeManager.getInstance();
        Employe e = manager.getEmploye(email);

        if(e == null || mapSessionId.containsValue(e) || !(Arrays.equals(e.getMotDePasse(), manager.hashSHA256(motDePasse)))){
            return null;
        }
        String sessionId = UUID.randomUUID().toString();
        mapSessionId.put(sessionId,e);

        return new ConnexionReponse(sessionId,e);
    }

    public void disconnect(String sessionId){
        mapSessionId.remove(sessionId);
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