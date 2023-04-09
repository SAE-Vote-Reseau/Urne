package vote.Urne;

import org.junit.jupiter.api.*;
import vote.Urne.Commandes.Commande;
import vote.Urne.Commandes.CommandeUrne.CommandeCreerSondage;
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
import vote.Urne.metier.Sondage;
import vote.Urne.metier.SondageManager;
import vote.crypto.KeyInfo;

import static org.mockito.Mockito.*;

class UrneTest {
    @Mock
    private Scrutateur scrutateurMock;
    @InjectMocks
    private BureauDeVote bureauDeVote = new BureauDeVote(
            UrneConf.getInstance().getPort(),
            UrneConf.getInstance().getIp(),
            UrneConf.getInstance().getPort()
    );
    private ServerSocket socketMock;

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
        socketMock = mock(ServerSocket.class);

        closeable = MockitoAnnotations.openMocks(this);
    }
    @Nested
    public class partieGenerale_Tests{
        @Test
        public void commandeCreer_test() throws IOException, ParsingException, ClassNotFoundException, ExecutionFailedException {
            String rawMessage = "creer \"message\" \"vote1\" \"vote2\"";
            Sondage s = SondageManager.getInstance().creerSondage("message","choix1","choix2","Admin");
            Mockito.when(bureauDeVote.getScrutateur().getKeyInfo(s)).thenReturn(new KeyInfo(BigInteger.ONE,BigInteger.TWO,BigInteger.TEN,BigInteger.TEN));
            Mockito.when(scrutateurMock.getKeyInfo(s)).thenReturn(new KeyInfo(BigInteger.ONE,BigInteger.TWO,BigInteger.TEN,BigInteger.TEN));
            System.out.println(bureauDeVote.getScrutateur().getKeyInfo(s));
            Commande c = new CommandeCreerSondage(rawMessage,bureauDeVote);
            c.executer();
        }
        @Test
        public void requeteCreer_Test(){

        }
    }

    @AfterEach
    public void close() throws Exception{
        closeable.close();
    }
}