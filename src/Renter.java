
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Renter {
    private String fullName;
    private String address;
    private int zipCode; //Postnummer
    private String city;
    private String state;
    private int phone;
    private int cellPhone;
    private String email;
    private int licenseId;
    private Date licenseDate;

    public Renter(String fullName, String address, int zipCode, String city, int phone,
                  String email, int licenseId, Date licenseDate, String state, int cellPhone) {
        setFullName(fullName);
        setAddress(address);
        setZipCode(zipCode);
        setCity(city);
        setPhone(phone);
        setEmail(email);
        setLicenseId(licenseId);
        setLicenseDate(licenseDate);
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

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zip_code) {
        this.zipCode = zip_code;
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

    public int getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(int licenseId) {
        this.licenseId = licenseId;
    }


    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
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




}