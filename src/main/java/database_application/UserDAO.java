package database_application;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class UserDAO {
    Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }
// load usrs from database
    public ArrayList<User> loadAllUsers(){
        ArrayList<User> user = new ArrayList();
        user.clear();
        String sql = "SELECT * FROM Uzivatele";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                user.add(new User(rs.getString("Jmeno"), rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
// insert user into database
    public void createUser(String Name, UserRole role){
        String sql = "INSERT INTO Uzivatele (Jmeno, Role) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, Name);
            pstmt.setString(2, role.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //delete user from database
    public void deleteUser(String name){
        String sql = "DELETE FROM Uzivatele WHERE Jmeno = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

// load one user from database based on name
    public Optional<User> loadUser(String name){
        String sql = "SELECT * FROM Uzivatele WHERE Jmeno = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getString("Jmeno"), rs.getString("role"));
                    user.setId(rs.getInt("UzivatelID"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    //táta napsal select
// find customer in database based on name
    public Optional<User> findCustomer(String name){
        String sql = "SELECT * FROM Uzivatele WHERE Jmeno like ? and role = 'ZAKAZNIK'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getString("Jmeno"), rs.getString("role"));
                    user.setId(rs.getInt("UzivatelID"));

                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void loadUsersFromJson(File file) {

    }
}
