import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class MSQLConnection {
    private String database = "jdbc:mysql://localhost:3306/kailua_rental";
    private String username = "nanna";
    private String password = "Fandoms33"; //change so that it fits your own
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


    public Contract getContract(int contractID) {
        Contract contract = null;
        try {
            String query =
                    " SELECT contract_id ,fullname, address, " +
                            "city, c.license_id ,start_date ," +
                            " end_date , c.plate_number ," +
                            " max_km , contract_mileage \n" +
                    "FROM contract c JOIN\n" +
                    "renter r ON  c.license_id = r.license_id\n" +
                    "WHERE contract_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, contractID);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int licenseID = rs.getInt("c.license_id");
                String renterName = rs.getString("fullname");
                String address = rs.getString("address");
                String city = rs.getString("city");
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                LocalDate endDate = rs.getDate("end_date").toLocalDate();
                int maxKm = rs.getInt("max_km");
                double mileage = rs.getDouble("contract_mileage");
                String numberPlate = rs.getString("c.plate_number");
                contract  = new Contract(contractID,licenseID,renterName,address,city,startDate,endDate,maxKm,mileage,numberPlate);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return contract;
    }

    public void updateContract(int contractID, int km) {
        Contract contract = getContract(contractID);
        if (km > contract.getMileage())
            try {
                String query = "UPDATE contract SET contract_mileage = ? WHERE contract_id = ?";
                PreparedStatement stm = connection.prepareStatement(query);
                stm.setInt(1, km);
                stm.setInt(2, contractID);
                stm.executeUpdate();

            } catch (Exception e) {
                System.out.println(e.getMessage());

    } else System.out.println("The entered mileage is less then the current mileage");

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