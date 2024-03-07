import com.mysql.cj.MysqlConnection;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;


public class SystemManager {
    MSQLConnection mysqlConnection;
    boolean systemRunning = true;
    MenuBuilder menuBuilder = new MenuBuilder();

    public void runProgram() {
        mysqlConnection = new MSQLConnection();
        while (mysqlConnection.getConnection() == null) {
           UI.printText("\n Invalid username or password. Please try again.", ConsoleColor.RED);
            mysqlConnection.createConnection();
        }
       while (systemRunning) {
            runMainMenu();
        }

    }

    public void runMainMenu() {
        Menu menu = menuBuilder.buildMainMenu();
        String choice = menu.menuInputHandler();
        switch (choice) {
            case "1" -> System.out.println("coming soon");
            case "2" -> viewContract();
            case "3" -> updateContract();
            case "4" -> System.out.println("coming soon");
            case "5" -> createRenter();
            case "q" -> systemRunning = false;
            default ->  UI.printText(" Not an option", ConsoleColor.RED);
        }


    }

    public Contract getContract()
    {   UI.printText(" Please provide the contractID of the contract you wish to access: ", ConsoleColor.WHITE);
        int contractId = UI.getIntInput();
        Contract contract = mysqlConnection.getContract(contractId);
        if(contract==null) {
            UI.printText("\n No contract with this ID", ConsoleColor.RED);
        }
        return contract;
    }
    public void viewContract(){
        Contract contract = getContract();
        if(contract!=null){
            UI.printContract(contract);
        }
    }

    public void updateContract(){
       Contract contract = getContract();
        if(contract!=null) {
            UI.printText("\n Please enter the new mileage in km: ", ConsoleColor.WHITE);
            double km = UI.getDoubleInput();
            if (km > contract.getMileage()) {
                mysqlConnection.updateContract(km, contract.getContractId());
            } else UI.printText("\n The entered mileage is less then the current mileage", ConsoleColor.RED);
        }
    }


    public void createRenter(){
        UI.printText("\n CREATING NEW RENTER", ConsoleColor.WHITE);
        UI.printText("\n\n Please provide the following data: ", ConsoleColor.WHITE);

        UI.printText("\n Full name:  ",ConsoleColor.WHITE);
        String fullName = UI.getStringInput();
        UI.printText(" Address: ", ConsoleColor.WHITE);
        String address = UI.getStringWithNumbersInput();
        UI.printText(" Zipcode: ", ConsoleColor.WHITE);
        int zipCode = UI.getIntInput();
        UI.printText(" City: ", ConsoleColor.WHITE);
        String city = UI.getStringInput();
        UI.printText(" State: ", ConsoleColor.WHITE);
        String state = UI.getStringInput();
        UI.printText(" Phone-number: ", ConsoleColor.WHITE);
        int phone = UI.getIntInput();
        UI.printText(" Cellphone number: ", ConsoleColor.WHITE);
        int cellPhone = UI.getIntInput();
        String email = UI.getEmail();
        UI.printText(" Drivers-Licence number: ", ConsoleColor.WHITE);
        int license_id = UI.getIntInput();
        Date licenseDate = UI.getDate();

        Renter renter = new Renter(fullName, address, zipCode, city,
                phone, email, license_id, licenseDate,
                state, cellPhone);

        mysqlConnection.createRenter(renter);

    }

    // never print using toString, this is just for test
    private void printCars(ArrayList<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }
}
