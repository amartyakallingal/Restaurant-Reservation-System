public class ReservationSystem {

    public void createRestaurant(String id, String name, String city, String state, String zipCode,
                                 int capacity, String owner_id, String license) {
        RestaurantRegistry.createRestaurant(id, name, city, state, zipCode, capacity, owner_id, license);
    }

    public void createCustomer(String id, String fname, String lname, String city, String state, String zipCode,
                               double funds) {
        CustomerRegistry.createCustomer(id, fname, lname, city, state, zipCode, funds);
    }

    public void createOwner(String id, String fname, String lname, String city, String state, String zipCode,
                            String startDate, String groupName) {
        OwnerRegistry.createOwner(id, fname, lname, city, state, zipCode, startDate, groupName);
    }

    public void requestReservation(String customerId, String restaurantId, int partySize, String reservationDate,
                                   String reservationTime, int creditsAwarded) {
        ReservationRegistry.requestReservation(customerId, restaurantId, partySize, reservationDate, reservationTime, creditsAwarded);
    }

    public void customerArrival(String customerId, String restaurantId, String reservationDate, String arrivalTime, String reservationTime) {
        RestaurantRegistry.customerArrival(customerId, restaurantId, reservationDate, arrivalTime, reservationTime);
    }

    public void addMenuItem(String menuItem, String restaurantId, int price) {
        RestaurantRegistry.addMenuItem(menuItem, restaurantId, price);
    }

    public void createMenuItem(String menuItem, String ingredientList) {
        MenuItemRegistry.createMenuItem(menuItem, ingredientList);
    }

    public String displayAllRestaurants() {
        return RestaurantRegistry.displayAllRestaurants();
    }

    public void orderMenuItem(String customerId, String restaurantId, String date, String time, String menuItem, int quantity) {
        RestaurantRegistry.orderMenuItem(customerId, restaurantId, date, time, menuItem, quantity);
    }


    public void makeReview(String customerID, String restaurantID, String diningDate, String diningTime, int score, String tags) {
        RestaurantRegistry.makeReview(customerID, restaurantID, diningDate, diningTime, score, tags);
    }

    public void viewRestaurantsOwnedBy(String ownerId) {
        OwnerRegistry.viewRestaurantsOwnedBy(ownerId);
    }

    public String displayRestaurantsOwnedBy(String ownerId) {
        return OwnerRegistry.displayRestaurantsOwnedBy(ownerId);
    }

    public void viewItemsServedBy(String restaurantId) {
        RestaurantRegistry.viewItemsServedBy(restaurantId);
    }

    public String[] getIngredientsArray(String itemName) {
        return MenuItemRegistry.getIngredientsArray(itemName);
    }

    public int getMenuItemPrice(String itemName, String restID) {
        return RestaurantRegistry.getItemPrice(itemName, restID);
    }

    public void viewIngredients(String itemName) {
        MenuItemRegistry.viewIngredients(itemName);
    }
    public void viewAllMenuItems() {
        MenuItemRegistry.viewAllMenuItems();
    }
    public void viewAllCustomers() {
        CustomerRegistry.viewAllCustomers();
    }

    public void viewAllRestaurants() {
        RestaurantRegistry.viewAllRestaurants();
    }

    public void calculateItemPopularity(String itemName) {
        MenuItemRegistry.calculateItemPopularity(itemName);
    }

    public void calculateAveragePrice(String itemName) {
        MenuItemRegistry.calculateAveragePrice(itemName);
    }

    public void viewOwners(String groupName) {
        OwnerRegistry.viewOwners(groupName);
    }
    public String displayOwners(String groupName) {
        return OwnerRegistry.displayOwners(groupName);
    }

    public double getCustomerFunds(String custId) {
        return CustomerRegistry.getCustomerFunds(custId);
    }

    public String displayItemPopularity(String itemName) {
        return MenuItemRegistry.displayItemPopularity(itemName);
    }

    public String displayAllCustomers() {
        return CustomerRegistry.displayAllCustomers();
    }

    public double displayAveragePrice(String itemName) {
        return MenuItemRegistry.displayAveragePrice(itemName);
    }

    public void orderMenuItemGUI(String customerID, String restID, String date, String time, String selectedItem, Integer selectedQuantity) throws Exception {
        RestaurantRegistry.orderMenuItemGUI(customerID, restID, date, time, selectedItem, selectedQuantity);
    }

    public String[] displayCustomerIDs() {
        return CustomerRegistry.getCustomerIDs();
    }

    public String[] displayRestaurantIDs() {
        return RestaurantRegistry.displayRestaurantIDs();
    }

    public String[] displayOwnerIDs() {
        return OwnerRegistry.displayOwnerIDs();
    }

    public String[] displayRestaurants() {
        return RestaurantRegistry.displayAllRestaurants().split("\n");
    }

    public boolean customerArrivalGUI(String customerID, String restID, String reservationDate, String arrivalTime, String reservationTime) throws Exception {
        return RestaurantRegistry.customerArrivalGUI(customerID, restID, reservationDate, arrivalTime, reservationTime);
    }

    public void requestReservationGUI(String customerId, String restaurantId, int partySize, String reservationDate, String reservationTime, int creditsAwarded) throws Exception {
        ReservationRegistry.requestReservationGUI(customerId, restaurantId, partySize, reservationDate, reservationTime, creditsAwarded);
    }
}
