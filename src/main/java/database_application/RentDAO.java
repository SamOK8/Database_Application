package database_application;

import java.sql.*;
import java.util.ArrayList;

public class RentDAO {
    private Connection connection;

    public RentDAO(Connection connection) {
        this.connection = connection;
    }
//insert rent into database
    public Rent rentACar(Rent rent) {
        String sql = "INSERT INTO pujceni (UzivatelID, VozidloID, od, do) VALUES (?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, rent.getCustomer().getId());
            pstmt.setInt(2, rent.getCar().getId());
            pstmt.setDate(3, new Date(System.currentTimeMillis()));
            pstmt.setDate(4, new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3));
            pstmt.executeUpdate();
            return rent;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //táta napsal select
// load rents from database
    public ArrayList<Rent> loadAllRentals() {
        ArrayList<Rent> rents = new ArrayList();
        rents.clear();
        String sql = "SELECT PujceniID, u.Jmeno as customerName, v.SPZ as SPZ, CisloDokladuZakaznika, od, do FROM pujceni p JOIN Uzivatele u ON p.UzivatelID = u.UzivatelID JOIN Vozidla v ON p.VozidloID = v.VozidloID";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(rs.getString("customerName"), "ZAKAZNIK");
                user.setDrivingLicenseCardNumber(rs.getString("CisloDokladuZakaznika"));
                Car car = new Car(rs.getString("SPZ"));
                Rent rent = new Rent(user, car);
                rent.setId(rs.getInt("PujceniID"));
                rent.setSince(rs.getDate("od"));
                rent.setUntil(rs.getDate("do"));
                rents.add(rent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rents;
    }

//delete rent from database
    public void deleteRent(Rent rent) {
        String sql = "DELETE FROM pujceni WHERE PujceniID =?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, rent.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
