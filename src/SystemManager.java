public class SystemManager {
    boolean systemRunning = true;
    MenuBuilder menuBuilder = new MenuBuilder();

    public void runProgram() {
        while (systemRunning) {
            runMainMenu();
        }
    }

    public void runMainMenu() {
        Menu menu = menuBuilder.buildMainMenu();
        int choice = menu.menuInputHandler();
        switch (choice) {
            //case 1 -> xxx;
        }
    }
}
