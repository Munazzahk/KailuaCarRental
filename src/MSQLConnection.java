import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class MSQLConnection {
    String query = "SELECT * FROM profile LIMIT 5;";
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

    } else System.out.println("The entered mileage is less then the current mileage");}


    public ArrayList<Car> getAllCars() {
        ArrayList<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM car;";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String plateNumber = rs.getString("plate_number");
                Category category = Category.valueOf(rs.getString("category"));
                String brand = rs.getString("brand");
                FuelType fuel = FuelType.valueOf(rs.getString("fuel"));
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

    // Used same method as the one up there - Check if it is ok
    // Tested and it works fine
    // Some things are enums in MYSQL, we need to make enums in JAV - it was Category and FuelType so they are Enums now
    public Car getCar(String platenumber) {
        String query = "SELECT * FROM car WHERE plate_number = ?";
        Car car = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, platenumber); //Prevents SQL injection attacks
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { //To check if there is a car with provided plate number
                    String plateNumber = rs.getString("plate_number");
                    Category category = Category.valueOf(rs.getString("category"));
                    String brand = rs.getString("brand");
                    FuelType fuel = FuelType.valueOf(rs.getString("fuel"));
                    LocalDate registrationDate = rs.getDate("registration_date").toLocalDate();
                    int mileage = rs.getInt("mileage");
                    car = new Car(plateNumber, category, brand, fuel, registrationDate, mileage);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    public Renter getRenter(String licenseId) {
        String query = "SELECT * FROM car WHERE plate_number = ?";
        Renter renter = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, licenseId); //Prevents SQL injection attacks
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { //To check if there is a car with provided plate number
                    String licnseId = rs.getString("license_id");
                    renter = new Renter();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return renter;
    }



}