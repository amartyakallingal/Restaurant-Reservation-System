import java.util.Collection;
import java.util.HashMap;

public class RestaurantRegistry {
    private static RestaurantRegistry instance = null;

    private final HashMap<String, Restaurant> registry;

    private RestaurantRegistry() {
        registry = new HashMap<>();
    }

    public static synchronized RestaurantRegistry getRestaurantRegistry() {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        return instance;
    }

    public static synchronized Restaurant getRestaurant(String restaurantID) {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        return instance.registry.getOrDefault(restaurantID, null);
    }

    public static void createRestaurant(String id, String name, String city, String state, String zipCode, int capacity, String ownerId, String license) {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        if (instance.registry.containsKey(id)) {
            System.out.println("ERROR: There already exists a restaurant with the entered id!");
            return;
        }

        Restaurant restaurant;

        try {
            restaurant = new Restaurant(id, name, city, state, zipCode, capacity, ownerId, license);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return;
        }

        instance.registry.put(id, restaurant);
    }
    public static void makeReview(String customerID, String restaurantID, String diningDate, String diningTime, int score, String tags) {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        Restaurant restaurant = RestaurantRegistry.getRestaurant(restaurantID);
        if (restaurant == null) {
            System.out.println("ERROR: The restaurant does not exist");
            return;
        }

        restaurant.makeReview(customerID, restaurantID, diningDate, diningTime, score, tags);
    }
    public static boolean containsRestaurant(String restId) {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        return instance.registry.containsKey(restId);
    }

    public static Collection<Restaurant> getRestaurants() {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        return instance.registry.values();
    }

    public static void viewAllRestaurants() {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        for (Restaurant restaurant : instance.registry.values()) {
            System.out.printf("%s (%s)\n", restaurant.restaurantId, restaurant.name);
        }
    }

    public static void viewItemsServedBy(String restaurantId) {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }
        Restaurant restaurant = instance.registry.getOrDefault(restaurantId, null);

        if (restaurant == null) {
            System.out.println("ERROR: The restaurant does not exist");
            return;
        }

        restaurant.viewMenu();
    }

    public static void orderMenuItem(String customerId, String restaurantId, String date, String time, String menuItem, int quantity) {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        Restaurant restaurant = instance.registry.getOrDefault(restaurantId, null);
        if (restaurant == null) {
            System.out.println("ERROR: restaurant doesn't exist");
        } else {
            try {
                restaurant.orderMenuItem(customerId, date, time, menuItem, quantity);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void addMenuItem(String menuItem, String restaurantId, int price) {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }
        MenuItem item = MenuItemRegistry.getMenuItem(menuItem);

        if (item == null) {
            System.out.println("ERROR: Menu item doesn't exist");
            return;
        }

        Restaurant restaurant = instance.registry.getOrDefault(restaurantId, null);
        if (restaurant == null) {
            System.out.println("ERROR: Entered restaurant doesn't exist");
        } else {
            restaurant.addMenuItem(item, price);
        }

    }

    public static void customerArrival(String customerId, String restaurantId, String reservationDate, String arrivalTime, String reservationTime) {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }
        Restaurant restaurant = RestaurantRegistry.getRestaurant(restaurantId);
        if (restaurant != null) {
            restaurant.customerArrival(customerId, restaurantId, reservationDate, arrivalTime, reservationTime);
        }
    }

    public static String displayAllRestaurants() {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }
        StringBuilder builder = new StringBuilder();
        for (Restaurant restaurant : instance.registry.values()) {
            builder.append(restaurant.name);
            builder.append(" - " + restaurant.restaurantId);
            builder.append("\n");
        }

        return builder.toString();
    }

    public static String[] getMenuItemArray(String restID) {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        Restaurant restaurant = instance.registry.getOrDefault(restID, null);
        String[] menuItems = new String[restaurant.menuItems.size() + 1 ];
        menuItems[0] = "Select";
        for (int i = 1; i <= restaurant.menuItems.size(); i++) {
            menuItems[i] = restaurant.menuItems.get(i - 1).toString();
        }

        return menuItems;
    }

    public static int getItemPrice(String itemName, String restID) {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }
        Restaurant restaurant = instance.registry.getOrDefault(restID, null);

        if (restaurant == null) {
            return 0;
        }

        MenuItem item = MenuItemRegistry.getMenuItem(itemName);

        return restaurant.itemPrices.getOrDefault(item, 0);
    }

    public static void orderMenuItemGUI(String customerID, String restID, String date, String time, String selectedItem, Integer selectedQuantity) throws Exception {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        Restaurant restaurant = instance.registry.getOrDefault(restID, null);
        if (restaurant == null) {
            throw new Exception("ERROR: restaurant doesn't exist");
        } else {
            restaurant.orderMenuItemGUI(customerID, date, time, selectedItem, selectedQuantity);
        }
    }

    public static String[] displayRestaurantIDs() {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        return instance.registry.keySet().toArray(new String[0]);
    }

    public static boolean customerArrivalGUI(String customerID, String restID, String reservationDate, String arrivalTime, String reservationTime) throws Exception {
        if (instance == null) {
            instance = new RestaurantRegistry();
        }

        Restaurant restaurant = RestaurantRegistry.getRestaurant(restID);
        return restaurant.customerArrivalGUI(customerID, restID, reservationDate, arrivalTime, reservationTime);
    }
}
