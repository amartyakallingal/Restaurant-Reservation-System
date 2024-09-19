import java.util.HashMap;

public class MenuItemRegistry {
    private static MenuItemRegistry instance = null;

    private HashMap<String, MenuItem> registry;

    private MenuItemRegistry() {
        registry = new HashMap<>();
    }
    public static synchronized MenuItemRegistry getMenuItemRegistry() {
        if (instance == null) {
            instance = new MenuItemRegistry();
        }

        return instance;
    }

    public static synchronized MenuItem getMenuItem(String itemName) {
        if (instance == null) {
            instance = new MenuItemRegistry();
        }

        return instance.registry.getOrDefault(itemName, null);
    }

    public static void createMenuItem(String menuItem, String ingredientList) {
        if (instance == null) {
            instance = new MenuItemRegistry();
        }

        if (instance.registry.containsKey(menuItem)) {
            System.out.println("The menu item already exists in the reservation system");
            return;
        }

        try {
            MenuItem item = new MenuItem(menuItem, ingredientList);
            instance.registry.put(menuItem, item);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void viewAllMenuItems() {
        if (instance == null) {
            instance = new MenuItemRegistry();
        }

        for (MenuItem item : instance.registry.values()) {
            System.out.println(item);
        }
    }

    public static void calculateAveragePrice(String itemName) {
        if (instance == null) {
            instance = new MenuItemRegistry();
        }

        MenuItem item = instance.registry.getOrDefault(itemName, null);
        if (item == null) {
            System.out.println("ERROR: item doesn't exist");
            return;
        }

        int totalPrice = 0;
        int count = 0;



        for (Restaurant rst : RestaurantRegistry.getRestaurants()) {
            Integer price = rst.itemPrices.getOrDefault(item, null);
            if (price != null) {
                totalPrice += price;
                count++;
            }
        }

        if (count == 0) {
            System.out.println("ERROR: item was never added to a restaurant");
        } else {
            double avgPrice = ((double) totalPrice) / ((double) count);
            System.out.printf("Average price for %s: $%2.2f\n", itemName, avgPrice);
        }
    }

    public static void calculateItemPopularity(String itemName) {
        if (instance == null) {
            instance = new MenuItemRegistry();
        }

        MenuItem item = getMenuItem(itemName);
        if (item == null) {
            System.out.println("ERROR: item doesn't exist");
            return;
        }

        int numRest = 0;
        int restSize = RestaurantRegistry.getRestaurants().size();
        for (Restaurant rst : RestaurantRegistry.getRestaurants()) {
            if (rst.menuItems.contains(item)) {
                numRest += 1;
            }
        }

        if (numRest == 0) {
            System.out.println("ERROR: item was never added to a restaurant");
        } else {
            double proportion = ((double) numRest) / ((double) restSize);
            proportion *= 100;
            if (proportion > 50) {
                System.out.printf("Popular: %s offered at %d out of %d restaurants (%.0f%%)\n", itemName, numRest,
                        restSize, proportion);
            } else {
                System.out.printf("Not popular: %s offered at %d out of %d restaurants (%.0f%%)\n", itemName, numRest,
                        restSize, proportion);
            }
        }
    }

    public static void viewIngredients(String itemName) {
        if (instance == null) {
            instance = new MenuItemRegistry();
        }

        MenuItem item = instance.registry.getOrDefault(itemName, null);

        if (item == null) {
            System.out.println("ERROR: The item does not exist");
            return;
        }

        item.viewIngredients();
    }

    public static String[] getIngredientsArray(String itemName) {
        if (instance == null) {
            instance = new MenuItemRegistry();
        }

        MenuItem item = instance.registry.getOrDefault(itemName, null);

        if (item == null) {
            return new String[]{};
        }

        return item.ingredients;
    }

    public static String displayItemPopularity(String itemName) {
        if (instance == null) {
            instance = new MenuItemRegistry();
        }

        MenuItem item = instance.registry.getOrDefault(itemName, null);
        if (item == null) {
            return "ERROR: item doesn't exist";
        }

        int numRest = 0;
        int restSize = RestaurantRegistry.getRestaurants().size();
        for (Restaurant rst : RestaurantRegistry.getRestaurants()) {
            if (rst.menuItems.contains(item)) {
                numRest += 1;
            }
        }

        if (numRest == 0) {
            return "Entered item is not offered at any restaurant";
        } else {
            double proportion = ((double) numRest) / ((double) restSize);
            proportion *= 100;
            return String.format("%s offered at %d out of %d restaurants (%.0f%%)\n", itemName, numRest,
                    restSize, proportion);
        }
    }

    public static double displayAveragePrice(String itemName) {
        if (instance == null) {
            instance = new MenuItemRegistry();
        }

        MenuItem item = instance.registry.getOrDefault(itemName, null);
        if (item == null) {
            System.out.println("ERROR: item doesn't exist");
            return Double.NaN;
        }

        int totalPrice = 0;
        int count = 0;



        for (Restaurant rst : RestaurantRegistry.getRestaurants()) {
            Integer price = rst.itemPrices.getOrDefault(item, null);
            if (price != null) {
                totalPrice += price;
                count++;
            }
        }

        if (count == 0) {
            return Double.NaN;
        } else {
            return ((double) totalPrice) / ((double) count);
        }
    }
}
