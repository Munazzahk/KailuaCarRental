import java.time.LocalDate;

public class Car {
    private String brand;
    private Category category;
    private FuelType fuelType; //Kan også laves som enum. Søgte og der er dog alligevel en del
    private String numberplate;
    private LocalDate registrationDate;
    private double mileage; //Km bilen har kørt


    public Car(String plateNumber, Category category, String brand, FuelType fuel, LocalDate registrationDate, int mileage) {
    this.numberplate = plateNumber;
    this.category = category;
    this.brand = brand;
    this.fuelType = fuel;
    this.registrationDate = registrationDate;
    this.mileage = mileage;

    }

    public String getNumberplate() {
        return numberplate;
    }

    public Category getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public double getMileage() {
        return mileage;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", numberplate='" + numberplate + '\'' +
                ", registrationDate=" + registrationDate +
                ", mileage=" + mileage +
                '}';
    }
}
