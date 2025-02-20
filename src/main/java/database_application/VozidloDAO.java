package database_application;


import java.sql.*;
import java.util.ArrayList;

public class VozidloDAO {
    private Connection connection;

    public VozidloDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Vozidlo> nacistVozidla(){
        ArrayList<Vozidlo> vozidla = new ArrayList();
        vozidla.clear();
        String sql = "SELECT SPZ, Znacka, Model FROM Vozidla";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vozidla.add(new Vozidlo(rs.getString("SPZ"), rs.getString("Znacka"), rs.getString("Model")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vozidla;
    }

    public void pridatVozidlo(String SPZ, String znacka, String model){
        String sql = "INSERT INTO Vozidla (SPZ, Znacka, Model) VALUES (?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, SPZ);
            pstmt.setString(2, znacka);
            pstmt.setString(3, model);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void vymazatVozidlo(String SPZ){
        String sql = "DELETE FROM Vozidla WHERE SPZ = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SPZ);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void upravitVozidlo(){

    }

}
