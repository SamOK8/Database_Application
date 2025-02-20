package database_application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSSQLConnection {
    private static Connection CONN;

    private static final String URL = "jdbc:sqlserver://LEGION5:1433;databaseName=autopujcovna;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
    private static final String USER = "";  // Pokud používáš Windows autentizaci, nevyplňuj
    private static final String PASSWORD = ""; // Nebo nech prázdné, pokud je Windows login


    public static Connection getConnection() {
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
