package vote.Urne.Requete.RequeteClient.RequeteAdmin.RequeteEtat;

import vote.Urne.BureauDeVote;
import vote.Urne.ConnexionsHandler;
import vote.Urne.Requete.RequeteClient.Requete;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteCreerSondage extends Requete {
    private static final long serialVersionUID = 4365821035425184354L;
    private String consigne;
    private String choix1;
    private String choix2;

    private String sessionId;

    public RequeteCreerSondage(String consigne,String choix1,String choix2,String sessionId){
        super("creer");
        this.consigne = consigne;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.sessionId = sessionId;
    }

    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        if(ConnexionsHandler.getInstance().isConnected(sessionId) && ConnexionsHandler.getInstance().getEmploye(sessionId).getIsAdmin()){
            if(bureau.getSondage() == null){
                bureau.creerSondage(consigne,choix1,choix2,ConnexionsHandler.getInstance().getEmploye(sessionId).getEmail());
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
