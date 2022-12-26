package vote.Urne.Requete.RequeteScrutateur;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Cette classe abstraite montre la template a suivre pour les futures Requetes a faire par notre Scrutateur
 * <p>
 *     Cette classe a comme seul objectif d'executer une requete spécifique au scrutateur
 * </p>
 */
public abstract class RequeteScrutateur implements Serializable {
    private static final long serialVersionUID = 1311779095806626001L;

    private String prefix;

    public RequeteScrutateur(String prefix){
        this.prefix = prefix;
    }

}
