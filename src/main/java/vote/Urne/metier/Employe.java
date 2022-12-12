package vote.Urne.metier;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Employe implements Serializable {
    private static final long serialVersionUID = -3716016828868854333L;
    private String email,nom,prenom;
    private byte[] motDePasse;
    private byte[] salt;
    boolean estAdmin;

    public Employe(String email,String nom,String prenom, byte[] motDePasse,byte[] salt, boolean estAdmin){
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
        this.salt = salt;
        this.estAdmin = estAdmin;
    }


    public String getEmail(){
        return email;
    }

    public byte[] getSalt(){
        return salt;
    }

    public byte[] getMotDePasse(){
        return motDePasse;
    }

    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }
    public boolean getIsAdmin(){
        return estAdmin;
    }
    public void setIsAdmin(boolean v){
        estAdmin = v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employe employe = (Employe) o;
        return Objects.equals(email, employe.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString(){
        return email + " " + prenom + " " + nom + " " + Arrays.toString(motDePasse) + " " +estAdmin;

    }
}
