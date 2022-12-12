package vote.Urne.metier.Stockage;

import vote.Urne.metier.Employe;
import vote.Urne.metier.Stockage.Conf.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockageEmployeBdd implements Stockage<Employe,String> {

    private static StockageEmployeBdd instance = null;

    private StockageEmployeBdd(){

    }

    @Override
    public void enregistrer(Employe employe) {
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "INSERT INTO Utilisateur(email,Nom,Prenom,password,isAdmin,salt) VALUES(?,?,?,?,?,?)";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,employe.getEmail());
            statement.setString(2,employe.getNom());
            statement.setString(3,employe.getPrenom());
            statement.setBytes(4,employe.getMotDePasse());
            statement.setBoolean(5,employe.getIsAdmin());
            statement.setBytes(6,employe.getSalt());

            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(String email) {
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "DELETE FROM Utilisateur WHERE email=?)";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,email);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mettreAJour(Employe employe) {
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "UPDATE Utilisateur SET email = ?, Nom = ?, Prenom = ?, password = ?, isAdmin = ?, salt = ? WHERE email = ?";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,employe.getEmail());
            statement.setString(2,employe.getNom());
            statement.setString(3,employe.getPrenom());
            statement.setBytes(4,employe.getMotDePasse());
            statement.setBoolean(5,employe.getIsAdmin());
            statement.setBytes(6,employe.getSalt());
            statement.setString(7,employe.getEmail());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Employe> getAll() {
        List<Employe> employes = new ArrayList<>();
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "Select * FROM Utilisateur";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            try(ResultSet resultSet = statement.executeQuery();) {
                while(resultSet.next()) {
                    Employe e = new Employe(resultSet.getString(3), resultSet.getString(1), resultSet.getString(2), resultSet.getBytes(4),resultSet.getBytes(6), resultSet.getBoolean(5));
                    employes.add(e);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employes;
    }

    @Override
    public Employe get(String email) {
        Employe e = null;
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "Select * FROM Utilisateur WHERE email = ?";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,email);
            try(ResultSet resultSet = statement.executeQuery();) {
                if(resultSet.next()) {
                    e = new Employe(resultSet.getString(3), resultSet.getString(1), resultSet.getString(2), resultSet.getBytes(4), resultSet.getBytes(6), resultSet.getBoolean(5));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return e;
    }

    public static StockageEmployeBdd getInstance(){
        if(instance == null){
            instance = new StockageEmployeBdd();
        }
        return instance;
    }
}
