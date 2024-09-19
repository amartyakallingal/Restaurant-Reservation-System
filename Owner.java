import java.util.ArrayList;
import java.util.Objects;

public class Owner {
    final String ownerID;
    final String firstName;
    final String lastName;
    final String city;
    final String state;
    final String zipCode;
    final String experienceStartDate;
    final String group;

    private final ArrayList<String> owned;


    public Owner(String ownerID, String firstName, String lastName, String city, String state, String zipCode,
                 String experienceStartDate, String groupName) {
        if (OwnerRegistry.containsOwner(ownerID)) {
            throw new IllegalArgumentException("ERROR: duplicate unique identifier");
        }

        this.ownerID = ownerID;
        this.firstName = firstName;
        this.lastName = Objects.requireNonNullElse(lastName, "");
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.experienceStartDate = experienceStartDate;
        this.group = groupName;
        this.owned = new ArrayList<>();

        System.out.printf("Owner added: %s - %s %s\n", ownerID, firstName, lastName);
    }

    public void addRestaurant(String restaurantId) {
        owned.add(restaurantId);
    }

    public void viewRestaurants() {
        Restaurant restaurant;
        for (String restId : owned) {
            restaurant = RestaurantRegistry.getRestaurant(restId);
            System.out.printf("%s (%s): Unified License - %s\n", restId, restaurant.name, restaurant.license);
        }
    }

    public boolean belongsTo(String groupName) {
        return this.group.equals(groupName);
    }

    public String displayRestaurants() {
        StringBuilder builder = new StringBuilder();

        Restaurant restaurant;
        for (String restId : owned) {
            restaurant = RestaurantRegistry.getRestaurant(restId);
            builder.append(restaurant.name);
            builder.append(" (").append(restaurant.restaurantId).append(")");
            builder.append("\n");
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}