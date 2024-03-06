import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UI {
    private static Scanner in = new Scanner(System.in);

    public static void printText(String text, ConsoleColor color) {
        System.out.print(color + text + ConsoleColor.RESET);
    }

    // Getting inout methods
    public String getStringInput() {
        String stringInput = in.nextLine();

        while (!isString(stringInput)) {
            printText(" You didn't use a string. Try again: ", ConsoleColor.RED);
            stringInput = in.nextLine();
        }
        return stringInput;
    }

    public static String getStringWithNumbersInput(){
        String stringInput = in.nextLine();
        return stringInput;
    }

    public static int getIntInput() {
        int intInput = Integer.MIN_VALUE; //change, not a good way to do it
        while (intInput == Integer.MIN_VALUE){
            try {
                intInput = in.nextInt();
                in.nextLine(); //Scanner bug
            }
            catch (InputMismatchException e){
                printText(" Input not recognized, please enter a number: ", ConsoleColor.RED);
                in.next();
            }
        }
        return intInput;
    }
    public static double getDoubleInput() {
        double doubleInput = Integer.MIN_VALUE; //change, not a good way to do it
        while (doubleInput == Integer.MIN_VALUE){
            try {
                doubleInput = in.nextDouble();
                in.nextLine(); //Scanner bug
            }
            catch (InputMismatchException e){
                printText(" Input not recognized, please enter a number: ", ConsoleColor.RED);
                in.next();
            }
        }
        return doubleInput;
    }


    public boolean getBooleanInput() {
        String booleanInput = in.nextLine();

        while (!isStringBoolean(booleanInput)) {
            printText(" Boolean input non-identified. Try again: ", ConsoleColor.RED);
            booleanInput = in.nextLine();
        }
        return whichBooleanIsString(booleanInput);
    }

    /// private boolean methods to check data type
    private boolean isStringBoolean(String strBool) {
        if (strBool.equalsIgnoreCase("y")
                || strBool.equalsIgnoreCase("true")
                || strBool.equalsIgnoreCase("yes")
                || strBool.equalsIgnoreCase("n")
                || strBool.equalsIgnoreCase("false")
                || strBool.equalsIgnoreCase("no")) {
            return true;
        } else {return false;}
    }


    private boolean whichBooleanIsString(String strBool) {
        if (strBool.equalsIgnoreCase("y")
                || strBool.equalsIgnoreCase("t")
                || strBool.equalsIgnoreCase("true")
                || strBool.equalsIgnoreCase("yes")) {
            return true;
        } else {return false;}
    }

    private boolean isString(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-ZåäöøæÅÄÖØÆéèêëÉÈÊËíìîïÍÌÎÏóòôõöÓÒÔÕÖúùûüÚÙÛÜÁáÀàÂâÃãÄäÇçÐðÉéÊêËëÍíÎîÏïÑñÓóÔôÕõÖöÚúÛûÜüÝýÿ\\s\\-',.]+$"); // regex - complicated - Pattern for letters and a few special characters

        //check if StringInput matches our letter pattern
        Matcher matcher = pattern.matcher(str);

        return matcher.find(); // Boolean true if there are only letters in stringInput
    }

    public static void printContract(Contract contract){
        printText("\nContract Id: " + contract.getContractId(), ConsoleColor.WHITE);
        printText("\nRenter Name: " + contract.getRenterName(), ConsoleColor.WHITE);
        printText("\nRenter Address: " + contract.getAddress() + ", " + contract.getCity(), ConsoleColor.WHITE);
        printText("\nRenter license : " + contract.getLicenseID(), ConsoleColor.WHITE);
        printText("\nCar license plate: " + contract.getNumberPlate(), ConsoleColor.WHITE);
        printText("\nContract period: " + contract.getStartDate()  + " - " + contract.getEndDate(), ConsoleColor.WHITE);
        printText("\nThe mileage at contract commencement : " + contract.getMileage() + "km", ConsoleColor.WHITE);
        printText("\nMaximum kilometers allowed: " + contract.getMaxKm() + "km", ConsoleColor.WHITE);
    }

    public static void printWelcome(String username){
        String[] parts = username.split("(?<=\\D)(?=\\d)");
        UI.printText("\n WELCOME " + parts[0].toUpperCase(), ConsoleColor.GREEN);


    }


    public void printListOfCars(ArrayList<Car> cars) {
        System.out.println("The system currently has these cars: ");
        for (Car car : cars) {
            printText(" - " + car.getNumberplate(), ConsoleColor.CYAN);
        }
    }

    public void printCarDetails(Car car) {
        if (car != null) {
            System.out.print("Details of car: ");
            printText(car.getNumberplate(), ConsoleColor.CYAN);
            System.out.print("\nBrand: ");
            printText(car.getBrand(), ConsoleColor.CYAN);
            System.out.print("\nCategory: ");
            printText(car.getCategory().toString(), ConsoleColor.CYAN);
            System.out.print("\nFuel type: ");
            printText(car.getFuelType().toString(), ConsoleColor.CYAN);
            System.out.print("\nMileage: ");
            printText(Double.toString(car.getMileage()), ConsoleColor.CYAN);
            System.out.print("\nRegistration date: ");
            printText(car.getRegistrationDate().toString(), ConsoleColor.CYAN);
        } else {
            printText("This car is not in the system!", ConsoleColor.RED);
        }
    }

    public void printBasicContractDetails(Contract contract) {
        if (contract != null) {
            System.out.print("Details of contract nr: ");
            printText(Integer.toString(contract.getContractId()), ConsoleColor.CYAN);
            System.out.print("\nNumber Plate: ");
            printText(contract.getNumberPlate(), ConsoleColor.CYAN);
            System.out.print("\nMileage: ");
            printText(Double.toString(contract.getMileage()), ConsoleColor.CYAN);
            System.out.print("\nStart date: ");
            printText(contract.getStartDate().toString(), ConsoleColor.CYAN);
            System.out.print("\nEnd date: ");
            printText(contract.getEndDate().toString(), ConsoleColor.CYAN);
            System.out.print("\nName of renter: ");
            printText(contract.getRenterName(), ConsoleColor.CYAN);
        } else {
            printText("This car is not in the system!", ConsoleColor.RED);
        }
    }

    //Kan godt slås sammen med den øverste. Den var bare lang så tænkte at der kan være en basic og en full
    public void printFullContractDetails(Contract contract) {
        if (contract != null) {
            printBasicContractDetails(contract);
            System.out.print("\nRenters adress: ");
            printText(contract.getAddress(), ConsoleColor.CYAN);
            System.out.print("\nRenters city: ");
            printText(contract.getCity(), ConsoleColor.CYAN);
            System.out.print("\nMax km: ");
            printText(Integer.toString(contract.getMaxKm()), ConsoleColor.CYAN);
            System.out.print("\nLicense ID: ");
            printText(Integer.toString(contract.getLicenseID()), ConsoleColor.CYAN);
        } else {
            printText("This car is not in the system!", ConsoleColor.RED);
        }
    }

}
