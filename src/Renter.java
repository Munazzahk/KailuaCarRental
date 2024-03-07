import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Renter {
    private String fullName;
    private String address;
    private int zip_code; //Postnummer
    private String city;
    private String state;
    private int phone;
    private int cellPhone;
    private String email;
    private int license_id;
    private Date license_date;

    public Renter(String fullName, String address, int zip_code, String city, int phone,
                  String email, int license_id, Date license_date, String state, int cellPhone) {
        this.fullName = fullName;
        this.address = address;
        this.zip_code = zip_code;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.license_id = license_id;
        this.license_date = license_date;
        this.state = state;
        this.cellPhone = cellPhone;

        setFullName(fullName);
        setAddress(address);
        setZip_code(zip_code);
        setCity(city);
        setPhone(phone);
        setEmail(email);
        setLicense_id(license_id);
        setLicense_date(license_date);
        setState(state);
        setCellPhone(cellPhone);
    }

    public Renter() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLicense_id() {
        return license_id;
    }

    public void setLicense_id(int license_id) {
        this.license_id = license_id;
    }


    public Date getLicense_date() {
        return license_date;
    }

    public void setLicense_date(Date license_date) {
        this.license_date = license_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(int cellPhone) {
        this.cellPhone = cellPhone;
    }

    public static Renter userInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter full name");
        String fullName = scanner.nextLine();

        System.out.println("Enter Address");
        String address = scanner.nextLine();

        System.out.println("Enter zipcode");
        int zipCode = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter city");
        String city = scanner.nextLine();

        System.out.println("Enter state");
        String state = scanner.nextLine();

        System.out.println("Enter phone-number");
        int phone = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter cellphone number");
        int cellPhone = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Email");
        String email = scanner.nextLine();

        if (email.contains("@")) {
            System.out.println("Valid email");
        } else {
            System.out.println(" invalid email");
        }

        System.out.println("Enter driversLicence");
        int license_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter License date (YYYY-MM-DD)");
        String licenseDateString = scanner.nextLine();

        java.sql.Date license_date = null;
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(licenseDateString);
            license_date = new java.sql.Date(utilDate.getTime());
            System.out.println(license_date);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
        }

        scanner.close();


        Renter newRenter = new Renter(fullName, address, zipCode, city, phone, email, license_id, license_date, state, cellPhone);
        return newRenter;
    }

    public void insertIntoDatabase() {
        String url = "jdbc:mysql://localhost:3306/kailua_rental";
        String username = "kailua";
        String password = "123";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO renter (license_id, fullname, address, zip_code, city, state, cellphone, phone, email, license_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, license_id);
            statement.setString(2, fullName);
            statement.setString(3, address);
            statement.setInt(4, zip_code);
            statement.setString(5, city);
            statement.setString(6, state);
            statement.setInt(7, cellPhone);
            statement.setInt(8, phone);
            statement.setString(9, email);
            statement.setDate(10, license_date);

            int rowInserted = statement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("Renter information inserted successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}