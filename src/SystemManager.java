import com.mysql.cj.MysqlConnection;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatException;
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
            case "1" -> makeContract();
            case "2" -> viewContract();
            case "3" -> updateContract();
            case "4" -> System.out.println("coming soon");
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

    // never print using toString, this is just for test
    private void printCars(ArrayList<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }


    private void makeContract() {
        LocalDate startDate = null;
        LocalDate endDate = null;

        while(startDate == null) {
            UI.printText("\nWhen does the contract starts?", ConsoleColor.WHITE);
            String dateString = UI.getStringWithNumbersInput();
            try {
                startDate = UI.getDateInput(dateString);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
        while(endDate == null) {
            UI.printText("\nWhen does the contract end?", ConsoleColor.WHITE);
            String dateString = UI.getStringWithNumbersInput();
            try {
                endDate = UI.getDateInput(dateString);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }


    }

}
