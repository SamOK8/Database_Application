package database_application;

import java.sql.*;
import java.util.ArrayList;

public class PujceniDAO {
    private Connection connection;

    public PujceniDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Pujceni> nacistPujceni(){
        ArrayList<Pujceni> pujceni = new ArrayList();
        pujceni.clear();
        String sql = "SELECT JmenoZakaznika, PrijmeniZakaznika, CisloDokladuZakaznika FROM pujceni";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pujceni.add(new Pujceni(rs.getString("JmenoZakaznika"), rs.getString("PrijmeniZakaznika"), rs.getString("CisloDokladuZakaznika")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pujceni;
    }

    public void pridatPujceni(String jmenoZakaznika, String prijmeniZakaznika, String cisloDokladuZakaznika) {
        String sql = "INSERT INTO pujceni (JmenoZakaznika, PrijmeniZakaznika, CisloDokladuZakaznika) VALUES (?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, jmenoZakaznika);
            pstmt.setString(2, prijmeniZakaznika);
            pstmt.setString(3, cisloDokladuZakaznika);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void vymazatPujceni(String jmenoZakaznika, String prijmeniZakaznika){
        String sql = "DELETE FROM pujceni WHERE JmenoZakaznika =? AND PrijmeniZakaznika =?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, jmenoZakaznika);
            pstmt.setString(2, prijmeniZakaznika);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void upravitPujceni(){

    }
}
