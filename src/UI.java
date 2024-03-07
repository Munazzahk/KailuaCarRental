import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static String getStringInput() {
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

    private static boolean isString(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-ZåäöøæÅÄÖØÆéèêëÉÈÊËíìîïÍÌÎÏóòôõöÓÒÔÕÖúùûüÚÙÛÜÁáÀàÂâÃãÄäÇçÐðÉéÊêËëÍíÎîÏïÑñÓóÔôÕõÖöÚúÛûÜüÝýÿ\\s\\-',.]+$"); // regex - complicated - Pattern for letters and a few special characters

        //check if StringInput matches our letter pattern
        Matcher matcher = pattern.matcher(str);

        return matcher.find(); // Boolean true if there are only letters in stringInput
    }

    public static void printContract(Contract contract){
        printText("\nDetails of contract nr: ", ConsoleColor.WHITE);
        printText(String.valueOf(contract.getContractId()), ConsoleColor.CYAN);
        printText("\nRenter Name: " , ConsoleColor.WHITE);
        printText(contract.getRenterName(), ConsoleColor.CYAN);
        printText("\nRenter Address: ", ConsoleColor.WHITE);
        printText(contract.getAddress() + ", " + contract.getCity(), ConsoleColor.CYAN);
        printText("\nRenter license : ", ConsoleColor.WHITE);
        printText(String.valueOf(contract.getLicenseID()), ConsoleColor.CYAN);
        printText("\nCar license plate: ", ConsoleColor.WHITE);
        printText(contract.getNumberPlate(), ConsoleColor.CYAN);
        printText("\nContract period: ", ConsoleColor.WHITE);
        printText( contract.getStartDate()  + " - " + contract.getEndDate(), ConsoleColor.CYAN);
        printText("\nThe mileage at contract commencement : ",ConsoleColor.WHITE);
        printText(contract.getMileage() + "km", ConsoleColor.CYAN);
        printText("\nMaximum kilometers allowed: ",ConsoleColor.WHITE);
        printText(contract.getMaxKm() + "km", ConsoleColor.CYAN);
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
            printText("Details of car: " + car.getNumberplate(), ConsoleColor.CYAN);
            printText("\nBrand: " + car.getBrand(), ConsoleColor.CYAN);
            printText("\nCategory: " + car.getCategory().toString(), ConsoleColor.CYAN);
            printText("\nFuel type: " + (car.getFuelType().toString()), ConsoleColor.CYAN);
            printText("\nMileage: "+ Double.toString(car.getMileage()), ConsoleColor.CYAN);
            printText("\nRegistration date: " + (car.getRegistrationDate().toString()), ConsoleColor.CYAN);
        } else {
            printText("This car is not in the system!", ConsoleColor.RED);
        }
    }

    public static Date getDate(){
        String licenseDateString;
        Date licenseDate;
        do {
            UI.printText(" License date (YYYY-MM-DD): ", ConsoleColor.WHITE);
            licenseDateString = getStringWithNumbersInput();
            licenseDate = convertToSqlDate(licenseDateString);
        } while (licenseDate == null);
        return licenseDate;
    }

    public static String getEmail(){
        String email = null;
        do {
            UI.printText(" Email: ", ConsoleColor.WHITE);
            email = getStringWithNumbersInput();
        } while (!checkValidEmail(email));
        return email;
    }
    private static boolean checkValidEmail(String email){
        if (email.contains("@")) {
            return  true;
        } else {
            System.out.println(" invalid email");
            return false;
        }
    }

    public static Date convertToSqlDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(dateString);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            UI.printText("Wrong input format \n", ConsoleColor.RED);
            return null;
        }
    }
}
