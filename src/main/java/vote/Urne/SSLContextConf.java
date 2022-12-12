package vote.Urne;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class SSLContextConf {

    public static SSLContext setSystemPropertySSLContextServer(String keystore, String password){
        /*//String keyFilename = get
        System.out.println(keyFilename);

        System.setProperty("javax.net.ssl.keyStore", keyFilename);
        System.setProperty("javax.net.ssl.keyStorePassword", password);
        try {
            return SSLContext.getDefault();
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(3);
        }
        return null;*/
        return null;
    }
}
