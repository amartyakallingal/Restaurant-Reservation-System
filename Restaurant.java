import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Restaurant {
    final String restaurantId;
    final String name;
    final String ownerId;
    final int capacity;
    final String city;
    final String state;
    final String zipCode;
    final String license;
    int rating;
    boolean top10;
    int revenue = 0;
    private final ArrayList<Reservation> reservations;
    public ArrayList<Review> reviews;
    public ArrayList<MenuItem> menuItems;
    public HashMap<MenuItem, Integer> itemPrices;
    ArrayList<String> tags;


    public Restaurant(String restaurantId, String name, String city, String state, String zipCode,
                      int capacity, String ownerId, String license) {
        boolean hadError = false;
        String errorMessage = "";
        if (RestaurantRegistry.containsRestaurant(restaurantId)) {
            hadError = true;
            errorMessage += "ERROR: There already exists a restaurant with the entered id!\n";
        }
        if (!OwnerRegistry.containsOwner(ownerId)) {
            hadError = true;
            errorMessage += "ERROR: owner doesn't exist\n";
        }
        if (restaurantId == null || restaurantId.isEmpty() || restaurantId.equals("null")) {
            hadError = true;
            errorMessage += "ERROR: Entered restaurant id is null or empty!\n";
        }
        if (name == null || name.isEmpty()) {
            hadError = true;
            errorMessage += "ERROR: Entered name is null or empty!\n";
        }
        if (city == null || city.isEmpty()) {
            hadError = true;
            errorMessage += "ERROR: Entered city is null or empty!\n";
        }
        if (state == null || state.isEmpty()) {
            hadError = true;
            errorMessage += "ERROR: Entered state is null or empty!\n";
        }
        if (zipCode == null || zipCode.isEmpty()) {
            hadError = true;
            errorMessage += "ERROR: Entered zipCode is null or empty!\n";
        }
        if (capacity < 0) {
            hadError = true;
            errorMessage += "ERROR: Entered capacity is below 0!\n";
        }

        if (hadError) {
            throw new IllegalArgumentException(errorMessage);
        }

        this.restaurantId = restaurantId;
        this.name = name;
        this.capacity = capacity;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.reservations = new ArrayList<>();
        this.ownerId = ownerId;
        this.license = license;
        this.itemPrices = new HashMap<>();
        this.menuItems = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.tags = new ArrayList<>();

        Owner owner = OwnerRegistry.getOwner(ownerId);
        owner.addRestaurant(restaurantId);

        System.out.printf("Restaurant created: %s (%s) - %s, %s %s\n", this.restaurantId, this.name,
                this.city, this.state, this.zipCode);
        System.out.printf("%s (%s) owns %s (%s)\n", ownerId, OwnerRegistry.getOwner(ownerId).firstName + " " + OwnerRegistry.getOwner(ownerId).lastName, restaurantId, name);
    }

    public boolean validateReservation(String date, String time, int partySize) {
        int currCapacity = capacity;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datetime = date + " " + time;
        LocalDateTime ldt = LocalDateTime.parse(datetime, dateFormat);
        LocalDateTime two_hours_b4 = ldt.minusHours(2);
        LocalDateTime two_hours_after = ldt.plusHours(2);

        for (Reservation reservation : reservations) {
            LocalDateTime existingTime = reservation.reservationDateTime;
            if (two_hours_b4.isBefore(existingTime) && two_hours_after.isAfter(existingTime) && (reservation.sts == Status.CONFIRMED || reservation.sts == Status.COMPLETE || (reservation.sts == Status.WALKON && reservation.seated))) {
                currCapacity -= reservation.partySize;
            }
        }

        return currCapacity >= partySize;
    }

    /**
     * traverse through the HashMap and find all values of valid reservations which are before two hours and after two hours
     * that is currentCapacity
     * if incremented by 1, is currentCapacity > capacity?
     * if it is validReservation returns false
     */

    // 2hrs b4 array
    // find first one within vlaid time and then start adding
    // 1hrs b4 array doesn't matter j add
    // same hr find last valid one and then start adding from beginning
    public void insertReservation(Reservation reservation) {
        reservations.add(reservation);
        Collections.sort(reservations);
    }

    public void makeReview(String customerID, String restaurantID, String diningDate, String diningTime, int score, String tags) {
        Customer customer = CustomerRegistry.getCustomer(customerID);
        Restaurant restaurant = RestaurantRegistry.getRestaurant(restaurantID);
        Reservation reservation = ReservationRegistry.getReservation(String.format(customerID + restaurantID + diningDate + diningTime));

        boolean hadError = false;
        String errorMessage = "";

        if (customer == null) {
            hadError = true;
            errorMessage += "ERROR: Entered customer does not exist\n";
        }

        if (restaurant == null) {
            hadError = true;
            errorMessage += "ERROR: Entered restaurant does not exist\n";
        }

        if (reservation == null) {
            hadError = true;
            errorMessage += "ERROR: reservation doesn't exist\n";
        } else if (reservation.sts != Status.COMPLETE) {
            hadError = true;
            errorMessage += "ERROR: reservation wasn't successfully completed\n";
        }

        if (hadError) {
            System.out.print(errorMessage);
            return;
        }

        Review newReview = customer.makeReview(score, "", tags);
        restaurant.reviews.add(newReview);

        System.out.printf("%s (%s) rating for this reservation: %d", restaurantID, restaurant.name, newReview.numericScore);
        System.out.printf("%n%s (%s) average rating: %.0f", restaurantID, restaurant.name, restaurant.getAverageScore());
        restaurant.addTags(tags);
        StringBuilder tagPrint = new StringBuilder();
        for (String tag : restaurant.tags) {
            tagPrint.append(tag).append(", ");
        }
        System.out.printf("%nTags: %s \n", tagPrint.substring(0, tagPrint.length() - 2));

    }

    public void seatCustomer(Customer customer, Reservation reservation) {
        reservation.setSeated(true);
    }

    public int checkArrivalTime(String reservationDate, String reservationTime, String arrivalTime) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String arrivalDateTime = reservationDate + " " + arrivalTime;
        String reservationDateTime = reservationDate + " " + reservationTime;
        LocalDateTime arrival = LocalDateTime.parse(arrivalDateTime, dateFormat);
        LocalDateTime reservation = LocalDateTime.parse(reservationDateTime, dateFormat);

        if (reservation.minusMinutes(30).isAfter(arrival)) {
            return -1; // Early
        } else if (arrival.isAfter(reservation.plusMinutes(15))) {
            return 1; // Late
        } else {
            return 0; // On time
        }
    }


    public void customerArrival(String customerId, String restaurantId, String reservationDate, String arrivalTime, String reservationTime) {
        String errorMessage = "Failed to process customer arrival. The following errors occurred: \n";
        if (!RestaurantRegistry.containsRestaurant(restaurantId)) {
            // id doesn't correspond to restaurant,
            // TODO: add error message
            errorMessage += "The restaurant ID does not correspond to a restaurant!";
        } else {
            Restaurant restaurant = RestaurantRegistry.getRestaurant(restaurantId);
        }
        if (!CustomerRegistry.containsCustomer(customerId)) {
            errorMessage += "The customer ID does not correspond to a customer!";
        } else {
            Customer customer = CustomerRegistry.getCustomer(customerId);
        }

        boolean hadError = false;


        if (customerId == null || restaurantId == null || reservationDate == null || arrivalTime == null
                || reservationTime == null) {
            hadError = true;
            errorMessage += "\tOne or more arguments is null\n";
        }

        Customer customer = CustomerRegistry.getCustomer(customerId);
        Restaurant restaurant = RestaurantRegistry.getRestaurant(restaurantId);


        if (customer == null) {
            hadError = true;
            errorMessage += "\tEntered customer ID does not correspond to existing customer!\n";
        }

        if (restaurant == null) {
            hadError = true;
            errorMessage += "\tEntered restaurant ID does not correspond to existing restaurant\n";
        }

        if (hadError) {
//            System.out.print(errorMessage);
            return;
        }

        String reservationKey = customerId + restaurantId + reservationDate + reservationTime;
        Reservation reservation = ReservationRegistry.getReservation(reservationKey);
//        if (reservation == null) {
//            System.out.print("Entered reservation does not exist! Treating as walk-in!");
//        }
        ;

        // walk-in party
        if (reservationTime.equals("null") || reservation == null) {
            System.out.printf("%s %s - Walk-in party\n", customer.firstName, customer.lastName);
            if (restaurant.validateReservation(reservationDate, arrivalTime, 4)) {
                System.out.println("No credits rewarded and no misses added");
                reservation = new Reservation(customerId, restaurantId, 4, reservationDate, arrivalTime,
                        0, true);
                restaurant.seatCustomer(customer, reservation);
                System.out.printf("Seats were available, %s %s seated\n", customer.firstName, customer.lastName);
            } else {
                System.out.println("No credits rewarded and no misses added");
                System.out.println("Seats not available - Request denied");
            }


            System.out.printf("Credits: %d\n", customer.totalCredits);
            System.out.printf("Misses: %d\n", customer.missedReservations);
        } else {
            try {
                // Early reservation
                if (checkArrivalTime(reservationDate, reservationTime, arrivalTime) == -1) {
                    System.out.printf("Customer %s (%s %s) has arrived early at %s\n", customerId, customer.firstName,
                            customer.lastName, restaurant.name);
                    System.out.println("Please come back during the reservation window\n" +
                            "No credits rewarded and no misses added");
                } else if (checkArrivalTime(reservationDate, reservationTime, arrivalTime) == 1) {
                    // late reservation
                    System.out.printf("Customer %s (%s %s) has arrived late at %s\n", customerId, customer.firstName, customer.lastName,
                            restaurant.name);
                    System.out.printf("%s %s - Missed reservation\n", customer.firstName, customer.lastName);
                    System.out.println("No credits rewarded and 1 miss added");
                    reservation.setStatus(Status.MISSED);
                    customer.addMissedReservation();
                    if (restaurant.validateReservation(reservationDate, arrivalTime, reservation.partySize)) {
                        System.out.printf("Seats were available, %s %s seated\n", customer.firstName, customer.lastName);
                        restaurant.seatCustomer(customer, reservation);
                    }
//                    if restaurant.validReservation(arrivalTime, partySize = 1) {
//
//                    }
                } else {
                    // on-time reservation
                    System.out.printf("Customer %s (%s %s) has arrived at %s\n", customerId, customer.firstName, customer.lastName,
                            restaurant.name);
                    System.out.printf("%s %s - Successfully completed reservation\n", customer.firstName, customer.lastName);
                    customer.addCredits(reservation.getCredits());
                    reservation.setStatus(Status.COMPLETE);
                    System.out.println("Full credits rewarded");
                    restaurant.seatCustomer(customer, reservation);
                }
            } catch (Exception e) {
                System.out.println("Error in parsing provided date or time values!");
            }

            System.out.printf("Credits: %d\n", customer.totalCredits);
            System.out.printf("Misses: %d\n", customer.missedReservations);

        }

    }

    public double getAverageScore() {
        int sum = 0;
        int count = 0;

        if (reviews.isEmpty()) {

            return 0;
        }

        for (Review review : reviews) {
            sum += review.numericScore;
            count++;
        }

        return (double) sum / count;
    }


    public void addMenuItem(MenuItem item, int price) {
        if (itemPrices.containsKey(item)) {
            System.out.println("ERROR: item has already been added to this restaurant, try again");
            return;
        } else {
            itemPrices.put(item, price);
            menuItems.add(item);
            System.out.printf("%s) Menu item added: %s - $%d\n", restaurantId, item.toString(), price);
        }
    }

    public void orderMenuItem(String customerId, String date, String time, String menuItem, int quantity) {
        Customer customer = CustomerRegistry.getCustomer(customerId);
        if (customer == null) {
            System.out.println("ERROR: customer doesn't exist");
            return;
        }
        String reservationKey = customerId + this.restaurantId + date + time;
        Reservation reservation = ReservationRegistry.getReservation(reservationKey);
        if (reservation == null) {
            System.out.println("ERROR: Customer has no reservation/experience at given time");
            return;
        }

        if (!reservation.isSeated()) {
            System.out.println("ERROR: Customer was not allowed to be seated");
            //return;
        }

        // seated at time socheck customer funds
        MenuItem item = MenuItemRegistry.getMenuItem(menuItem);
        if (item == null) {
            System.out.println("ERROR: Entered menu item does not exist");
            return;
        }

        if (!menuItems.contains(item)) {
            System.out.println("ERROR: Entered menu item is not served at restaurant!");
            return;
        }

        int itemPrice = itemPrices.get(item);
        int netPrice = itemPrice * quantity;
        if (customer.checkFunds(netPrice)) {
            // customer has sufficient funds and can order item
            this.revenue += netPrice;
            reservation.incrementBill(netPrice);
            customer.decrementFunds(netPrice);
        } else {
            // customer is broke
            System.out.println("ERROR: Customer has insufficient funds!");
        }
        System.out.println("Menu item successfully ordered");
        System.out.println("Total Price for ordered amount: " + netPrice);
        System.out.println(customerId + " bill for current reservation: " + reservation.bill);
        System.out.println(customerId + " remaining funds: " + (int) customer.currentFunds);
        System.out.println(restaurantId + " total revenue from all reservations: " + this.revenue);


    }

    public void viewMenu() {
        for (MenuItem item : menuItems) {
            System.out.println(item);
        }
    }

    @Override
    public String toString() {
        return restaurantId + " (" + name + ") : Unified License - " + license;
    }

    public void addTags(String concatTags) {
        String[] tagArr = concatTags.split(":");
        tags.addAll(List.of(tagArr));
    }

    public boolean customerArrivalGUI(String customerID, String restID, String reservationDate, String arrivalTime, String reservationTime) throws Exception {
        String errorMessage = "Failed to process customer arrival. The following errors occurred: \n";
        boolean hadError = false;

        if (reservationDate == null || arrivalTime == null
                || reservationTime == null || reservationDate.isEmpty() || arrivalTime.isEmpty() || reservationTime.isEmpty()) {
            hadError = true;
            errorMessage += "\tOne or more arguments is null or empty\n";
        }

        if (hadError) {
            throw new Exception("Error in processing customer arrival: \n" + errorMessage);
        }
        Customer customer = CustomerRegistry.getCustomer(customerID);
        Restaurant restaurant = RestaurantRegistry.getRestaurant(restaurantId);

        String reservationKey = customerID + restaurantId + reservationDate + reservationTime;
        Reservation reservation = ReservationRegistry.getReservation(reservationKey);

        // walk-in party
        if (reservationTime.equals("null") || reservation == null) {
            System.out.printf("%s %s - Walk-in party\n", customer.firstName, customer.lastName);
            if (restaurant.validateReservationGUI(reservationDate, arrivalTime, 4)) {
                reservation = new Reservation(customerID, restaurantId, 4, reservationDate, arrivalTime,
                        0, true);
                restaurant.seatCustomer(customer, reservation);
            } else {
                throw new Exception("Error: insufficient space for walk-in customer!");
            }
            return true;
        } else {
            try {
                // Early reservation
                if (checkArrivalTime(reservationDate, reservationTime, arrivalTime) == -1) {
                    throw new Exception("Customer has arrived early for reservation, please return at a later time");
                } else if (checkArrivalTime(reservationDate, reservationTime, arrivalTime) == 1) {
                    // late reservation
                    reservation.setStatus(Status.MISSED);
                    customer.addMissedReservation();
                    if (restaurant.validateReservation(reservationDate, arrivalTime, reservation.partySize)) {
                        restaurant.seatCustomer(customer, reservation);
                        return false;
                    } else {
                        throw new Exception("Customer arrived late for reservation!\nThere was insufficient space to seat them!");
                    }
                } else {
                    // on-time reservation
                    customer.addCredits(reservation.getCredits());
                    reservation.setStatus(Status.COMPLETE);
                    restaurant.seatCustomer(customer, reservation);
                    return false;
                }
            } catch (DateTimeParseException e) {
                throw new Exception("Error in parsing provided date or time values!");
            }

        }
    }

    private boolean validateReservationGUI(String date, String time, int partySize) throws Exception {
        int currCapacity = capacity;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datetime = date + " " + time;
        LocalDateTime ldt = null;
        try {
            ldt = LocalDateTime.parse(datetime, dateFormat);
        } catch (Exception _) {
            throw new Exception("Date and/or arrival time entered in incorrect format");
        }

        LocalDateTime two_hours_b4 = ldt.minusHours(2);
        LocalDateTime two_hours_after = ldt.plusHours(2);

        for (Reservation reservation : reservations) {
            LocalDateTime existingTime = reservation.reservationDateTime;
            if (two_hours_b4.isBefore(existingTime) && two_hours_after.isAfter(existingTime) && (reservation.sts == Status.CONFIRMED || reservation.sts == Status.COMPLETE || (reservation.sts == Status.WALKON && reservation.seated))) {
                currCapacity -= reservation.partySize;
            }
        }

        return currCapacity >= partySize;
    }

    public void orderMenuItemGUI(String customerID, String date, String time, String selectedItem, Integer selectedQuantity) throws Exception {
        String reservationKey = customerID + restaurantId + date + time;
        Customer customer = CustomerRegistry.getCustomer(customerID);
        Reservation reservation = ReservationRegistry.getReservation(reservationKey);
        MenuItem item = MenuItemRegistry.getMenuItem(selectedItem);
        int itemPrice = itemPrices.get(item);
        int netPrice = itemPrice * selectedQuantity;
        if (customer.checkFunds(netPrice)) {
            this.revenue += netPrice;
            reservation.incrementBill(netPrice);
            customer.decrementFunds(netPrice);
        } else {
            // customer is broke
            throw new Exception("Customer has insufficient funds to complete requested order!");
        }
    }
}
