import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ArrayList;

public class Customer {
    final String customerId;
    final String firstName;
    final String lastName;
    final String city;
    final String state;
    final String zipCode;
    double currentFunds;
    double initialFunds;
    int totalCredits;
    int missedReservations;
    private ArrayList<Reservation> existingReservations;
    private static int reviewNumber = 0;

    public Customer(String customerId, String firstName, String lastName, String city, String state, String zipCode, double
                    initialFunds) {
        boolean hadError = false;
        String errorMessage = "Failure to create new customer! The following errors occurred: \n";
        if (CustomerRegistry.containsCustomer(customerId)) {
            hadError = true;
            errorMessage += "\tThere is already a customer with the entered id!\n";
        }
        if (firstName == null || firstName.isEmpty()) {
            hadError = true;
            errorMessage += "\tEntered first name is null or empty!\n";
        }
        if (city == null || city.isEmpty()) {
            hadError = true;
            errorMessage += "\tEntered city is null or empty!\n";
        }
        if (state == null || state.isEmpty()) {
            hadError = true;
            errorMessage += "\tEntered state is null or empty!\n";
        }
        if (zipCode == null || zipCode.isEmpty()) {
            hadError = true;
            errorMessage += "\tEntered zip code is null or empty!\n";
        }

        if (initialFunds < 0) {
            hadError = true;
            errorMessage += "\tNegative initial funds is impossible!\n";
        }

        if (hadError) {
            throw new IllegalArgumentException(errorMessage);
        }

        this.customerId = customerId;
        this.firstName = firstName;

        if (lastName == null || lastName.equals("null")) {
            this.lastName = "";
        } else {
            this.lastName = lastName;
        }

        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.initialFunds = initialFunds;
        this.currentFunds = initialFunds;
        this.totalCredits = 0;
        this.missedReservations = 0;
        this.existingReservations = new ArrayList<>();

        if (this.lastName.isEmpty()) {
            System.out.printf("Customer added: %s - %s\n", this.customerId, this.firstName);
        } else {
            System.out.printf("Customer added: %s - %s %s\n", this.customerId, this.firstName, this.lastName);
        }
    }

    public boolean checkExistingReservations(String date, String time) { //date = yyyy-mm-dd time = hh:mm
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datetime = date + " " + time;
        LocalDateTime ldt = LocalDateTime.parse(datetime, dateFormat);
        LocalDateTime two_hours_b4 = ldt.minusHours(2);
        LocalDateTime two_hours_after = ldt.plusHours(2);

        for (Reservation reservation : existingReservations) {
            LocalDateTime existingTime = reservation.reservationDateTime;
            if (two_hours_b4.isBefore(existingTime) && two_hours_after.isAfter(existingTime)) {
                return false;
            }
        }

        return true;
    }

    public void insertReservation(Reservation reservation) {
        existingReservations.add(reservation);
    }

    public void addMissedReservation() {
        missedReservations++;
        if (missedReservations >= 3) {
            System.out.println("Misses: 3");
            System.out.printf("%s - 3 Misses reached, both misses and credits will reset back to 0", this);
            forfeitCredits();
        }
    }

    public void forfeitCredits() {
        this.totalCredits = 0;
        this.missedReservations = 0;
    }

    public void addCredits(int credits) {
        this.totalCredits += credits;
        this.totalCredits = Math.max(totalCredits, 0);
    }

    public Review makeReview(int score, String feedback, String tags) {
        return new Review(String.format(""+customerId+(reviewNumber++)), score, feedback, tags);
    }

    public void decrementFunds(int netPrice) {
        this.currentFunds -= netPrice;
    }

    public boolean checkFunds(int netPrice) {
        return this.currentFunds >= netPrice;
    }

    @Override
    public String toString() {
        if (lastName.isEmpty()) {
            return customerId + " (" + firstName + ")";
        } else {
            return customerId + " (" + firstName + " " + lastName + ")";
        }
    }
}