package vote.Urne;


import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteGetKey extends RequeteScrutateur{ // devrait extends Requete une fois qu'on aura un truc commun, je ne peux pas a cause de Repondre pour le moment
    private static final long serialVersionUID = 5591047666560502694L;

    public RequeteGetKey(){
        super("getKey");
    }
}
