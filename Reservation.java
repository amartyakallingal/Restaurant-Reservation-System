import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Reservation implements Comparable<Reservation> {
    private final String reservationId;
    private final Customer customer;
    private final Restaurant restaurant;
    private final String reservationDate;
    private final String reservationTime;
    int bill = 0;
    final int partySize;
    final int creditsAwarded;
    boolean seated = false;
    Status sts;
    LocalDateTime reservationDateTime;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

//    public Reservation(Customer customer, Restaurant restaurant, int partySize, String reservationDate,
//                       String reservationTime, int creditsAwarded) {
//        this.customer = customer;
//        this.restaurant = restaurant;
//        this.reservationDate = reservationDate;
//        this.reservationTime = reservationTime;
//        this.partySize = partySize;
//        this.creditsAwarded = creditsAwarded;
//        sts = Status.CONFIRMED;
//        this.reservationId = customer.customerId + restaurant.restaurantId + reservationDate + reservationTime;
//        String dateString = reservationDate + " " + reservationTime;
//        this.reservationDateTime = LocalDateTime.parse(dateString, Reservation.formatter);
//    }
    public Reservation(String custId, String restId, int partySize, String reservationDate, String reservationTime,
                       int creditsAwarded, boolean isWalkOn) {
        if (!isWalkOn) {
            this.sts = Status.PENDING;
            Restaurant restaurant;
            Customer customer;
            if (CustomerRegistry.containsCustomer(custId)) {
                customer = CustomerRegistry.getCustomer(custId);
            } else {
                throw new IllegalArgumentException("ERROR: Customer doesn't exist");
            }

            System.out.printf("Reservation requested for %s\n", customer.customerId);

            restaurant = RestaurantRegistry.getRestaurant(restId);
            if (restaurant == null) {
                throw new IllegalArgumentException("ERROR: Restaurant doesn't exist");
            }

            if (!customer.checkExistingReservations(reservationDate, reservationTime)) {
                throw new IllegalArgumentException("Reservation request denied, customer already has reservation with another restaurant" +
                        " within 2 hours of the requested time");
            }

            boolean hadError = false;
            String errorMessage = "One or more errors occurred!\n";
            if (custId == null || restId == null || reservationDate == null) {
                hadError = true;
                errorMessage += "\tEntered customerId, restaurantId, or reservationDate are null!\n";
            }
            if (!CustomerRegistry.containsCustomer(custId)) {
                hadError = true;
                errorMessage += "\tEntered customerId does not correspond to an existing Customer!\n";
            } else {
                customer = CustomerRegistry.getCustomer(custId);
                if (!customer.checkExistingReservations(reservationDate, reservationTime)) {
                    hadError = true;
                    errorMessage += "\tCustomer has existing reservation at entered time!\n";
                }
            }
            if (!RestaurantRegistry.containsRestaurant(restId)) {
                hadError = true;
                errorMessage += "\tEntered restaurantId does not correspond to an existing Restaurant!\n";
            } else {
                restaurant = RestaurantRegistry.getRestaurant(restId);
                if (!restaurant.validateReservation(reservationDate, reservationTime, partySize)) {
                    throw new IllegalArgumentException("Reservation request denied, table has another active reservation within 2 hours of the requested time\n");
                }
            }
            if (partySize < 1) {
                hadError = true;
                errorMessage += "\tParty size cannot be less than 1!\n";
            }
            if (creditsAwarded < 0) {
                hadError = true;
                errorMessage += "\tCredits awarded cannot be less than 0!\n";
            }

            if (hadError) {
                throw new IllegalArgumentException(errorMessage);
            }


            restaurant = RestaurantRegistry.getRestaurant(restId);
            if (!restaurant.validateReservation(reservationDate, reservationTime, partySize)) {
                this.sts = Status.DENIED;
                throw new IllegalArgumentException("Reservation request denied, table has another active reservation within 2 hours of the requested time");
            } else {
                this.sts = Status.CONFIRMED;
                System.out.println("Reservation confirmed");
            }

            this.customer = CustomerRegistry.getCustomer(custId);
            this.restaurant = RestaurantRegistry.getRestaurant(restId);
            this.reservationDate = reservationDate;
            this.reservationTime = reservationTime;
            this.partySize = partySize;
            this.creditsAwarded = creditsAwarded;
            this.reservationDateTime = LocalDateTime.parse(reservationDate + " " + reservationTime, formatter);
            restaurant.insertReservation(this);
            customer.insertReservation(this);

            this.reservationId = custId + restId + reservationDate + reservationTime;

            ReservationRegistry.addReservation(reservationId, this);
            System.out.printf("Reservation made for %s (%s %s) at %s\n", custId, customer.firstName, customer.lastName, restaurant.name);
        } else {
            this.customer = CustomerRegistry.getCustomer(custId);
            if (customer == null) {
                throw new IllegalArgumentException("Customer does not exist!");
            }
            this.restaurant = RestaurantRegistry.getRestaurant(restId);
            if (restaurant == null) {
                throw new IllegalArgumentException("Restaurant does not exist!");
            }
            this.reservationDate = reservationDate;
            this.reservationTime = reservationTime;
            this.partySize = partySize;
            this.creditsAwarded = creditsAwarded;

            if (reservationTime.equals("null")) {
                System.out.printf("Error for walk on custId: %s  restId: %s date: %s time: %s", custId, restId, reservationDate, reservationTime);
            }
            this.reservationDateTime = LocalDateTime.parse(reservationDate + " " + reservationTime, formatter);
            restaurant.insertReservation(this);
            customer.insertReservation(this);
            this.sts = Status.WALKON;
            this.reservationId = custId + restId + reservationDate + reservationTime;
            ReservationRegistry.addReservation(this.reservationId, this);
        }
    }

    public String getReservationId() {
        return reservationId;
    }

    public int getCredits() {
        return this.creditsAwarded;
    }

    public void setStatus(Status status) {
        this.sts = status;
    }

    public void setSeated(boolean seated) {
        this.seated = seated;
    }

    @Override
    public int compareTo(Reservation o) {
        return o != null ? this.reservationDateTime.compareTo(((Reservation) o).reservationDateTime) : -1;
    }

    public boolean isSeated() {
        return seated;
    }
    public void incrementBill(int amt) { this.bill += amt; }
}
