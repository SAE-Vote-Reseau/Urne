package vote.Urne.metier;

public class Vote {
    private String emailEmploye;
    private String uuidReferundum;

    public Vote(String emailEmploye, String uuid){
        this.emailEmploye = emailEmploye;
        this.uuidReferundum = uuid;
    }

    public String getUuidReferundum() {
        return uuidReferundum;
    }

    public String getEmailEmploye() {
        return emailEmploye;
    }
}
