import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MSQLConnection {

/*    private String database = "jdbc:mysql : //localhost:3306/kailua_rental"; */
/*    private String username;*/
/*    private String password; //change so that it fits your own */
/*    private Connection connection = null;*/

    private static final String DB_URL_rental = "jdbc:mysql://localhost:3306/kailua_rental";
    private static final String DB_URL_employees = "jdbc:mysql://localhost:3306/kailua_employees";
    private static final String ADMIN_USERNAME = "kailua";
    private static final String ADMIN_PASSWORD = "123";

    private Connection connection = null;

    public  MSQLConnection() {
/*        this.username = username;
        this.password = password;*/
        createConnection();
    }


/*    private void createConnection() {
        System.out.println("Connecting to database: " + DB_URL_rental);
*//*        if (connection != null)
            return;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(database, username, password);
        } catch (Exception e) {
            System.out.println("Exception here: " + e.getMessage());
        }*//*

        // Connect to the first database containing usernames and passwords
        try (Connection conn1 = DriverManager.getConnection(DB_URL_rental, ADMIN_USERNAME, ADMIN_PASSWORD)) {
            // Check the password for the given username in the first database
            ArrayList data = logon();
            String username = data.get(0).toString();
            String password = data.get(1).toString();
            boolean passwordMatches = checkPassword(conn1, username, password);
            if (passwordMatches) {
                System.out.println("Password matches for user: " + username);

            } else {
                System.out.println("Password does not match for user: " + username);
            }
        } catch (SQLException e) {
            System.out.println("IM HERE IN CONN1");
            e.printStackTrace();
        }

        // Connect to the second database containing only usernames
        try (Connection conn2 = DriverManager.getConnection(DB_URL_rental, ADMIN_USERNAME, ADMIN_PASSWORD)) {
            // Perform operations on the second database as needed
            // For example, you can retrieve usernames from the 'employees' table in database2
        } catch (SQLException e) {
            System.out.println("IM HERE IN CONN2");
            e.printStackTrace();
        }
    }*/


    private void createConnection() {
        try (Connection conn = DriverManager.getConnection(DB_URL_employees, ADMIN_USERNAME, ADMIN_PASSWORD)) {
            ArrayList<String> loginInfo = logon();
            String username = loginInfo.get(0);
            String password = loginInfo.get(1);

            // Check if the password matches the one in the database
            boolean passwordMatches = checkPassword(conn, username, password);

            if (passwordMatches) {
                System.out.println("Password matches for user: " + username);
                if (connection != null)
                    return;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(DB_URL_rental, username, password);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }

            } else {
                System.out.println("Password does not match for user: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


       public ArrayList logon(){
        String employeeUsername, employeePassword;
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter your username: ");
        employeeUsername = in.nextLine();
        System.out.print("\nPlease enter your password: ");
        employeePassword = in.nextLine();

        ArrayList usernameAndPassword = new ArrayList();
        usernameAndPassword.add(employeeUsername);
        usernameAndPassword.add(employeePassword);
        return usernameAndPassword;
    }


    private static boolean checkPassword(Connection connection, String username, String password) throws SQLException {
        String sql = "SELECT employee_username FROM employees WHERE employee_username = ? ";
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
  /*  public ArrayList<Car> getAllCars() {
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
    }*/




}