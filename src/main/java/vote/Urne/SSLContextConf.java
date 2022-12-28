package vote.Urne;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.KeyStore;

public class SSLContextConf {

    private static SSLContextConf instance = null;

    private SSLContextConf(){

    }

    public SSLContext getSSLContext(){
        final char[] password = "auuugh".toCharArray();
        try {
            final KeyStore keystore = KeyStore.getInstance(new File("SAE"), password);
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
