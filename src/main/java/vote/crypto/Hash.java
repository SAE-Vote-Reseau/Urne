package vote.crypto;

import org.mindrot.jbcrypt.BCrypt;
import vote.Urne.metier.Employe;
import vote.Urne.metier.EmployeManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Hash {

    public static String pepper = "/I-*s!raè+l";

    public static byte[][] hashPassword(String toHash){
      //  toHash += pepper; //pepper
        String salt = BCrypt.gensalt(12);
        return new byte[][]{BCrypt.hashpw(toHash,salt).getBytes(StandardCharsets.UTF_8),salt.getBytes(StandardCharsets.UTF_8)};
    }

    public static boolean checkPasswordFor(String email, String password){
        //password += pepper;
        Employe e = EmployeManager.getInstance().getEmploye(email);
        return  e != null && BCrypt.checkpw(password,new String(e.getMotDePasse(),StandardCharsets.UTF_8));
    }
}
