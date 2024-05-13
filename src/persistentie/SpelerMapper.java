package persistentie;

import domein.Speler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpelerMapper {

    private static final String INSERT_SPELER = "INSERT INTO ID430262_kingdominoDB.Speler (gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld)"
            + "VALUES (?, ?, ?, ?)";
            
    public void voegToe(Speler speler) 
    {
    	Connectie ssh = new Connectie();
    	try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
                PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) 
        {
            query.setString(1, speler.getGebruikersnaam());
            query.setInt(2, speler.getGeboortejaar());
            query.setInt(3, speler.getAantalGewonnen());
            query.setInt(4, speler.getAantalGespeeld());
            
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    	ssh.closeConnection();
    }

    
    public Speler geefSpeler(String gebruikersnaam) {
    	Connectie ssh =new Connectie();
        Speler speler = null;

        try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID430262_kingdominoDB.Speler WHERE gebruikersnaam = ?")) {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) 
                {
                    int geboortejaar = rs.getInt("geboortejaar");
                    int aantalGewonnen = rs.getInt("aantalGewonnen");
                    int aantalGespeeld = rs.getInt("aantalGespeeld");

                    speler = new Speler(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);               
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        ssh.closeConnection();
        return speler;
    }


    // Return alle spelers in Array
    public Speler[] geefAlleSpelers() {
        Connectie ssh = new Connectie();
        List<Speler> playersList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
             PreparedStatement query = conn.prepareStatement("SELECT * FROM ID430262_kingdominoDB.Speler");
             ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                String gebruikersnaam = rs.getString("gebruikersnaam");
                int geboortejaar = rs.getInt("geboortejaar");
                int aantalGewonnen = rs.getInt("aantalGewonnen");
                int aantalGespeeld = rs.getInt("aantalGespeeld");

                Speler speler = new Speler(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);
                playersList.add(speler);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        ssh.closeConnection();

        // Convert list to array
        Speler[] playersArray = new Speler[playersList.size()];
        playersList.toArray(playersArray);

        return playersArray;
    }

    public  void updateSpeler(Speler speler, Boolean isGewonnen) {
        Speler speler1 = geefSpeler(speler.getGebruikersnaam());

    	Connectie ssh = new Connectie();
    	try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
                PreparedStatement query = conn.prepareStatement("UPDATE ID430262_kingdominoDB.Speler SET aantalGewonnen = ?, aantalGespeeld = ? WHERE gebruikersnaam = ?")) {
            if(isGewonnen) {
                query.setInt(1, speler1.getAantalGewonnen() + 1);
            }
            query.setInt(2, speler1.getAantalGespeeld() + 1);
            query.setString(3, speler.getGebruikersnaam());
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    	ssh.closeConnection();
    }

}

