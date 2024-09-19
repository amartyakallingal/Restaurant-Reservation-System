import java.util.Scanner;
public class RestaurantController {
    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";
        ReservationSystem system = new ReservationSystem();

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                for (int i = 0; i < tokens.length; ++i) {
                    tokens[i] = tokens[i].trim();
                }

                if (tokens[0].indexOf("//") == 0) {
                    // System.out.println(wholeInputLine);
                    continue;
                } else if (wholeInputLine.isEmpty()) {
                    continue;
                }
                System.out.println(">> " + wholeInputLine.trim());
                if (tokens[0].equals("create_restaurant")) {
                    system.createRestaurant(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5],
                            Integer.parseInt(tokens[6]), tokens[7], tokens[8]);

                } else if (tokens[0].equals("run_demo")) {
                    Initialization.initialize(system);
                }
                else if (tokens[0].equals("create_customer")) {
//                    System.out.print("unique_identifier: " + tokens[1] + ", first_name: " + tokens[2] + ", last_name: " + tokens[3]);
//                    System.out.print(", city: " + tokens[4] + ", state: " + tokens[5]  + ", zip_code: " + tokens[6]);
//                    System.out.println(", funds: " + tokens[7]);
                    system.createCustomer(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6],
                            Double.parseDouble(tokens[7]));

                } else if (tokens[0].equals("create_owner")) {
                    system.createOwner(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6],
                            tokens[7], tokens[8]);
                }
                else if (tokens[0].equals("make_reservation")) {
//                    System.out.print("customer_identifier: " + tokens[1] + ", restaurant_identifier: " + tokens[2] + ", party_size: " + tokens[3]);
//                    System.out.println(", reservation_date: " + tokens[4] + ", reservation_time: " + tokens[5] + ", credits: " + tokens[6]);
                    system.requestReservation(tokens[1], tokens[2], Integer.parseInt(tokens[3]), tokens[4], tokens[5],
                            Integer.parseInt(tokens[6]));

                } else if (tokens[0].equals("customer_arrival")) {
                    system.customerArrival(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);

                } else if (tokens[0].equals("add_menu_item")) {
                    system.addMenuItem(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                } else if (tokens[0].equals("create_menu_item")) {
                    system.createMenuItem(tokens[1], tokens[2]);


                } else if (tokens[0].equals("order_menu_item")) {
                    system.orderMenuItem(tokens[1], tokens[2], tokens[3], tokens[4],
                            tokens[5], Integer.parseInt(tokens[6]));
                } else if (tokens[0].equals("calculate_average_price")) {
                    system.calculateAveragePrice(tokens[1]);
                } else if (tokens[0].equals("view_restaurants_owned")) {
                    system.viewRestaurantsOwnedBy(tokens[1]);
                }
                else if (tokens[0].equals("exit")) {
                    System.out.println("stop acknowledged");
                    break;
                } else if (tokens[0].equals("view_menu_items")) {
                    system.viewItemsServedBy(tokens[1]);
                } else if (tokens[0].equals("view_all_customers")) {
                    system.viewAllCustomers();
                } else if (tokens[0].equals("view_all_menu_items")) {
                    system.viewAllMenuItems();
                } else if (tokens[0].equals("view_all_restaurants")) {
                    system.viewAllRestaurants();
                } else if (tokens[0].equals("view_ingredients")) {
                    system.viewIngredients(tokens[1]);
                } else if (tokens[0].equals("view_owners")) {
                    system.viewOwners(tokens[1]);
                } else if (tokens[0].equals("calculate_item_popularity")) {
                    system.calculateItemPopularity(tokens[1]);
                } else if (tokens[0].equals("customer_review")) {
                    system.makeReview(tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), tokens[6]);
                }
                else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
                System.out.println();
            } catch (Exception e) {
                displayMessage("error", "during command loop >> execution");
                e.printStackTrace();
                System.out.println();
            }
        }
//        System.out.println("simulation terminated");
        commandLineInput.close();
    }

    void displayMessage(String status, String text_output) {
        System.out.println(status.toUpperCase() + ": " + text_output.toLowerCase());
    }
}
