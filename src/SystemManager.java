import com.mysql.cj.MysqlConnection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class SystemManager {
    MSQLConnection mysqlConnection;
    boolean systemRunning = true;
    MenuBuilder menuBuilder = new MenuBuilder();
    UI ui = new UI();

    public void runProgram() {
        mysqlConnection = new MSQLConnection();
        while (mysqlConnection.getConnection() == null) {
           UI.printText("\n Invalid username or password. Please try again.", ConsoleColor.RED);
            mysqlConnection.createConnection();
        }
       while (systemRunning) {
           //getCarsByTimePeriod();
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

    //Den virker i hvert fald
    public void getCarsByTimePeriod() {
        LocalDate newStartDate = ui.getStartDate();
        LocalDate newEndDate = ui.getEndDate();
        ui.printListOfCars(mysqlConnection.getCarsByTimePeriod(newStartDate, newEndDate));

    }

    //Har lagt den her lige nu fordi jeg ikke ved hvor den skulle hen
    //Hvis den har egen klasse er det måske lidt for unødvendigt? eller ikke?
    //Igen der skal lige spørges om category som så skal indsættes her
    public ArrayList<Car> sortCarByCategory(ArrayList<Car> cars, Category category) {
        ArrayList<Car> sortedCars = new ArrayList<>();

        for (Car car : cars) {
            if (car.getCategory() == category) {
                sortedCars.add(car);
            }
        }
        return sortedCars;
    }

    //Sorts car by category family... category should be an input
    public void sortCars() {
        ui.printListOfCars(sortCarByCategory(mysqlConnection.getAllCars(), Category.Family));
    }


}
