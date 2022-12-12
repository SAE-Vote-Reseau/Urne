package vote.Urne.Commandes.Exceptions;

/**
 * Cette Exception cherche a expliciter un type spécifique
 * d'inconvenience qui peut se produire dans notre programme
 * On parle d'une inconvenience lors de l'execution d'une commande
 */
public class ExecutionFailedException extends Exception {
    public ExecutionFailedException(String str){
        super(str);
    }
}
