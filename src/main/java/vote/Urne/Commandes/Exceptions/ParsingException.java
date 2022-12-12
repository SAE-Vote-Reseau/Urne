package vote.Urne.Commandes.Exceptions;

/**
 * Cette Exception cherche a expliciter un type spécifique
 * d'inconvenience qui peut se produire dans notre programme
 * On parle d'une inconvenience lors de la compréhension de la commande envoyée par l'Utilisasteur
 */
public class ParsingException extends Exception {
    public ParsingException(String str){
        super(str);
    }
}