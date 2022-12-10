package vote.Urne;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteLancerSondage extends RequeteScrutateur{ // devrait extends Requete une fois qu'on aura un truc commun, je ne peux pas a cause de Repondre pour le moment
    private static final long serialVersionUID = 5591047666560502694L;
    private Sondage sondage;
    private int nbBits;

    public RequeteLancerSondage(Sondage sondage, int nbBits){
        super("getKey");
        this.sondage = sondage;
        this.nbBits = nbBits;
    }
}
