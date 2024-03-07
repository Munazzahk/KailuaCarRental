public class MenuBuilder {

    public Menu buildMainMenu() {
        Menu mainMenu = new Menu();
        mainMenu.setMenuTitle(" Which menu would you like to access?");
        mainMenu.setMenuItems(" 1) Create new contract", " 2) View a contract", " 3) Update contract mileage",
                " 4) Delete a contract ", " 5) Create renter",  "q) Quit");
        mainMenu.printMenu();
        return mainMenu;
    }

}
