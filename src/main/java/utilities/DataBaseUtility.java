package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseUtility {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CommonUtility.getProperty("URL"), CommonUtility.getProperty("USER"), CommonUtility.getProperty("PASSWORD"));
    }
    public static void executeSelectQuery(String query) {
        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Process result set
                System.out.println("Column 1: " + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void executeUpdateQuery(String query, Object... params) {
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the PreparedStatement
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void executeTransaction(TransactionTask task) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Disable auto-commit
            
            task.execute(conn);
            
            conn.commit(); // Commit the transaction
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback in case of an error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restore auto-commit mode
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FunctionalInterface
    public interface TransactionTask {
        void execute(Connection conn) throws SQLException;
    }
    public static void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
