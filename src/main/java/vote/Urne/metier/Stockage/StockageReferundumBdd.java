package vote.Urne.metier.Stockage;

import vote.Urne.metier.Sondage;
import vote.Urne.metier.Stockage.Conf.SQLUtils;
import vote.Urne.metier.Vote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockageReferundumBdd implements Stockage<Sondage,String> {

    private static StockageReferundumBdd instance = null;

    private StockageReferundumBdd(){
    }

    @Override
    public void enregistrer(Sondage sondage) {
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "INSERT INTO Referundum(Consigne,choix1,choix2,resultat,uuid,createur) VALUES(?,?,?,?,?,?)";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,sondage.getConsigne());
            statement.setString(2,sondage.getChoix1());
            statement.setString(3,sondage.getChoix2());

            if(sondage.getResultat() == null){
                statement.setNull(4, Types.NULL);
            }
            else {
                statement.setInt(4, sondage.getResultat());
            }
            statement.setString(5,sondage.getUuid().toString());
            statement.setString(6,sondage.getEmailCreateur());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Sondage> getAllSortedAndFinished(){
        List<Sondage> sondages = new ArrayList<>();
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "Select * FROM Referundum WHERE resultat IS NOT NULL ORDER BY dateCreation DESC";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            try(ResultSet resultSet = statement.executeQuery();) {
                while(resultSet.next()) {
                    Sondage e = new Sondage(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(7),resultSet.getString(1));
                    e.setResultat(resultSet.getInt(5));

                    sondages.add(e);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sondages;
    }

    @Override
    public void supprimer(String uuid) {
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "DELETE FROM Referundum WHERE uuid = ?";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,uuid);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mettreAJour(Sondage sondage) {
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "UPDATE Referundum SET uuid = ?, Consigne = ?, choix1 = ?, choix2 = ?, resultat= ? WHERE uuid = ?";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,sondage.getUuid().toString());
            statement.setString(6,sondage.getUuid().toString());
            statement.setString(2,sondage.getConsigne());
            statement.setString(3,sondage.getChoix1());
            statement.setString(4,sondage.getChoix2());
            statement.setInt(5,sondage.getResultat());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Sondage> getAll() {
        List<Sondage> sondages = new ArrayList<>();
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "Select * FROM Referundum";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            try(ResultSet resultSet = statement.executeQuery();) {
                while(resultSet.next()) {
                    Sondage e = new Sondage(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(7));
                    e.setResultat(resultSet.getInt(5));
                    sondages.add(e);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sondages;
    }

    @Override
    public Sondage get(String uuid) {
        Sondage s = null;
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "Select * FROM Referundum WHERE uuid = ?";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,uuid);
            try(ResultSet resultSet = statement.executeQuery();) {
                resultSet.next();
                s = new Sondage(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return s;
    }

    public static StockageReferundumBdd getInstance(){
        if(instance == null){
            instance = new StockageReferundumBdd();
        }
        return instance;
    }
}
