package vote.Urne.metier.Stockage;

import vote.Urne.Sondage;
import vote.Urne.metier.Stockage.Conf.SQLUtils;
import vote.Urne.metier.Vote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockageVoteBdd implements Stockage<Vote,String[]> {
    private static StockageVoteBdd instance = null;

    private StockageVoteBdd(){
    }

    @Override
    public void enregistrer(Vote vote) {
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "INSERT INTO A_Voter(referundumUuid,UtilisateurEmail) VALUES(?,?)";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,vote.getUuidReferundum());
            statement.setString(2,vote.getEmailEmploye());

            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(String[] primaryKeys) {
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "DELETE FROM A_Voter WHERE referundumUuid = ? AND UtilisateurEmail = ?";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,primaryKeys[0]);
            statement.setString(2,primaryKeys[1]);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mettreAJour(Vote v) {
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "UPDATE A_Voter SET referundumUuid = ? AND UtilisateurEmail = ? WHERE referundumUuid = ? AND UtilisateurEmail = ?";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,v.getUuidReferundum());
            statement.setString(3,v.getUuidReferundum());

            statement.setString(2,v.getEmailEmploye());
            statement.setString(4,v.getEmailEmploye());

            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Vote> getAll() {
        List<Vote> votes = new ArrayList<>();
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "Select * FROM A_Voter";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            try(ResultSet resultSet = statement.executeQuery();) {
                while(resultSet.next()) {
                    Vote v = new Vote(resultSet.getString(2),resultSet.getString(1));
                    votes.add(v);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return votes;
    }

    @Override
    public Vote get(String[] primaryKeys) {
        Vote s = null;
        SQLUtils sql = SQLUtils.getInstance();
        Connection c = sql.getConnection();

        String requete = "Select * FROM A_Voter WHERE referundumUuid = ? AND UtilisateurEmail = ?";
        try(PreparedStatement statement = c.prepareStatement(requete, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1,primaryKeys[0]);
            statement.setString(2,primaryKeys[1]);

            try(ResultSet resultSet = statement.executeQuery();) {
                if(resultSet.next()) {
                    s = new Vote(resultSet.getString(1), resultSet.getString(2));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return s;
    }

    public static StockageVoteBdd getInstance(){
        if(instance == null){
            instance = new StockageVoteBdd();
        }
        return instance;
    }
}
