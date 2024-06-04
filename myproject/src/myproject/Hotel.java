package myproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Hotel {
	
	 private static final String JDBC_URL = "jdbc:mysql://localhost:3306/HotelManagement";
	 private static final String JDBC_USERNAME = "root";
	 private static final String JDBC_PASSWORD = "Vikas@123";
	
	 private static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
   }
	
	 
   public static void storeCustomerInDatabase(Customer customer) {
       try (Connection connection = getConnection()) {
           String insertCustomerSQL = "INSERT INTO customer (name, phone_number, aadhaar_card, checkin_date, checkout_date) VALUES (?, ?, ?, ?, ?)";

           try (PreparedStatement preparedStatement = connection.prepareStatement(insertCustomerSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

               preparedStatement.setString(1, customer.getName());
               preparedStatement.setString(2, customer.getPhoneNumber());
               preparedStatement.setString(3, customer.getAadhaarCard());
               preparedStatement.setDate(4, customer.getCheckinDate());
               preparedStatement.setDate(5, customer.getCheckoutDate());

               int rowsAffected = preparedStatement.executeUpdate();
               if (rowsAffected > 0) {
                   System.out.println("Customer information stored successfully.");
                   try (var rs = preparedStatement.getGeneratedKeys()) {
                       if (rs.next()) {
                           int customerId = rs.getInt(1);
                           storeRoomPreferences(customerId, customer.getCheckinDate(), customer.getCheckoutDate(), customer.getRoomPreferences());
                           storeServices(customerId, customer.getCheckinDate(), customer.getCheckoutDate(), customer.getServices());
                       }
                   }
               }
           }
       } catch (SQLException e) {
           System.err.println("Database error: " + e.getMessage());
           e.printStackTrace();
       }
   }

   public static void storeRoomPreferences(int customerId, java.sql.Date checkinDate, java.sql.Date checkoutDate, String[] roomPreferences) {
       String insertRoomPreferenceSQL = "INSERT INTO room (customer_id, date, room_type) VALUES (?, ?, ?)";

       try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertRoomPreferenceSQL)) {

           long diff = checkoutDate.getTime() - checkinDate.getTime();
           long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

           for (int i = 0; i <= days; i++) {
               Date date = new Date(checkinDate.getTime() + (i * 24 * 60 * 60 * 1000));
               java.sql.Date sqlDate = new java.sql.Date(date.getTime());

               String roomType = roomPreferences[i];

               preparedStatement.setInt(1, customerId);
               preparedStatement.setDate(2, sqlDate);
               preparedStatement.setString(3, roomType);

               preparedStatement.addBatch();
           }

           int[] rowsAffected = preparedStatement.executeBatch();
           System.out.println("Room preferences stored successfully for " + rowsAffected.length + " days.");

       } catch (SQLException e) {
           System.err.println("Database error: " + e.getMessage());
           e.printStackTrace();
       }
   }

   public static void storeServices(int customerId, java.sql.Date checkinDate, java.sql.Date checkoutDate, String[][] services) {
       String insertServiceSQL = "INSERT INTO services (customer_id, date, service_type) VALUES (?, ?, ?)";

       try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertServiceSQL)) {

           long diff = checkoutDate.getTime() - checkinDate.getTime();
           long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

           for (int i = 0; i <= days; i++) {
               Date date = new Date(checkinDate.getTime() + (i * 24 * 60 * 60 * 1000));
               java.sql.Date sqlDate = new java.sql.Date(date.getTime());

               for (String service : services[i]) {
                   preparedStatement.setInt(1, customerId);
                   preparedStatement.setDate(2, sqlDate);
                   preparedStatement.setString(3, service);

                   preparedStatement.addBatch();
               }
           }

           int[] rowsAffected = preparedStatement.executeBatch();
           System.out.println("Services stored successfully for " + rowsAffected.length + " entries.");

       } catch (SQLException e) {
           System.err.println("Database error: " + e.getMessage());
           e.printStackTrace();
       }
   }

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       ArrayList<Customer> customers = new ArrayList<>();

       while (true) {
           System.out.println("Welcome to our residency");

           System.out.println("Enter your name Please:");
           String name = scanner.nextLine();

           System.out.println("Enter your phone number:");
           String phoneNumber = scanner.nextLine();

           System.out.println("Enter your Aadhaar card number:");
           String aadhaarCard = scanner.nextLine();

           System.out.println("Enter check-in date (yyyy-MM-dd):");
           String checkinDateStr = scanner.nextLine();

           System.out.println("Enter check-out date (yyyy-MM-dd):");
           String checkoutDateStr = scanner.nextLine();

           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.sql.Date checkinDate = null;
           java.sql.Date checkoutDate = null;

           try {
               checkinDate = new java.sql.Date(dateFormat.parse(checkinDateStr).getTime());
               checkoutDate = new java.sql.Date(dateFormat.parse(checkoutDateStr).getTime());

               // Validate that checkout date is after check-in date
               if (!checkoutDate.after(checkinDate)) {
                   System.out.println("Check-out date must be after check-in date.");
                   continue;
               }

               // Calculate the number of days between check-in and check-out dates
               long diff = checkoutDate.getTime() - checkinDate.getTime();
               long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;

               // Get room preferences for each day of the stay
               String[] roomPreferences = new String[(int) days];
               for (int i = 0; i < days; i++) {
                   Date date = new Date(checkinDate.getTime() + (i * 24 * 60 * 60 * 1000));
                   System.out.println("Enter room type for " + dateFormat.format(date) + " (Gold/Silver/Bronze):");
                   roomPreferences[i] = scanner.nextLine();
               }

               // Get service preferences for each day of the stay
               String[][] services = new String[(int) days][];
               for (int i = 0; i < days; i++) {
                   Date date = new Date(checkinDate.getTime() + (i * 24 * 60 * 60 * 1000));
                   System.out.println("Enter services for " + dateFormat.format(date) + " (comma separated, e.g., spa,swimming,pool,breakfast,lunch,dinner):");
                   String servicesStr = scanner.nextLine();
                   services[i] = servicesStr.split(",");
               }

               // Create a new Customer object
               Customer customer = new Customer(0, name, phoneNumber, aadhaarCard, checkinDate, checkoutDate, roomPreferences, services);
               customers.add(customer);

               // Store the customer in the database
               storeCustomerInDatabase(customer);

               System.out.println("Do you want to add another customer? (yes/no)");
               String response = scanner.nextLine();
               if (!response.equalsIgnoreCase("yes")) {
                   break;
               }

           } catch (ParseException e) {
               System.out.println("Invalid date format. Please use yyyy-MM-dd.");
           }
       }
       scanner.close();
   }


}
