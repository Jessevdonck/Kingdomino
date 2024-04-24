package persistentie;

import domein.DominoTegel;
import domein.Landschap;
import util.LandschapType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DominoTegelMapper {

    // Deze methode haalt alle informatie over de kaarten uit de databank, maakt er objecten van en stopt ze in een lijst
    // De kaarten worden hier NIET geschud meegegeven
    public List<DominoTegel> geefAlleDominoTegels() {
        Connectie ssh = new Connectie();
        List<DominoTegel> dominoTegelList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
             PreparedStatement query = conn.prepareStatement("SELECT * FROM ID430262_kingdominoDB.DominoTegel");
             ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                String landschapType1 = rs.getString("landschapType1");
                String landschapType2 = rs.getString("landschapType2");
                int kronenAantal1 = rs.getInt("kronenAantal1");
                int kronenAantal2 = rs.getInt("kronenAantal2");
                int tegelNummer = rs.getInt("tegelNummer");
                String fotoAchterkant = rs.getString("fotoAchterkant");
                String fotoVoorkant = rs.getString("fotoVoorkant");

                LandschapType landschapType1Enum = LandschapType.valueOf(landschapType1);
                LandschapType landschapType2Enum = LandschapType.valueOf(landschapType2);
                Landschap landschap1 = new Landschap(landschapType1Enum, kronenAantal1);
                Landschap landschap2 = new Landschap(landschapType2Enum, kronenAantal2);

                DominoTegel dominoTegel = new DominoTegel(landschap1, landschap2, tegelNummer, fotoAchterkant, fotoVoorkant);
                dominoTegelList.add(dominoTegel);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        ssh.closeConnection();

        return dominoTegelList;
    }

}
