package vote.Urne;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteCreerSondage extends Requete{
    private static final long serialVersionUID = 4365821035425184354L;
    private String consigne;
    private String choix1;
    private String choix2;

    private int nbBits;

    private String sessionId;

    public RequeteCreerSondage(String consigne,String choix1,String choix2,int nbBits,String sessionId){
        super("creer");
        this.consigne = consigne;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.sessionId = sessionId;

        this.nbBits = nbBits;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(sessionId) && ConnexionsHandler.getInstance().getEmploye(sessionId).getIsAdmin()){
            if(bureau.getSondage() == null){
                bureau.creerSondage(consigne,choix1,choix2,nbBits,ConnexionsHandler.getInstance().getEmploye(sessionId).getEmail());
                out.writeObject("OK");
            }
            else {
                out.writeObject("Erreur: Deja un sondage en cours !");
            }
        }
        else {
            out.writeObject("Pas les droits");
        }
    }
}
