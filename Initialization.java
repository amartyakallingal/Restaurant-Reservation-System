public class Initialization {
    public static void initialize(ReservationSystem system) {
        // add Owners
        system.createOwner("OWN001", "Ricardo", "Cardenas", "Orlando", "FL", "32801", "2022-05-30", "Restaurant Brands");
        system.createOwner("OWN002", "Andrall", "Pearson", "Louisville", "KY", "40213", "2015-07-17", "Yum Brands");
        system.createOwner("OWN003", "Joshua", "Kobza", "Toronto", "ON", "M5X", "2023-05-01", "Restaurant Brands");
        // add Restaurants
        system.createRestaurant("REST001", "Mellow Mushroom", "Boca Raton", "FL", "33431", 8, "OWN001", "FS817");
        system.createRestaurant("REST002", "Little Caesars", "Key West", "FL", "30289", 7, "OWN001", "FS526");
        system.createRestaurant("REST003", "Pizza Hut", "Buckhead", "GA", "30625", 7, "OWN002", "FS035");
        system.createRestaurant("REST004", "Domino’s", "Alpharetta", "GA", "30504", 7, "OWN002", "FS182");
        system.createRestaurant("REST005", "Papa Johns", "San Diego", "CA", "94105", 7, "OWN003", "FS942");
        system.createRestaurant("REST006", "Blaze Pizza", "San Francisco", "CA", "92101", 7, "OWN003", "FS752");

        // add Customers
        system.createCustomer("CUST001", "Angel", "Cabrera", "Miami", "FL", "33122", 100);
        system.createCustomer("CUST002", "Mark", "Moss", "Atlanta", "GA", "30313", 100);
        system.createCustomer("CUST003", "Neel", "Ganediwal", "Parkland", "FL", "33067", 100);
        system.createCustomer("CUST004", "Henry", "Owen", "Chicago", "IL", "60629", 100);
        system.createCustomer("CUST005", "Michael", null, "New Orleans", "LA", "82402", 100);

        // add MenuItems
        system.createMenuItem("cheese pizza", "dough:mozzarella:tomato sauce");
        system.createMenuItem("vegetable pizza", "dough:mozzarella:olives:mushrooms");
        system.createMenuItem("pepperoni pizza", "dough:pork:tomato sauce:paprika");
        system.createMenuItem("hawaiian pizza", "dough:pineapple:mozzarella:bacon");
        system.createMenuItem("margherita pizza", "dough:mozzarella:tomato sauce");

        system.addMenuItem("cheese pizza", "REST001", 8);
        system.addMenuItem("vegetable pizza", "REST001", 13);
        system.addMenuItem("cheese pizza", "REST002", 9);
        system.addMenuItem("hawaiian pizza", "REST002", 12);
        system.addMenuItem("cheese pizza", "REST003", 12);
        system.addMenuItem("vegetable pizza", "REST003", 9);
        system.addMenuItem("pepperoni pizza", "REST004", 10);
        system.addMenuItem("hawaiian pizza", "REST004", 15);
        system.addMenuItem("pepperoni pizza", "REST005", 13);
        system.addMenuItem("vegetable pizza", "REST005", 14);
        system.addMenuItem("cheese pizza", "REST005", 9);
        system.addMenuItem("pepperoni pizza", "REST006", 11);
        system.addMenuItem("hawaiian pizza", "REST006", 12);

    }
}
