package vote.Urne;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MailConf {
    private static MailConf instance = null;
    private String host;
    private String port;
    private String login;
    private String password;
    private String pathTrust;
    private String pwdTrust;

    private MailConf(){
        try{
            File file = new File("./Mail.props");
            if (file.exists()) {
                System.out.println("file au répertoire : " + file.getAbsolutePath());
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream(file);
                props.load(fis);

                host = props.getProperty("host");
                port = props.getProperty("port");

                login = props.getProperty("login");
                password = props.getProperty("password");

                pathTrust = props.getProperty("pathTrust");
                pwdTrust = props.getProperty("pwdTrust");

                fis.close();
            } else {
                file.createNewFile();
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(file);
                props.load(fis);
                props.setProperty("host","A remplir");
                props.setProperty("port","A remplir");
                props.setProperty("login","A remplir");
                props.setProperty("password","A remplir");
                props.setProperty("pathTrust","A remplir");
                props.setProperty("pwdTrust","A remplir");
                props.store(fos,"PROPERTIES");
                fis.close();
                fos.close();
                throw new RuntimeException("Veuillez remplir le fichier de configuration du mail");
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPathTrust() {
        return pathTrust;
    }

    public String getPwdTrust() {
        return pwdTrust;
    }

    public static MailConf getInstance(){
        if(instance == null){
            instance = new MailConf();
        }
        return instance;
    }
}
