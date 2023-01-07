package vote.Urne;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UrneConf {

    private static UrneConf instance = null;

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    private String ip;
    private int port;

    public int getPortScrutateur() {
        return portScrutateur;
    }

    private int portScrutateur;

    private UrneConf(){
        try{
            File file = new File("./urne.props");
            if (file.exists()) {
                System.out.println("file au répertoire : " + file.getAbsolutePath());
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream(file);
                props.load(fis);

                ip = props.getProperty("ipScrutateur");
                port = Integer.parseInt(props.getProperty("port"));
                portScrutateur = Integer.parseInt(props.getProperty("portScrutateur"));

                if(ip == null || port == 0){
                    throw new RuntimeException("Poprietes invalides");
                }

                fis.close();
            } else {
                file.createNewFile();
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(file);
                props.load(fis);
                props.setProperty("ipScrutateur","A remplir");
                props.setProperty("port","A remplir");
                props.setProperty("portScrutateur","A remplir");

                props.store(fos,"PROPERTIES");
                fis.close();
                fos.close();
                throw new RuntimeException("Veuillez remplir le fichier de configuration urne");
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(-2);
        }
    }
    public static UrneConf getInstance(){
        if (instance == null){
            instance = new UrneConf();
        }
        return instance;
    }
}
