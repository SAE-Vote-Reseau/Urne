package vote.Urne;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteConnexion extends Requete{
    private static final long serialVersionUID = -4031012286226927617L;
    private String email;
    private String password;

    public RequeteConnexion(String email, String password){
        super("connect");
        this.email =email;
        this.password = password;
    }
    @Override
    public void repondre(BureauDeVote bureau, ObjectOutputStream out) throws IOException {
        ConnexionsHandler connexions = ConnexionsHandler.getInstance();
        ConnexionReponse reponse = connexions.connect(email,password);

        System.out.println(reponse == null ? "Identifiant incorrect": "Bien connecté avec le sessionId " + reponse.getSsid());

        out.writeObject(reponse);
        out.flush();
    }
}
