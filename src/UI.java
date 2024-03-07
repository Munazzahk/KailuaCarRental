import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UI {
    private static Scanner in = new Scanner(System.in);

    public static void printText(String text, ConsoleColor color) {
        System.out.print(color + text + ConsoleColor.RESET);
    }

    public static LocalDate getDateInput(String dateString) throws ParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Parse the input string to a LocalDate object
            LocalDate localDate = LocalDate.parse(dateString, dateFormatter);
            return localDate;
        } catch (Exception e) {
            // If parsing fails, throw an error indicating invalid date format
            throw new ParseException("Invalid date format. Please enter a date in the format yyyy-MM-dd", 0);
        }
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


    public static boolean getBooleanInput() {
        String booleanInput = in.nextLine();

        while (!isStringBoolean(booleanInput)) {
            printText(" Boolean input non-identified. Try again: ", ConsoleColor.RED);
            booleanInput = in.nextLine();
        }
        return whichBooleanIsString(booleanInput);
    }

    /// private boolean methods to check data type
    private static boolean isStringBoolean(String strBool) {
        if (strBool.equalsIgnoreCase("y")
                || strBool.equalsIgnoreCase("true")
                || strBool.equalsIgnoreCase("yes")
                || strBool.equalsIgnoreCase("n")
                || strBool.equalsIgnoreCase("false")
                || strBool.equalsIgnoreCase("no")) {
            return true;
        } else {return false;}
    }


    private static boolean whichBooleanIsString(String strBool) {
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


    public static void printListOfCars(ArrayList<Car> cars) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            printText("\n" + (i + 1) + ") " + car.getBrand(), ConsoleColor.CYAN);
        }
        printText("\n", ConsoleColor.WHITE);
    }

    public static void printListOfRenters(ArrayList<Renter> renters) {
        for (int i = 0; i < renters.size(); i++) {
            Renter renter = renters.get(i);
            printText("\n" + (i + 1) + ") " + renter.getFullName(), ConsoleColor.CYAN);
        }
        printText("\n", ConsoleColor.WHITE);
    }
    public static void printListOfContracts(ArrayList<Contract> contracts) {
        for (int i = 0; i < contracts.size(); i++) {
            Contract contract = contracts.get(i);
            printText("\n ContractID: " + contract.getContractId() +" Car: " + contract.getNumberPlate() + " Renter name: " + contract.getRenterName(), ConsoleColor.CYAN);
        }
        printText("\n", ConsoleColor.WHITE);
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
    public static LocalDate getStartDate() {
        printText("\n Enter the start of the contract period: ", ConsoleColor.RESET);
        LocalDate startDate = enterDate();
        return startDate;
    }

    public static LocalDate getEndDate() {
        printText("\n Enter the end of the contract period: ", ConsoleColor.RESET);
        LocalDate endDate = enterDate();
        return endDate;
    }


    public static LocalDate enterDate() {
        int day = 0;
        int month = 0;
        int year = 0;

        do {
            try {
                System.out.print("\n Please give the day in format 'DD': ");
                int inputDay = in.nextInt();
                in.nextLine(); // Consume the newline character left in the buffer

                if (inputDay < 1 || inputDay > 31) {
                    System.out.println(" Invalid day. Please ensure the day is between 1 and 31.");
                    continue; // Invalid day, loop again
                }
                day = inputDay;

                System.out.print(" Please give the month in format 'MM': ");
                int inputMonth = in.nextInt();
                in.nextLine(); // Consume the newline character left in the buffer

                if (inputMonth < 1 || inputMonth > 12) {
                    System.out.println(" Invalid month. Please ensure the month is between 1 and 12.");
                    continue; // Invalid month, loop again
                }
                month = inputMonth;

                System.out.print(" Please give the year in format 'YYYY': ");
                int inputYear = in.nextInt();
                in.nextLine(); // Consume the newline character left in the buffer

                // should have someway to ensure we can't make a contract back in time
                if (inputYear < 1950 || inputYear > 2030) {
                    System.out.println(" Invalid year. Please ensure the year is between 1950 and 2030.");
                    continue; // Invalid year, loop again
                }
                year = inputYear;

            } catch (InputMismatchException e) {
                System.out.println(" Invalid input. Please enter numeric values for the date.");
                in.nextLine(); // Clear the input buffer
            }
        } while (year == 0); // runs until a valid date is entered
        return LocalDate.of(year, month, day);
    }

    public static void printCategories(){
        int count = 0;
        for (Category c : Category.values()){
            count += 1;
            printText("\n " + count + ") "+c, ConsoleColor.CYAN);
        }
        printText("\n", ConsoleColor.WHITE);
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
}
