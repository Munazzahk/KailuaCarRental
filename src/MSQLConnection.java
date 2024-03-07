import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MSQLConnection {

    private static final String DB_URL_rental = "jdbc:mysql://localhost:3306/kailua_rental";
    private static final String DB_URL_employees = "jdbc:mysql://localhost:3306/kailua_employees";
    private static final String ADMIN_USERNAME = "kailua";
    private static final String ADMIN_PASSWORD = "123";

    private Connection connection = null;

    public  MSQLConnection() {
        createConnection();
    }

    public Connection getConnection() {
        return connection;
    }
    public void createConnection() {
        try (Connection conn = DriverManager.getConnection(DB_URL_employees, ADMIN_USERNAME, ADMIN_PASSWORD)) {
            ArrayList<String> loginInfo = logon();
            String username = loginInfo.get(0);
            String password = loginInfo.get(1);

            // Check if the password matches the one in the database
            boolean passwordMatches = checkPassword(conn, username, password);

            if (passwordMatches) {
                    if (connection != null)
                    return;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(DB_URL_rental, username, password);
                    UI.printWelcome(username);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


       public ArrayList logon(){
        String employeeUsername, employeePassword;
        Scanner in = new Scanner(System.in);
        System.out.print("\n Please enter your username: ");
        employeeUsername = in.nextLine();
        System.out.print("\n Please enter your password: ");
        employeePassword = in.nextLine();

        ArrayList usernameAndPassword = new ArrayList();
        usernameAndPassword.add(employeeUsername);
        usernameAndPassword.add(employeePassword);
        return usernameAndPassword;
    }


    private static boolean checkPassword(Connection connection, String username, String password) throws SQLException {
        String sql = "SELECT employee_username FROM employees WHERE BINARY employee_username = ? ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // If the result set has a next row, the username and password match
            }
        }
    }

   /* public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("EXCEPTION: " + e.getStackTrace());
        }
        connection = null;
    }*/


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
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int maxKm = rs.getInt("max_km");
                double mileage = rs.getDouble("contract_mileage");
                String numberPlate = rs.getString("c.plate_number");
                contract  = new Contract(contractID,licenseID,renterName,address,city, (java.sql.Date) startDate, (java.sql.Date) endDate,maxKm,mileage,numberPlate);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return contract;
    }

    public void updateContract(double km, int contractID) {
        try {
            String query = "UPDATE contract SET contract_mileage = ? WHERE contract_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setDouble(1, km);
            stm.setInt(2, contractID);
            stm.executeUpdate();
            UI.printText("\n Contract updated", ConsoleColor.GREEN);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // this is just to test. delete and replace with proper method
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

    public void createRenter(Renter renter) {

        try  {
            String sql = "INSERT INTO renter (license_id, fullname, address, zip_code, city, state, cellphone, phone, email, license_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, renter.getLicenseId());
            statement.setString(2, renter.getFullName());
            statement.setString(3, renter.getAddress());
            statement.setInt(4, renter.getZipCode());
            statement.setString(5, renter.getCity());
            statement.setString(6, renter.getState());
            statement.setInt(7, renter.getCellPhone());
            statement.setInt(8, renter.getPhone());
            statement.setString(9, renter.getEmail());
            statement.setDate(10, (java.sql.Date) renter.getLicenseDate());

            int rowInserted = statement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("Renter created successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Renter getRenter(int licenseId) {
        Renter renter = null;
        try{
            String query = "SELECT * FROM renter WHERE license_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, licenseId); //Prevents SQL injection attacks
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { //To check if there is a renter with provided plate number
                String fullName = rs.getString("fullname");
                String address = rs.getString("address");
                int zipCode = rs.getInt("zip_code");
                String city = rs.getString("city");
                String state = rs.getString("state");
                int phone = rs.getInt("phone");
                int cellPhone = rs.getInt("cellphone");
                String email = rs.getString("email");
                Date licenseDate = rs.getDate("license_date");
                renter = new Renter(fullName,address,zipCode,city,state,
                        phone,cellPhone,email,licenseId,(java.sql.Date)licenseDate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return renter;
    }

    public   ArrayList<Renter>  getAllRenters() {
        ArrayList<Renter> renters = new ArrayList<>();
            try  {
                String query = "SELECT * FROM renter;";
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String fullName = rs.getString("fullname");
                    String address = rs.getString("address");
                    int zipCode = rs.getInt("zip_code");
                    String city = rs.getString("city");
                    String state = rs.getString("state");
                    int phone = rs.getInt("phone");
                    int cellPhone = rs.getInt("cellphone");
                    String email = rs.getString("email");
                    int licenseId = rs.getInt("license_id");
                    Date licenseDate = rs.getDate("license_date");
                    Renter renter = new Renter(fullName,address,zipCode,city,state,
                            phone,cellPhone,email,licenseId,(java.sql.Date)licenseDate);
                    renters.add(renter);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return renters;
    }
    public   ArrayList<Contract>  getAllContracts() {
        ArrayList<Contract> contracts = new ArrayList<>();
        try  {
            String query =    " SELECT contract_id ,fullname, address, " +
                    "city, c.license_id ,start_date ," +
                    " end_date , c.plate_number ," +
                    " max_km , contract_mileage \n" +
                    "FROM contract c JOIN\n" +
                    "renter r ON  c.license_id = r.license_id;";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int contractID = rs.getInt("contract_id");
                String renterName = rs.getString("fullname");
                String address = rs.getString("address");
                String city = rs.getString("city");
                int licenseId = rs.getInt("license_id");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int maxKm = rs.getInt("max_km");
                double mileage = rs.getDouble("contract_mileage");
                String numberPlate = rs.getString("plate_number");
                Contract contract = new Contract( contractID,licenseId, renterName,
                        address, city,  (java.sql.Date)startDate,
                        (java.sql.Date) endDate,  maxKm,  mileage,  numberPlate);
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    public void deleteContract(int contractID){
        try{
            String query = " DELETE FROM contract WHERE contract_id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,contractID);
            statement.executeUpdate();
            UI.printText("\n Contract Deleted", ConsoleColor.GREEN);
        }catch (Exception e){
            UI.printText("\n Something went wrong, try again later", ConsoleColor.RESET);
        }
    }

    public void createContract(Renter renter, Car car, Date startDate, Date endDate, int maxKm) {
        try {
            String query = "INSERT INTO contract (plate_number, license_id, start_date, end_date, max_km, contract_mileage)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, car.getNumberplate());
            statement.setInt(2, renter.getLicenseId());
            statement.setDate(3, (java.sql.Date) startDate);
            statement.setDate(4, (java.sql.Date) endDate);
            statement.setInt(5, maxKm);
            statement.setNull(6, Types.DECIMAL);
            statement.executeUpdate();

            UI.printText("\n CONTRACT CREATED", ConsoleColor.GREEN);
        } catch (Exception e) {
            UI.printText("\n Something went wrong, try again", ConsoleColor.RED);
        }
    }
    public ArrayList<Car> getCarsByTimePeriod(LocalDate startDate, LocalDate endDate) {
        ArrayList<Car> availableCars = new ArrayList<>();

        String query = "SELECT DISTINCT car.* " +
                "FROM car " +
                "LEFT JOIN contract ON car.plate_number = contract.plate_number " +
                "WHERE " +
                "( " +
                "    car.plate_number NOT IN ( " +
                "        SELECT c2.plate_number " +
                "        FROM contract c2 " +
                "        WHERE ( " +
                "            c2.start_date <= ? " +
                "            AND " +
                "            c2.end_date >= ? " +
                "        ) " +
                "    ) " +
                ")";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(endDate));
            stmt.setDate(2, java.sql.Date.valueOf(startDate));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String plateNumber = rs.getString("plate_number");
                    Category category = Category.valueOf(rs.getString("category"));
                    String brand = rs.getString("brand");
                    FuelType fuel = FuelType.valueOf(rs.getString("fuel"));
                    LocalDate registrationDate = rs.getDate("registration_date").toLocalDate();
                    int mileage = rs.getInt("mileage");
                    Car car = new Car(plateNumber,category,brand,fuel,registrationDate,mileage);
                    availableCars.add(car);
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return availableCars;
    }

}