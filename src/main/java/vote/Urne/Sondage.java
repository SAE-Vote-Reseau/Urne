package vote.Urne;

import java.io.Serializable;

public class Sondage implements Serializable {
    private static final long serialVersionUID = 7339119561699635756L;
    private String consigne;
    private String choix1;
    private String choix2;
    //peut etre rajouter le stockage des votes ici

    public Sondage(String consigne,String choix1,String choix2){
        this.consigne = consigne;
        this.choix1 = choix1;
        this.choix2 = choix2;
    }

    @Override
    public String toString(){
        return consigne + ": " + choix1 + "/" + choix2;
    }

}
