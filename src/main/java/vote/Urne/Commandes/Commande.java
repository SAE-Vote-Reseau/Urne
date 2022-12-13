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

/**
 * Classe Abstraite Faisant partie du package Commandes
 *
 * <p>
 *     Cette classe va être la template pour toute commande qu'on puisse vouloir implementer
 *     Commande est directement connecté avec BureauDeVote et permet la communication avec
 *     l'utilisateur et l'application Urne
 * </p>
 */
public abstract class Commande {
        private BureauDeVote urne;

        public Commande(BureauDeVote urne){
                this.urne = urne;
        }

        public BureauDeVote getUrne() {
                return urne;
        }

        /**
         * La méthode executer va être la méthode qui permet le fonctionnement
         * des diverses commandes, avec cette méthode on peut implementer les diverses
         * fonctionnalités des commandes qu'on voudrait implementer
         * @throws ExecutionFailedException
         * exception identifie une problematique au sein de la commande
         */
        public abstract void executer() throws ExecutionFailedException;

}
