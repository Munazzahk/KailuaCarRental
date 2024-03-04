public class MenuBuilder {
    //Står måske for at lave menuerne i stedet for at lave dem i UI?

    public Menu buildMainMenu() {
        Menu mainMenu = new Menu();
        mainMenu.setMenuTitle(" Which menu would you like to access?");
        mainMenu.setMenuItems("Cashier", "Manager ", "Coach", "Quit");
        mainMenu.printMenu();
        return mainMenu;
    }

}
