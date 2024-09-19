

import java.util.HashMap;

public class CustomerRegistry {
    private static CustomerRegistry instance = null;

    private final HashMap<String, Customer> registry;

    private CustomerRegistry() {
        registry = new HashMap<>();
    }

    public static synchronized CustomerRegistry getCustomerRegistry() {
        if (instance == null) {
            instance = new CustomerRegistry();
        }

        return instance;
    }

    public static synchronized Customer getCustomer(String customerID) {
        if (instance == null) {
            instance = new CustomerRegistry();
        }

        return instance.registry.getOrDefault(customerID, null);
    }

    public static void createCustomer(String id, String fname, String lname, String city, String state, String zipCode,
                                      double funds) {
        if (instance == null) {
            instance = new CustomerRegistry();
        }

        if (instance.registry.containsKey(id)) {
            System.out.println("The customer already exists in the reservation system");
            return;
        }

        Customer customer;

        try {
            customer = new Customer(id, fname, lname, city, state, zipCode, funds);
        } catch (Exception e) {
            System.out.println(e.toString());
            return;
        }

        instance.registry.put(id, customer);
    }

    public static boolean containsCustomer(String custId) {
        if (instance == null) {
            instance = new CustomerRegistry();
        }

        return instance.registry.containsKey(custId);
    }

    public static void viewAllCustomers() {
        if (instance == null) {
            instance = new CustomerRegistry();
        }

        for (Customer cust : instance.registry.values()) {
            System.out.println(cust);
        }
    }

    public static double getCustomerFunds(String custId) {
        if (instance == null) {
            instance = new CustomerRegistry();
        }

        Customer customer = instance.registry.getOrDefault(custId, null);

        if (customer == null) {
            System.out.println("ERROR: The customer does not exist");
            return -1;
        }

        return customer.currentFunds;
    }

    public static String displayAllCustomers() {
        if (instance == null) {
            instance = new CustomerRegistry();
        }

        StringBuilder builder = new StringBuilder();

        for (Customer cust : instance.registry.values()) {
            builder.append(cust).append("\n");
        }

        return builder.toString();
    }


    public static String[] getCustomerIDs() {
        if (instance == null) {
            instance = new CustomerRegistry();
        }

        return instance.registry.keySet().toArray(new String[0]);
    }
}
