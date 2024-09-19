import java.util.ArrayList;

public class MenuItem {
    final String menuItem;
    final String ingredientList;
    final String[] ingredients;


    public MenuItem(String menuItem, String ingredients) {
        this.menuItem = menuItem;
        this.ingredientList = ingredients;
        this.ingredients = ingredientList.split(":");
        System.out.println(menuItem + " created");
    }

    public String toString() {
        return menuItem;
    }

    public void viewIngredients() {
        String output = "Ingredients: ";
        for (String ingredient : ingredients) {
            output += ingredient + ", ";
        }
        System.out.println(output.substring(0, output.length()-2));
    }
}