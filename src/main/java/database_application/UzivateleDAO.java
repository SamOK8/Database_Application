package database_application;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class UzivateleDAO {
    Connection connection;

    public UzivateleDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Uzivatel> nacistUzivatele(){
        ArrayList<Uzivatel> uzivatel = new ArrayList();
        uzivatel.clear();
        String sql = "SELECT * FROM Uzivatele";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                uzivatel.add(new Uzivatel(rs.getString("Jmeno"), rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uzivatel;
    }

    public void pridatUzivatele(String Jmeno){
        String sql = "INSERT INTO Uzivatele (Jmeno) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, Jmeno);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void vymazatUzivatele(String Jmeno){
        String sql = "DELETE FROM Uzivatele WHERE Jmeno = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Jmeno);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void upravitUzivatele(){

    }

    public Optional<Uzivatel> nacistUzivatele(String jmeno){
        String sql = "SELECT * FROM Uzivatele WHERE Jmeno = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, jmeno);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Uzivatel(rs.getString("Jmeno"), rs.getString("role")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
