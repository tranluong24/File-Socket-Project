package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/FileSocketDB";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "123456";


    private DatabaseUtil() {

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    System.err.println("Error closing database resource: " + e.getMessage());
                    // e.printStackTrace(); // In stack trace nếu cần debug
                }
            }
        }
    }
}
