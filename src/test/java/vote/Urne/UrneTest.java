package vote.Urne;

import org.junit.jupiter.api.*;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.CommandeUrne.CommandeCreerSondage;
import vote.Urne.Commandes.CommandeUrne.CommandeCreerUtilisateur;
import vote.Urne.Commandes.CommandeUrne.CommandePublierResultat;
import vote.Urne.Commandes.Exceptions.ExecutionFailedException;
import vote.Urne.Commandes.Exceptions.ParsingException;
import vote.Urne.Requetes.RequeteClient.Requete;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;


import org.mockito.*;
import vote.Urne.etats.RecolteEtat;
import vote.Urne.etats.TermineEtat;
import vote.Urne.metier.Sondage;
import vote.Urne.metier.SondageManager;
import vote.crypto.KeyInfo;

import static org.junit.jupiter.api.Assertions.*;

class UrneTest {
    @Mock
    private ServerSocket socketMock;
    private Socket scrutateurSocketMock;
    @Mock
    private Scrutateur scrutateurMock;
    @InjectMocks
    private BureauDeVote bureauDeVote = new BureauDeVote(
            UrneConf.getInstance().getPort(),
            UrneConf.getInstance().getIp(),
            UrneConf.getInstance().getPort()
    );
    private Sondage s = new Sondage("consigne1","vote1","vote2","emailTest@yopmail.com");


    private AutoCloseable closeable;

    UrneTest() throws IOException {
    }

    /*
    Tests unitaires:
    Commandes Generales (A la fin si j'ai le temps)

    Partie Scrutateur creersondage etc ... (Faire une socket simuler un scrutateur)
    Parties Admin
    Partie Utilisateurs

     */
    @BeforeEach
    public void initialize() throws IOException {
        closeable = MockitoAnnotations.openMocks(this);
    }
    @Nested
    public class partieGenerale_Tests{
        @Test
        public void commandeCreer_test() throws IOException, ParsingException, ClassNotFoundException, ExecutionFailedException {
            String rawMessage = "creer \"message\" \"vote1\" \"vote2\"";
            Mockito.when(bureauDeVote.getScrutateur().getKeyInfo(s)).thenReturn(new KeyInfo(BigInteger.ONE,BigInteger.TWO,BigInteger.TEN,BigInteger.TEN));
            Mockito.when(scrutateurMock.getKeyInfo(s)).thenReturn(new KeyInfo(BigInteger.ONE,BigInteger.TWO,BigInteger.TEN,BigInteger.TEN));
            System.out.println(bureauDeVote.getScrutateur().getKeyInfo(s));
            Commande c = new CommandeCreerSondage(rawMessage,bureauDeVote);
            c.executer();
            assertEquals(RecolteEtat.class,bureauDeVote.getEtat());
        }
        @Test
        public void requeteCreer_Test() throws IOException, ClassNotFoundException, ParsingException, ExecutionFailedException {
            String rawMessage = "creer \"message\" \"vote1\" \"vote2\"";
            Commande c = new CommandeCreerSondage(rawMessage,bureauDeVote);
            c.executer();
            Mockito.verify(scrutateurMock).getKeyInfo(s);
            assertEquals(RecolteEtat.class,bureauDeVote.getEtat());
        }
        @Test
        public void commandePublierResultat_test() throws ExecutionFailedException {
            Commande publier = new CommandePublierResultat(bureauDeVote);
            s.setNbVotant(1);
            bureauDeVote.setSondage(s);
            publier.executer();
            Mockito.verify(bureauDeVote.getSondage().getNbVotant());
            assertEquals(TermineEtat.class,bureauDeVote.getEtat());
        }
        @Test
        public void commandeCreerUtilisateur_test() throws ParsingException, ExecutionFailedException {
            String rawCommand = "creer_utilisateur \"a@yopmail.com\" \"prenom \" \" nom \" \" 1234\" \"true\" ";
            Commande utilisateur = new CommandeCreerUtilisateur(bureauDeVote,rawCommand);
            utilisateur.executer();
        }
    }

    @AfterEach
    public void close() throws Exception{
        closeable.close();
    }
}