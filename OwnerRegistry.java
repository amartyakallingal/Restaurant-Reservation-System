import java.util.ArrayList;
import java.util.HashMap;

public class OwnerRegistry {
    private static OwnerRegistry instance = null;

    private final HashMap<String, Owner> registry;

    private OwnerRegistry() {
        registry = new HashMap<>();
    }

    public static synchronized OwnerRegistry getOwnerRegistry() {
        if (instance == null) {
            instance = new OwnerRegistry();
        }

        return instance;
    }

    public static synchronized Owner getOwner(String ownerID) {
        if (instance == null) {
            instance = new OwnerRegistry();
        }

        return instance.registry.getOrDefault(ownerID, null);
    }

    public static boolean containsOwner(String ownerId) {
        if (instance == null) {
            instance = new OwnerRegistry();
        }

        return instance.registry.containsKey(ownerId);
    }

    public static void createOwner(String id, String fname, String lname, String city, String state, String zipCode, String startDate, String groupName) {
        if (instance == null) {
            instance = new OwnerRegistry();
        }
        Owner owner;
        try {
            owner = new Owner(id, fname, lname, city, state, zipCode, startDate, groupName);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        instance.registry.put(id, owner);
    }

    public static void viewOwners(String groupName) {
        if (instance == null) {
            instance = new OwnerRegistry();
        }

        for (Owner owner : instance.registry.values()) {
            if (owner.belongsTo(groupName)) {
                System.out.printf("%s %s\n", owner.firstName, owner.lastName);
            }
        }
    }

    public static void viewRestaurantsOwnedBy(String ownerId) {
        Owner owner = instance.registry.getOrDefault(ownerId, null);

        if (owner == null) {
            System.out.println("ERROR: The owner does not exist");
            return;
        }

        owner.viewRestaurants();
    }

    public static String displayRestaurantsOwnedBy(String ownerId) {
        Owner owner = instance.registry.getOrDefault(ownerId, null);

        if (owner == null) {
            System.out.println("ERROR: The owner does not exist");
            return "";
        }

        return owner.displayRestaurants();
    }

    public static String displayOwners(String groupName) {
        if (instance == null) {
            instance = new OwnerRegistry();
        }

        StringBuilder builder = new StringBuilder();
        for (Owner owner : instance.registry.values()) {
            if (owner.belongsTo(groupName)) {
                builder.append(owner.firstName + " " + owner.lastName);
                builder.append("\n");
            }
        }
        return builder.toString();
    }


    public static String[] displayOwnerIDs() {
        if (instance == null) {
            instance = new OwnerRegistry();
        }

        return instance.registry.keySet().toArray(new String[0]);
    }
}
