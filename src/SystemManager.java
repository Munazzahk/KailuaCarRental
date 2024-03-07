import java.time.LocalDate;
import java.util.ArrayList;


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
            case "4" -> deleteContract();
            case "5" -> viewCarDetails();
            case "6" ->viewRenterDetails();
            case "q" -> {UI.printText("\n GOODBYE :)", ConsoleColor.GREEN)
                        ;systemRunning = false;}
            default -> UI.printText(" Not an option", ConsoleColor.RED);
        }
    }

    public void viewCarDetails(){
        UI.printText("\n VIEWING CARS", ConsoleColor.WHITE);
        ArrayList<Car> cars = mysqlConnection.getAllCars();
        Car car = chooseCar(cars);
        UI.printCarDetails(car);
    }

    public void viewRenterDetails(){
        UI.printText("\n VIEWING RENTERS", ConsoleColor.WHITE);
        ArrayList<Renter> renters = mysqlConnection.getAllRenters();
        Renter renter = chooseRenter(renters);
        UI.printRenterDetails(renter);
    }

    public void deleteContract(){
        int contractId = chooseContractID();
        UI.printText("\n DELETING CONTRACT", ConsoleColor.WHITE);
        mysqlConnection.deleteContract(contractId);
    }

    public int chooseContractID(){
        UI.printText("\n VIEWING CONTRACTS", ConsoleColor.WHITE);
        UI.printListOfContracts(mysqlConnection.getAllContracts());
        UI.printText("\n Please provide the contractID of the contract you wish to access: ", ConsoleColor.WHITE);
        return  UI.getIntInput();
    }


    public Contract getContract() {
        int contractId = chooseContractID();
        Contract contract = mysqlConnection.getContract(contractId);
        if (contract == null) {
            UI.printText("\n No contract with this ID", ConsoleColor.RED);
        }
        return contract;
    }

    public void viewContract() {
        Contract contract = getContract();
        if (contract != null) {
            UI.printContract(contract);
        }
    }

    public void updateContract() {
        Contract contract = getContract();
        UI.printText("\n UPDATING CONTRACT", ConsoleColor.WHITE);
        if (contract != null) {
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

    public Category chooseCategory() {
        Category category = null;
        int choice = UI.getIntInput();
        switch (choice) {
            case 1 -> category = Category.Luxury;
            case 2 -> category = Category.Sport;
            case 3 -> category = Category.Family;
            default -> UI.printText(" Not an option", ConsoleColor.RED);
        }
        return category;
    }


    private void makeContract() {
        UI.printText("\n CREATING NEW CONTRACT ", ConsoleColor.WHITE);
        LocalDate newStartDate = UI.getStartDate();
        LocalDate newEndDate = UI.getEndDate();
        ArrayList<Car> listOfAvailableCars = getListOfAvailableCars(newStartDate, newEndDate);
        UI.printText("\n Please choose a category: ", ConsoleColor.WHITE);
        UI.printCategories();
        Category category = chooseCategory();
        UI.printText("\n The following cars are available: ", ConsoleColor.WHITE);
        listOfAvailableCars = sortCarByCategory(listOfAvailableCars, category);
        Car car = chooseCar(listOfAvailableCars);
        Renter renter = getARenter();
        UI.printText("\n Please provide the maximum mileage (km): ", ConsoleColor.WHITE);
        int max_km = UI.getIntInput();
        mysqlConnection.createContract(renter,car, java.sql.Date.valueOf(newStartDate),java.sql.Date.valueOf(newEndDate),max_km);

    }

    public Renter getARenter(){
        UI.printText("\n Do you want to create a new renter? (Y/N): ", ConsoleColor.WHITE);
        boolean create = UI.getBooleanInput();
        if(create){
            return createRenter();
        }
        else {
            UI.printText("\n Please choose an existing renter: ", ConsoleColor.WHITE);
            ArrayList<Renter> renters = mysqlConnection.getAllRenters();
            return chooseRenter(renters);
        }
    }

    public Renter createRenter(){
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
        int licenseId = UI.getIntInput();
        UI.printText(" License date of issue: ", ConsoleColor.WHITE);
        java.util.Date licenseDate = java.sql.Date.valueOf(UI.enterDate());

        Renter renter = new Renter(fullName,address,zipCode,city,state,
                phone,cellPhone,email,licenseId,(java.sql.Date)licenseDate);

        mysqlConnection.createRenter(renter);
        return renter;
    }
    public ArrayList<Car> getListOfAvailableCars(LocalDate newStartDate, LocalDate newEndDate) {
        return mysqlConnection.getCarsByTimePeriod(newStartDate, newEndDate);

    }

    public ArrayList<Car> sortCarByCategory(ArrayList<Car> cars, Category category) {
        ArrayList<Car> sortedCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.getCategory() == category) {
                sortedCars.add(car);
            }
        }
        return sortedCars;
    }

    public Car chooseCar(ArrayList<Car> cars){
        Car car = null;
        while(car == null){
        UI.printListOfCars(cars);
        int choice = UI.getIntInput();
        car =  getCarByIndex(cars, choice);}
        return car;
    }

    public Car getCarByIndex(ArrayList<Car> cars, int index) {
        if (index >= 1 && index <= cars.size()) {
            return cars.get(index - 1);
        } else {
            UI.printText("\n Option not available", ConsoleColor.RED);
            return null;
        }
    }
    public Renter chooseRenter(ArrayList<Renter> renters){
        Renter renter = null;
        while(renter == null){
            UI.printListOfRenters(renters);
            int choice = UI.getIntInput();
            renter =  getRenterByIndex(renters, choice);}
        return renter;
    }

    public Renter getRenterByIndex(ArrayList<Renter> renters, int index) {
        if (index >= 1 && index <= renters.size()) {
            return renters.get(index - 1);
        } else {
            UI.printText("\n Option not available", ConsoleColor.RED);
            return null;
        }
    }

}
