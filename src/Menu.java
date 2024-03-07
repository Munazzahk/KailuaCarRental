import java.util.ArrayList;
import java.util.Arrays;

public class Menu {
    private String menuTitle;
    private ArrayList<String> menuItems = new ArrayList<>();

    public Menu() {
    }

    public void setMenuItems(String... menuItems) {
        this.menuItems = new ArrayList<String>(Arrays.asList(menuItems));
    }
    public void addMenuItems(String item){
        this.menuItems.add(item);
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public int getMenuItemsSize() {
        return menuItems.size();
    }

    // HANDLE MENU CHOICES

    public String getMenuChoiceFromUserInput() {
        UI.printText(" Please enter the desired menu-number: ",ConsoleColor.RESET);
        return UI.getStringWithNumbersInput();
    }

    public String menuInputHandler() {
        String choice = getMenuChoiceFromUserInput();
        return choice;
    }

    // Prints the heading text and the menu
    public void printMenu() {
        int titleLength = menuTitle.length();
        int spaceLength = (49-titleLength)/2;
        System.out.println("\n");
        System.out.println(" "+"_".repeat(spaceLength) +  menuTitle + "_".repeat(spaceLength)+"\n");
        for (int i = 0; i < menuItems.size(); i++) {
            String line =   menuItems.get(i);
            System.out.println("   "+line);
        }
        System.out.println(" ________________________________________________ ");
        System.out.println("\n");
    }
}
