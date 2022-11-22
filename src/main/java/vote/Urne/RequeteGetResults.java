package vote.Urne;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteGetResults extends Requete{

    public RequeteGetResults(){
        super("getResults");
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        out.writeObject(bureau.resultatDisponible() ? bureau.getResultat() : null);
        out.flush();
    }
}
