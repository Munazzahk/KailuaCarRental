import com.mysql.cj.MysqlConnection;

import java.util.ArrayList;
import java.util.Scanner;


public class SystemManager {
    MSQLConnection mysqlConnection = new MSQLConnection();
    boolean systemRunning = true;
    MenuBuilder menuBuilder = new MenuBuilder();

    public void runProgram() {
        // logon();
        System.out.println(mysqlConnection.getContract(1));
        mysqlConnection.updateContract(1,50);
        System.out.println(mysqlConnection.getContract(1));
        mysqlConnection.updateContract(1,20);
       // while (systemRunning) {
          //  runMainMenu();
        //}

    }

    public void runMainMenu() {

        Menu menu = menuBuilder.buildMainMenu();
        int choice = menu.menuInputHandler();
        switch (choice) {
           // case 1 -> xxx;
        }


    }


/*    public void logon(){
        String employeeUsername, employeePassword;
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter your username: ");
        employeeUsername = in.nextLine();
        System.out.print("\nPlease enter your password: ");
        employeePassword = in.nextLine();
        mysqlConnection = new  MSQLConnection(employeeUsername,employeePassword);
    }*/

    // never print using toString, this is just for test
    private void printCars(ArrayList<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }
}
