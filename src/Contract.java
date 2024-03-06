import java.time.LocalDate;
import java.util.Date;

public class Contract {
    private int contractId;
    private String renterName;
    private String address;
    private String city;
    private int licenseID;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxKm;
    private double mileage;
    private String numberPlate;


    public Contract( int contractId,int licenseID, String renterName, String address, String city, LocalDate startDate,
                    LocalDate endDate, int maxKm, double mileage, String numberPlate){
        this.contractId = contractId;
        this.renterName =renterName;
        this.address = address;
        this.city = city;
        this.licenseID =licenseID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxKm = maxKm;
        this.mileage = mileage;
        this. numberPlate = numberPlate;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "renterName='" + renterName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", licenseID=" + licenseID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", maxKm=" + maxKm +
                ", mileage=" + mileage +
                ", numberPlate='" + numberPlate + '\'' +
                '}';
    }
}
