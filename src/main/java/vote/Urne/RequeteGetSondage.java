package vote.Urne;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteGetSondage extends Requete{
    public RequeteGetSondage(){
        super("getSondage");
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        System.out.println("Demande sondage actuel recu");
        out.writeObject(bureau.aSondageEnCours() ? bureau.getSondage():null);
        out.flush();
    }
}
