package vote.Urne.metier.Stockage;

import vote.Urne.metier.Employe;
import vote.Urne.metier.Stockage.Conf.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StockageEmployeBdd implements Stockage<Employe,String> {

    @Override
    public void enregistrer(Employe employe) {
        /*Employe e = null;
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "INSER INTO Utilisateur VALUES()";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,email);
            try(ResultSet resultSet = statement.executeQuery();) {
                resultSet.next();
                e = new Employe(resultSet.getString(3),resultSet.getString(1),resultSet.getString(2),resultSet.getBytes(4),resultSet.getBoolean(5));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return e;*/
    }

    @Override
    public void supprimer(String email) {

    }

    @Override
    public void mettreAJour(Employe employe) {

    }

    @Override
    public List<Employe> getAll() {
        return null;
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
                resultSet.next();
                e = new Employe(resultSet.getString(3),resultSet.getString(1),resultSet.getString(2),resultSet.getBytes(4),resultSet.getBoolean(5));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return e;
    }
}
