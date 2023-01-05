package vote.Urne.metier.Stockage.Conf;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLUtils {
    private static SQLUtils instance = null;
    private Connection connection;

    private SQLUtils() {
        try{
        File file = new File("./SQLUtils.props");
        if (file.exists()) {
            System.out.println("file au répertoire : " + file.getAbsolutePath());
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(file);
            props.load(fis);
            String url = props.getProperty("url");
            String driver = props.getProperty("driver");

            String login = props.getProperty("login");
            String password = props.getProperty("password");
            fis.close();
            Class.forName(driver);
            connection = DriverManager.getConnection(url,login,password);
            } else {
            file.createNewFile();
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(file);
            props.load(fis);
            props.setProperty("url","A remplir");
            props.setProperty("driver","A remplir");
            props.setProperty("login","A remplir");
            props.setProperty("password","A remplir");
            props.store(fos,"PROPERTIES");
            fis.close();
            fos.close();
            System.out.println("le fichier est au repertoire : " + file.getAbsolutePath());
            throw new RuntimeException("Veuillez remplir le fichier de configuration");
        }
    }catch (IOException | SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
            System.out.println("Configuration sql invalide");
            System.exit(-2);
    }
//        String url = "jdbc:mysql://webinfo.iutmontp.univ-montp2.fr/jimenezh";
//        String driver = "com.mysql.cj.jdbc.Driver";
//        String login = "jimenezh";
//        String password = "203300612DK";

    }

    public Connection getConnection() {
        return this.connection;
    }


    public static SQLUtils getInstance(){

        if(instance == null) {
            instance = new SQLUtils();
        }
        return instance;
    }
}
