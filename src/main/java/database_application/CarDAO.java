package database_application;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CarDAO {
    private Connection connection;

    public CarDAO(Connection connection) {
        this.connection = connection;
    }


    //ai napsalo zaklad statementu
    // load all cars from database
    public ArrayList<Car> loadAllCars(){
        ArrayList<Car> vozidla = new ArrayList();
        vozidla.clear();
        String sql = "SELECT VozidloID, SPZ, Znacka, Model FROM Vozidla";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Car car = new Car(rs.getString("SPZ"), rs.getString("Znacka"), rs.getString("Model"));
                car.setId(rs.getInt("VozidloID"));
                vozidla.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vozidla;
    }
//insert car into database
    public void createCar(String SPZ, String znacka, String model){
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
    //delete car from database
    public void deleteCar(String SPZ){
        String sql = "DELETE FROM Vozidla WHERE SPZ = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SPZ);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//ai napsalo vetsinu
// load cars from json file
    public String loadCarsFromJson(File file) {
        try(FileInputStream fis = new FileInputStream(file)){
             JSONTokener tokener = new JSONTokener(fis);
             JSONObject jsonObject = new JSONObject(tokener);
             JSONArray carsArray = jsonObject.getJSONArray("cars");

             ArrayList<Car> vozidla = loadAllCars();

            for (int i = 0; i < carsArray.length(); i++) {
                JSONObject obj = carsArray.getJSONObject(i);
                for (Car vo : vozidla) {
                    if (vo.getSPZ().equals(obj.getString("SPZ"))) {
                        return "There can't be duplicate SPZ";
                    }
                }
                createCar(obj.getString("SPZ"), obj.getString("Znacka"), obj.getString("Model"));
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return "cars loaded";
    }
}
