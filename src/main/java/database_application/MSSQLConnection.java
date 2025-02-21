package database_application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MSSQLConnection {
    private static Connection CONN;

    private static String URL;
    private static String USER;
    private static String PASSWORD;




//ai
// this method make connection to the database
    public static Connection getConnection() throws URISyntaxException {
        String jarPath = new File(MSSQLConnection.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        String jarDir = new File(jarPath).getParent();
        File configFile = new File(jarDir, "application.properties");
        try (InputStream input = new FileInputStream(configFile)) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return null;
            }
            prop.load(input);
            URL = prop.getProperty("db.url");
            USER = prop.getProperty("db.user");
            PASSWORD = prop.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (CONN != null) {
            return CONN;
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            CONN = DriverManager.getConnection(URL);
            System.out.println("✅ Připojení k SQL Serveru úspěšné!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Nepodařilo se načíst JDBC driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Chyba při připojování k databázi!");
            e.printStackTrace();
        }
        return CONN;
    }
}
