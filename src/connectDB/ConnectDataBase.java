package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDataBase {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLiCoffee;encrypt=true;trustServerCertificate=true;characterEncoding=UTF-8";
    private static final String USER = "sa";
    private static final String PASSWORD = "sapassword";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Connect to SQL Server
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to SQL Server successfully!");

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to SQL Server!");
            e.printStackTrace();
        }
        return conn;
    }
}