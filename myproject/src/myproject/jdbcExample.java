package myproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbcExample {
    public static void main(String[] args) {
        try {
            // Step 1: Register the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish the connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HotelManagement", "root", "Vikas@123");

            System.out.println("Connection Created");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Include the connector jar in your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed. Check output console.");
            e.printStackTrace();
        }
    }
}
