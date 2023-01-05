package vote.Urne;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.KeyStore;
import java.util.Properties;

public class SSLContextConf {

    private static SSLContextConf instance = null;
    private String password;
    private String path;
    private String clientTrustPath;
    private String passwordClient;

    public String getClientTrustPath() {
        return clientTrustPath;
    }

    public String getPasswordClient() {
        return passwordClient;
    }

    private SSLContextConf(){
        try{
            File file = new File("./TLS.props");
            if (file.exists()) {
                System.out.println("file au répertoire : " + file.getAbsolutePath());
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream(file);
                props.load(fis);

                path = props.getProperty("pathTrust");
                password = props.getProperty("password");

                clientTrustPath = props.getProperty("clientTestTrustPath");
                passwordClient = props.getProperty("passwordClient");


                if(path == null || password == null){
                    throw new RuntimeException("Poprietes invalides");
                }

                fis.close();
            } else {
                file.createNewFile();
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(file);
                props.load(fis);
                props.setProperty("pathTrust","A remplir");
                props.setProperty("password","A remplir");
                props.setProperty("passwordClient","Facultatif");
                props.setProperty("clientTestTrustPath","Facultatif(sert a pouvoir simuler un client)");

                props.store(fos,"PROPERTIES");
                fis.close();
                fos.close();
                throw new RuntimeException("Veuillez remplir le fichier de configuration du mail");
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(-2);
        }
    }

    public SSLContext getSSLContext(){
        final char[] password = this.password.toCharArray();
        try {
            final KeyStore keystore = KeyStore.getInstance(new File(this.path), password);
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keystore);

            final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keystore,password);

            final SSLContext context = SSLContext.getInstance("TLS");
            context.init(keyManagerFactory.getKeyManagers(),trustManagerFactory.getTrustManagers(),null);
            return context;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    public static SSLContextConf getInstance(){
        if (instance == null){
            instance = new SSLContextConf();
        }
        return instance;
    }
}
