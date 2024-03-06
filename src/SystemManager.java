import com.mysql.cj.MysqlConnection;

import java.util.ArrayList;


public class SystemManager {
    MSQLConnection mysqlConnection= new  MSQLConnection();
    boolean systemRunning = true;
    MenuBuilder menuBuilder = new MenuBuilder();
    UI ui = new UI();

    public void runProgram() {
        //System.out.println(mysqlConnection.getContract(1));
        //mysqlConnection.updateContract(1,50);
        //System.out.println(mysqlConnection.getContract(1));
        //mysqlConnection.updateContract(1,20);

        //ui.printListOfCars(mysqlConnection.getAllCars());
        //ui.printCarDetails(mysqlConnection.getCar("BATMAN1"));
        //ui.printBasicContractDetails(mysqlConnection.getContract(1));
        ui.printFullContractDetails(mysqlConnection.getContract(1));
    }

    public void runMainMenu() {
        Menu menu = menuBuilder.buildMainMenu();
        int choice = menu.menuInputHandler();
        switch (choice) {
           // case 1 -> xxx;
        }
    }
}
