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
        printText("\n Details of contract nr: ", ConsoleColor.WHITE);
        printText(String.valueOf(contract.getContractId()), ConsoleColor.CYAN);
        printText("\n Renter Name: " , ConsoleColor.WHITE);
        printText(contract.getRenterName(), ConsoleColor.CYAN);
        printText("\n Renter Address: ", ConsoleColor.WHITE);
        printText(contract.getAddress() + ", " + contract.getCity(), ConsoleColor.CYAN);
        printText("\n Renter license : ", ConsoleColor.WHITE);
        printText(String.valueOf(contract.getLicenseID()), ConsoleColor.CYAN);
        printText("\n Car license plate: ", ConsoleColor.WHITE);
        printText(contract.getNumberPlate(), ConsoleColor.CYAN);
        printText("\n Contract period: ", ConsoleColor.WHITE);
        printText( contract.getStartDate()  + " - " + contract.getEndDate(), ConsoleColor.CYAN);
        printText("\n The mileage at contract commencement : ",ConsoleColor.WHITE);
        printText(contract.getMileage() + "km", ConsoleColor.CYAN);
        printText("\n Maximum kilometers allowed: ",ConsoleColor.WHITE);
        printText(contract.getMaxKm() + "km", ConsoleColor.CYAN);
    }

    public static void printWelcome(String username){
        String[] parts = username.split("(?<=\\D)(?=\\d)");
        UI.printText("\n WELCOME " + parts[0].toUpperCase(), ConsoleColor.GREEN);


    }


    public static void printListOfCars(ArrayList<Car> cars) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            printText("\n " + (i + 1) + ") ", ConsoleColor.WHITE);
            printText(car.getBrand(), ConsoleColor.CYAN);
        }
        printText("\n", ConsoleColor.WHITE);
    }

    public static void printListOfRenters(ArrayList<Renter> renters) {
        for (int i = 0; i < renters.size(); i++) {
            Renter renter = renters.get(i);
            printText("\n " + (i + 1) + ") ", ConsoleColor.WHITE);
            printText(renter.getFullName(), ConsoleColor.CYAN);
        }
        printText("\n", ConsoleColor.WHITE);
    }
    public static void printListOfContracts(ArrayList<Contract> contracts) {
        for (int i = 0; i < contracts.size(); i++) {
            Contract contract = contracts.get(i);
            printText("\n ContractID: ", ConsoleColor.WHITE);
            printText(Integer.toString(contract.getContractId()), ConsoleColor.CYAN);
            printText(" Car: ", ConsoleColor.WHITE);
            printText(contract.getNumberPlate(), ConsoleColor.CYAN);
            printText(" Renter-name: ", ConsoleColor.WHITE);
            printText(contract.getRenterName(), ConsoleColor.CYAN);
        }
        printText("\n", ConsoleColor.WHITE);
    }

    public static void printRenterDetails(Renter renter){
        if (renter != null) {
            printText(" Details of renterID: ", ConsoleColor.WHITE);
            printText(Integer.toString(renter.getLicenseId()), ConsoleColor.CYAN);
            printText("\n FullName: ", ConsoleColor.WHITE);
            printText(renter.getFullName(), ConsoleColor.CYAN);
            printText("\n Address: ", ConsoleColor.WHITE);
            printText(renter.getAddress() + ", " + renter.getCity() + ", "+ renter.getState(), ConsoleColor.CYAN);
            printText("\n Zipcode: ", ConsoleColor.WHITE);
            printText(Integer.toString(renter.getZipCode()), ConsoleColor.CYAN);
            printText("\n Phone: ", ConsoleColor.WHITE);
            printText(Integer.toString(renter.getPhone()), ConsoleColor.CYAN);
            printText("\n CellPhone: ", ConsoleColor.WHITE);
            printText(Integer.toString(renter.getCellPhone()), ConsoleColor.CYAN);
            printText("\n Email: ", ConsoleColor.WHITE);
            printText(renter.getEmail(), ConsoleColor.CYAN);
            printText("\n LicenseDate: ", ConsoleColor.WHITE);
            printText(renter.getLicenseDate() + "", ConsoleColor.CYAN);
        } else {
            printText(" This renter is not in the system!", ConsoleColor.RED);
        }
    }

    public static void printCarDetails(Car car) {
        if (car != null) {
            printText(" Details of car: ", ConsoleColor.WHITE);
            printText(car.getNumberplate(), ConsoleColor.CYAN);
            printText("\n Brand: ", ConsoleColor.WHITE);
            printText(car.getBrand(), ConsoleColor.CYAN);
            printText("\n Category: ", ConsoleColor.WHITE);
            printText(car.getCategory().toString(), ConsoleColor.CYAN);
            printText("\n Fuel type: " + (car.getFuelType().toString()), ConsoleColor.WHITE);
            printText((car.getFuelType().toString()), ConsoleColor.CYAN);
            printText("\n Mileage: ", ConsoleColor.WHITE);
            printText(Double.toString(car.getMileage()), ConsoleColor.CYAN);
            printText("\n Registration date: ", ConsoleColor.WHITE);
            printText((car.getRegistrationDate().toString()), ConsoleColor.CYAN);
        } else {
            printText(" This car is not in the system!", ConsoleColor.RED);
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
                printText("\n Please give the day in format 'DD': ", ConsoleColor.WHITE);
                int inputDay = in.nextInt();
                in.nextLine(); // Consume the newline character left in the buffer

                if (inputDay < 1 || inputDay > 31) {
                    printText(" Invalid day. Please ensure the day is between 1 and 31.", ConsoleColor.RED);
                    continue; // Invalid day, loop again
                }
                day = inputDay;

                printText(" Please give the month in format 'MM': ", ConsoleColor.WHITE);
                int inputMonth = in.nextInt();
                in.nextLine(); // Consume the newline character left in the buffer

                if (inputMonth < 1 || inputMonth > 12) {
                    printText(" Invalid month. Please ensure the month is between 1 and 12.", ConsoleColor.RED);
                    continue; // Invalid month, loop again
                }
                month = inputMonth;

                printText(" Please give the year in format 'YYYY': ", ConsoleColor.WHITE);
                int inputYear = in.nextInt();
                in.nextLine(); // Consume the newline character left in the buffer

                // should have someway to ensure we can't make a contract back in time
                if (inputYear < 1950 || inputYear > 2030) {
                    printText(" Invalid year. Please ensure the year is between 1950 and 2030.", ConsoleColor.RED);
                    continue; // Invalid year, loop again
                }
                year = inputYear;

            } catch (InputMismatchException e) {
                printText(" Invalid input. Please enter numeric values for the date.", ConsoleColor.RED);
                in.nextLine(); // Clear the input buffer
            }
        } while (year == 0); // runs until a valid date is entered
        return LocalDate.of(year, month, day);
    }

    public static void printCategories(){
        int count = 0;
        for (Category c : Category.values()){
            count += 1;
            printText("\n " + count + ")", ConsoleColor.WHITE);
            printText(" " + c, ConsoleColor.CYAN);
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
            printText(" invalid email", ConsoleColor.RED);
            return false;
        }
    }
}
