package vote.Urne.Commandes;

import vote.Urne.*;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Commande {
        private BureauDeVote urne;

        public Commande(BureauDeVote urne){
                this.urne = urne;
        }

        public BureauDeVote getUrne() {
                return urne;
        }

        public abstract void executer() throws ExecutionFailedException;


}
