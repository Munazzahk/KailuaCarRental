import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MSQLConnection {
    private String database = "jdbc:mysql://localhost:3306/kailua_rental";
    private String username = "";
    private String password = ""; //change so that it fits your own
    private Connection connection = null;

    public  MSQLConnection() {
        createConnection();
    }


    private void createConnection() {
        if (connection != null)
            return;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(database, username, password);
        } catch (Exception e) {
            System.out.println("Exception here: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("EXCEPTION: " + e.getStackTrace());
        }
        connection = null;
    }

    // this is just to test. delete and replace with proper method
    // Some things are enums in MYSQL, just used strings rn, we need to make enums in JAVA
    public ArrayList<Car> getAllCars() {
        ArrayList<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM car;";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String plateNumber = rs.getString("plate_number");
                String category = rs.getString("category");
                String brand = rs.getString("brand");
                String fuel = rs.getString("fuel");
                LocalDate registrationDate = rs.getDate("registration_date").toLocalDate();
                int mileage = rs.getInt("mileage");
                Car car = new Car(plateNumber,category,brand,fuel,registrationDate,mileage);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

}