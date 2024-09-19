import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.util.Date;

public class MainUI {

    //Make Reservation Things
    private static ReservationSystem mainSystem;
    private static JTextField customerIdField;
    private static JTextField restaurantIdField;
    private static JTextField partySizeField;
    private static JTextField reservationDateField;
    private static JTextField reservationTimeField;
    private static JTextField creditsAwardedField;
    private static JButton submitButton;
    private static JButton returnButton;
    private static JTextArea restaurantListArea;
    private static JButton makeReservationButton;
    private static JButton arrivedButton;
    private static JButton returnButton2;



    public static void mainPage() {
        JFrame frame = new JFrame("Restaurant Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JPanel titlePanel = new JPanel();
        //JPanel buttonPanel = new JPanel();

        JLabel title = new JLabel("Restaurant Reservation System", SwingConstants.CENTER);
        title.setFont(new FontUIResource("Arial", Font.BOLD, 36));
        title.setBounds(0, 75, 600, 100);

        JButton custButton = new JButton("Customer Page");
        custButton.setBounds(75, 250, 150, 250);

        custButton.addActionListener(event -> {
            frame.setVisible(false);
            customerPage();
        });

        JButton ownerButton = new JButton("Owner Page");
        ownerButton.setBounds(225, 250, 150, 250);
        ownerButton.addActionListener(event -> {
            frame.setVisible(false);
            ownerPage();
        });

        // add a button for the registerCustomerPage
        JButton registerCustomerButton = new JButton("Register Person");
        registerCustomerButton.setBounds(375, 250, 150, 250);
        registerCustomerButton.addActionListener(event -> {
            frame.setVisible(false);
            registerCustomerPage();
        });

        titlePanel.setLayout(null);
        titlePanel.add(title);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        titlePanel.add(custButton);
        titlePanel.add(ownerButton);
        titlePanel.add(registerCustomerButton);

        frame.getContentPane().add(titlePanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void ownerPage() {
        JFrame ownerFrame = new JFrame("Owner Page");
        ownerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ownerFrame.setSize(600, 600);

        JPanel mainPanel = new JPanel();

        JLabel title = new JLabel("Owner Page", SwingConstants.CENTER);
        title.setFont(new FontUIResource("Default", Font.BOLD, 36));
        title.setBounds(0, 25, 600, 100);

        mainPanel.setLayout(null);
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        

        //Top: create restaurant
        JLabel restID = new JLabel("Restaurant ID: ");
        restID.setFont(new FontUIResource("Default", Font.BOLD, 12));
        restID.setBounds(20, 70, 100, 100);
        JLabel restName = new JLabel("Restaurant Name: ");
        restName.setFont(new FontUIResource("Default", Font.BOLD, 12));
        restName.setBounds(20, 90, 150, 100); //Extra long text
        JLabel city = new JLabel("City: ");
        city.setFont(new FontUIResource("Default", Font.BOLD, 12));
        city.setBounds(20, 110, 100, 100);
        JLabel state = new JLabel("State: ");
        state.setFont(new FontUIResource("Default", Font.BOLD, 12));
        state.setBounds(20, 130, 100, 100);
        JLabel zipCode = new JLabel("Zipcode: ");
        zipCode.setFont(new FontUIResource("Default", Font.BOLD, 12));
        zipCode.setBounds(20,150,100,100);
        JLabel capacity = new JLabel("Capacity: ");
        capacity.setFont(new FontUIResource("Default", Font.BOLD, 12));
        capacity.setBounds(20,170,100,100);
        JLabel ownerID = new JLabel("Owner ID: ");
        ownerID.setFont(new FontUIResource("Default", Font.BOLD, 12));
        ownerID.setBounds(20,190,100,100);
        JLabel license = new JLabel("License: ");
        license.setFont(new FontUIResource("Default", Font.BOLD, 12));
        license.setBounds(20,210,100,100);

        JTextField restIDTxt = new JTextField();
        restIDTxt.setBounds(110, 109,300, 25);
        JTextField restNameTxt = new JTextField();
        restNameTxt.setBounds(130, 129, 280, 25);
        JTextField cityTxt = new JTextField();
        cityTxt.setBounds(50,149,360,25);
        JTextField stateTxt = new JTextField();
        stateTxt.setBounds(60, 169, 350, 25);
        JTextField zipTxt = new JTextField();
        zipTxt.setBounds(75, 189, 335, 25);
        JTextField capacityTxt = new JTextField();
        capacityTxt.setBounds(80, 209, 330, 25);
        JComboBox<String> ownerIDselect = new JComboBox<>(mainSystem.displayOwnerIDs());
        ownerIDselect.setBounds(90, 229, 320, 25);
        JTextField licenseTxt = new JTextField();
        licenseTxt.setBounds(90, 249, 320, 25);

        mainPanel.add(restID);
        mainPanel.add(restName);
        mainPanel.add(city);
        mainPanel.add(state);
        mainPanel.add(zipCode);
        mainPanel.add(capacity);
        mainPanel.add(ownerID);
        mainPanel.add(license);

        mainPanel.add(restIDTxt);
        mainPanel.add(restNameTxt);
        mainPanel.add(cityTxt);
        mainPanel.add(stateTxt);
        mainPanel.add(zipTxt);
        mainPanel.add(capacityTxt);
        mainPanel.add(ownerIDselect);
        mainPanel.add(licenseTxt);

        JButton createRest = new JButton("Create Restaurant");
        createRest.setBounds(430, 109, 150, 170);
        createRest.addActionListener(event -> {
            if(restIDTxt.getText().isEmpty() || restNameTxt.getText().isEmpty() || cityTxt.getText().isEmpty() || stateTxt.getText().isEmpty() || zipTxt.getText().isEmpty()
            || capacityTxt.getText().isEmpty() || licenseTxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(ownerFrame, "Restaurant not created");
            } else {
                mainSystem.createRestaurant(restIDTxt.getText(), restNameTxt.getText(), cityTxt.getText(),
                        stateTxt.getText(), zipTxt.getText(), Integer.parseInt(capacityTxt.getText()),
                        (String) ownerIDselect.getSelectedItem(), licenseTxt.getText());

                restIDTxt.setText("");
                restNameTxt.setText("");
                cityTxt.setText("");
                stateTxt.setText("");
                zipTxt.setText("");
                capacityTxt.setText("");
                licenseTxt.setText("");
            }
        });

        //Add Menu Item GUI
        JLabel itemName = new JLabel("Item Name: ");
        itemName.setFont(new FontUIResource("Default", Font.BOLD, 12));
        itemName.setBounds(20, 280, 100, 100);
        JLabel restID2 = new JLabel("Restaurant ID: ");
        restID2.setFont(new FontUIResource("Default", Font.BOLD, 12));
        restID2.setBounds(20,300,100,100);
        JLabel price = new JLabel("Price: ");
        price.setFont(new FontUIResource("Default", Font.BOLD, 12));
        price.setBounds(20,320,100,100);

        JTextField itemNameTxt = new JTextField();
        itemNameTxt.setBounds(95,319,315,25);
        JComboBox<String> restID2Select = new JComboBox<>(mainSystem.displayRestaurantIDs());
        restID2Select.setBounds(110, 339, 300, 25);
        JTextField priceTxt = new JTextField();
        priceTxt.setBounds(55, 359, 355, 25);

        JButton addItem = new JButton("Add Menu Item");
        addItem.setBounds(430, 319, 150, 70);
        addItem.addActionListener(event -> {

            if (itemNameTxt.getText().isEmpty() || priceTxt.getText().isEmpty()) {
               JOptionPane.showMessageDialog(ownerFrame, "Menu item not added");
            } else {
                mainSystem.createMenuItem(itemNameTxt.getText(), (String) restID2Select.getSelectedItem());
                mainSystem.addMenuItem(itemNameTxt.getText(), (String) restID2Select.getSelectedItem(), Integer.parseInt(priceTxt.getText()));

                itemNameTxt.setText("");
                priceTxt.setText("");
            }
        });

        mainPanel.add(itemName);
        mainPanel.add(restID2);
        mainPanel.add(price);
        mainPanel.add(itemNameTxt);
        mainPanel.add(restID2Select);
        mainPanel.add(priceTxt);

        //Display restaurants owned by owner
        JLabel ownerID2 = new JLabel("Owner ID: ");
        ownerID2.setFont(new FontUIResource("Default", Font.BOLD, 12));
        ownerID2.setBounds(20, 390, 100, 100);
        JComboBox<String> ownerID2Select = new JComboBox<>(mainSystem.displayOwnerIDs());
        ownerID2Select.setBounds(90, 429, 320, 25);

        JButton displayOwners = new JButton("Show Restaurants");
        displayOwners.setBounds(430, 429, 150, 25); 

        mainPanel.add(ownerID2);
        mainPanel.add(ownerID2Select);

        JTextArea restDisplay = new JTextArea();
        restDisplay.setLineWrap(true);
        restDisplay.setWrapStyleWord(true);
        restDisplay.setBounds(20, 460, 385, 100);

        displayOwners.addActionListener(event -> {
            restDisplay.setText("");
            restDisplay.append(mainSystem.displayRestaurantsOwnedBy((String) ownerID2Select.getSelectedItem()));
        });

        JButton returnScreen = new JButton("Main Page");
        returnScreen.setBounds(430, 460, 150, 100);
        returnScreen.addActionListener(event -> {
            ownerFrame.setVisible(false);
            mainPage();
        });


        mainPanel.add(createRest);
        mainPanel.add(addItem);
        mainPanel.add(displayOwners);
        mainPanel.add(restDisplay);
        mainPanel.add(returnScreen);

        ownerFrame.getContentPane().add(mainPanel);
        ownerFrame.setVisible(true);
    }


    public static void MakeReservationPage() {
        JFrame reservationFrame = new JFrame();
        reservationFrame.setTitle("Restaurant Reservation Form");
        reservationFrame.setSize(600, 600);
        reservationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reservationFrame.setLocationRelativeTo(null);

        // Create components
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>Make Reservation Page</div></html>");
        titleLabel.setFont(new Font("Default", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel customerIdLabel = new JLabel("Customer ID:");
        JComboBox<String> customerIdSelect = new JComboBox<>(mainSystem.displayCustomerIDs());

        JLabel restaurantIdLabel = new JLabel("Restaurant ID:");
        JComboBox<String> restaurantIdSelect = new JComboBox<String>(mainSystem.displayRestaurantIDs());

        JLabel partySizeLabel = new JLabel("Party Size:");
        partySizeField = new JTextField(20);

        JLabel reservationDateLabel = new JLabel("Reservation Date:");
        reservationDateField = new JTextField(20);

        JLabel reservationTimeLabel = new JLabel("Reservation Time:");
        reservationTimeField = new JTextField(20);

        JLabel creditsAwardedLabel = new JLabel("Credits Awarded:");
        creditsAwardedField = new JTextField(20);

        submitButton = new JButton("Submit");
        returnButton = new JButton("Return to Main Page");

        // Set up the layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 5, 5));
        formPanel.setBorder(new EmptyBorder(10, 100, 100, 20));
        //formPanel.setBorder(new EmptyBorder(20, 10, 10, 30));
        formPanel.add(customerIdLabel);
        formPanel.add(customerIdSelect);
        formPanel.add(restaurantIdLabel);
        formPanel.add(restaurantIdSelect);
        formPanel.add(partySizeLabel);
        formPanel.add(partySizeField);
        formPanel.add(reservationDateLabel);
        formPanel.add(reservationDateField);
        formPanel.add(reservationTimeLabel);
        formPanel.add(reservationTimeField);
        formPanel.add(creditsAwardedLabel);
        formPanel.add(creditsAwardedField);

        JPanel rightMarginPanel = new JPanel();
        rightMarginPanel.setPreferredSize(new Dimension(100, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(returnButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(rightMarginPanel, BorderLayout.EAST);

        reservationFrame.add(mainPanel);

        // Add action listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerId = (String) customerIdSelect.getSelectedItem();
                String restaurantId = (String) restaurantIdSelect.getSelectedItem();

                String reservationDate = reservationDateField.getText();
                String reservationTime = reservationTimeField.getText();

                if(customerId.isEmpty() || restaurantId.isEmpty() || reservationDate.isEmpty() || reservationTime.isEmpty() ||
                        partySizeField.getText().isEmpty() || creditsAwardedField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(reservationFrame, "Reservation not created as one or more fields are empty!");
                } else {


                    int partySize;
                    int creditsAwarded;
                    try {
                        partySize = Integer.parseInt(partySizeField.getText());
                        creditsAwarded = Integer.parseInt(creditsAwardedField.getText());

                    } catch (NumberFormatException _) {
                        JOptionPane.showMessageDialog(reservationFrame, "Invalid input for party size or credits awarded");
                        return;
                    }


                    try {
                        mainSystem.requestReservationGUI(customerId, restaurantId, partySize, reservationDate, reservationTime, creditsAwarded);
                        JOptionPane.showMessageDialog(reservationFrame, "Reservation submitted successfully!");
                        partySizeField.setText("");
                        reservationDateField.setText("");
                        reservationTimeField.setText("");
                        creditsAwardedField.setText("");

                        reservationFrame.setVisible(false);
                        customerPage();
                    } catch (DateTimeException dte) {
                        JOptionPane.showMessageDialog(reservationFrame, "ERROR: Date and/or time formatted incorrectly");
                    }
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(reservationFrame, exception.getMessage());
                    }


                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservationFrame.setVisible(false);
                mainPage();
            }
        });

        reservationFrame.setVisible(true);
    }


    public static void customerPage() {
        JFrame customerFrame = new JFrame();
        customerFrame.setTitle("Customer Page");
        customerFrame.setSize(600, 600);
        customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customerFrame.setLocationRelativeTo(null);

        // Create components
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>Customer Page</div></html>");
        titleLabel.setFont(new Font("Default", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel restaurantLabel = new JLabel("<html><div style='text-align: center;'>Restaurants:</div></html>");
        restaurantLabel.setFont(new Font("Default", Font.PLAIN, 18));
        //restaurantLabel.setHorizontalAlignment(JLabel.CENTER);

        restaurantListArea = new JTextArea();
        restaurantListArea.setEditable(false);
        restaurantListArea.append(mainSystem.displayAllRestaurants());

        JScrollPane scrollPane = new JScrollPane(restaurantListArea);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        scrollPane.setBorder(border);

        makeReservationButton = new JButton("Make Reservation");
        arrivedButton = new JButton("Arrived at Restaurant");
        returnButton = new JButton("Return to Main Page");

        // Set up the layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(titleLabel);
        titlePanel.add(restaurantLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(makeReservationButton);
        buttonPanel.add(arrivedButton);
        buttonPanel.add(returnButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        customerFrame.add(mainPanel);

        // Add action listeners
        makeReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                customerFrame.setVisible(false);
                MakeReservationPage();
            }
        });

        arrivedButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                customerFrame.setVisible(false);
                arrivePage();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                customerFrame.setVisible(false);
                mainPage();
            }
        });

        customerFrame.setVisible(true);
    }

    public static void arrivePage() {
        JFrame arriveFrame = new JFrame();
        arriveFrame.setTitle("Arrival Page");
        arriveFrame.setSize(600, 600);
        arriveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(null);

        JLabel arriveTitle = new JLabel("Arrival Page", SwingConstants.CENTER);
        arriveTitle.setFont(new FontUIResource("Default", Font.BOLD, 36));
        arriveTitle.setBounds(0, 25, 600, 100);

        JLabel custIDLabel = new JLabel("Customer ID: ");
        custIDLabel.setFont(new FontUIResource("Default", Font.PLAIN, 18));
        // insert a drop down to select from listed customers in the system
        JComboBox<String> custIDselect = new JComboBox<String>(mainSystem.displayCustomerIDs());
        custIDselect.setBounds(135, 135, 300, 30);




        custIDLabel.setBounds(20, 100, 200, 100);
        JLabel restIDLabel = new JLabel("Restaurant ID: ");
        restIDLabel.setFont(new FontUIResource("Default", Font.PLAIN, 18));
        restIDLabel.setBounds(20, 150, 200, 100);
        JLabel partySizeLabel = new JLabel("Arrival Time: ");
        partySizeLabel.setFont(new FontUIResource("Default", Font.PLAIN, 18));
        partySizeLabel.setBounds(20, 200, 200, 100);
        JLabel reservationDateLabel = new JLabel("Reservation Date: ");
        reservationDateLabel.setFont(new FontUIResource("Default", Font.PLAIN, 18));
        reservationDateLabel.setBounds(20, 250, 200, 100);
        JLabel reservationTimeLabel = new JLabel("Reservation Time: ");
        reservationTimeLabel.setFont(new FontUIResource("Default", Font.PLAIN, 18));
        reservationTimeLabel.setBounds(20, 300, 200, 100);

        //JTextField custIDField = new JTextField();
        //custIDField.setBounds(135, 135, 300, 30);
//        JTextField restIDField = new JTextField();
//        restIDField.setBounds(145, 185, 300, 30);
        JComboBox<String> restIDselect = new JComboBox<>(mainSystem.displayRestaurantIDs());
        restIDselect.setBounds(145, 185, 300, 30);
        JTextField arrivalTimeField = new JTextField();
        arrivalTimeField.setBounds(130, 235, 300, 30);
        JTextField reservationDateField = new JTextField();
        reservationDateField.setBounds(175, 285, 300, 30);
        JTextField reservationTimeField = new JTextField();
        reservationTimeField.setBounds(175, 335, 300, 30);

        titlePanel.add(arriveTitle);
        titlePanel.add(custIDLabel);
        titlePanel.add(restIDLabel);
        titlePanel.add(partySizeLabel);
        titlePanel.add(reservationDateLabel);
        titlePanel.add(reservationTimeLabel);

        titlePanel.add(custIDselect);
        titlePanel.add(restIDselect);
        titlePanel.add(arrivalTimeField);
        titlePanel.add(reservationDateField);
        titlePanel.add(reservationTimeField);

        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(100,400, 200, 100);
        continueButton.addActionListener(event -> {
            String customerID = (String) custIDselect.getSelectedItem();
            String restID = (String) restIDselect.getSelectedItem();
            String date = reservationDateField.getText();
            String reservationTime = reservationTimeField.getText();
            String arrivalTime = arrivalTimeField.getText();

            if (customerID.isEmpty() || restID.isEmpty() || date.isEmpty() || reservationTime.isEmpty() || arrivalTime.isEmpty()) {
                JOptionPane.showMessageDialog(arriveFrame, "One or more fields are empty!");
                return;
            }

            boolean walkIn = true;
            boolean success = false;
            try {
                walkIn = mainSystem.customerArrivalGUI(customerID, restID, date,
                        arrivalTime, reservationTime);
                success = true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(arriveFrame, e.getMessage());
            }

//            custIDField.setText("");
//            restIDField.setText("");
            reservationDateField.setText("");
            arrivalTimeField.setText("");
            reservationTimeField.setText("");


            if (success) {
                arriveFrame.setVisible(false);
                if (walkIn) {
                    orderPage(customerID, restID, date, arrivalTime);
                } else {
                    orderPage(customerID, restID, date, reservationTime);
                }
            }


        });

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(300, 400, 200, 100);
        returnButton.addActionListener(event -> {
            arriveFrame.setVisible(false);
            customerPage();
        });

        titlePanel.add(continueButton);
        titlePanel.add(returnButton);


        arriveFrame.getContentPane().add(titlePanel);
        arriveFrame.setVisible(true);
    }

    public static void orderPage(String customerID, String restID, String date, String time) {
        // Create the GUI on the event-dispatching thread
        SwingUtilities.invokeLater(() -> {
            // Create and set up the main frame
            JFrame frame = new JFrame("Order");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);
            frame.setLayout(new BorderLayout());

            // Create the main panel
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Create and set up the menu items dropdown
            String[] options = RestaurantRegistry.getMenuItemArray(restID);
            ;
            JComboBox<String> dropDown = new JComboBox<>(options);
            dropDown.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Create and set up the details button
            JButton showDetailsButton = new JButton("Show Details");
            showDetailsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Create and set up the details table
            String[] columnNames = {"Details"};
            DefaultTableModel detailsTableModel = new DefaultTableModel(columnNames, 0);
            JTable detailsTable = new JTable(detailsTableModel);
            detailsTable.setFillsViewportHeight(true);
            detailsTable.setGridColor(Color.BLACK);
            detailsTable.setShowGrid(true);
            detailsTable.setVisible(false);
            JScrollPane detailsScrollPane = new JScrollPane(detailsTable);

            // Add action listener to the details button
            showDetailsButton.addActionListener(e -> {
                String[] ingredients = mainSystem.getIngredientsArray((String) dropDown.getSelectedItem());
                int price = mainSystem.getMenuItemPrice((String) dropDown.getSelectedItem(), restID);
                double avgPrice = mainSystem.displayAveragePrice((String) dropDown.getSelectedItem());

                String selectedItem = (String) dropDown.getSelectedItem();
                if (selectedItem != null && !selectedItem.equals("Select")) {
                    Object[][] info = new Object[ingredients.length][2];
                    String priceString = String.format("Price: $%d", price);
                    String avgPriceString = String.format("Average Price: $%.2f", avgPrice);
                    StringBuilder ingredientsString = new StringBuilder("Ingredients: ");

                    for (int i = 0; i < ingredients.length; ++i) {
                        ingredientsString.append(ingredients[i]);
                        if (i != ingredients.length - 1) {
                            ingredientsString.append(", ");
                        }
                    }

                    detailsTableModel.setRowCount(0); // Clear existing rows
                    detailsTableModel.addRow(new Object[]{priceString});
                    detailsTableModel.addRow(new Object[]{avgPriceString});
                    detailsTableModel.addRow(new Object[]{ingredientsString.toString()});
                }
                detailsTable.setVisible(true);
            });

            // Create and set up the quantity dropdown
            Integer[] quantities = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            JComboBox<Integer> quantityDropDown = new JComboBox<>(quantities);
            quantityDropDown.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Create and set up the quantity panel
            JPanel quantityPanel = new JPanel();
            quantityPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            quantityPanel.add(new JLabel("Quantity:"));
            quantityPanel.add(quantityDropDown);

            // Create and set up the order button
            JButton orderButton = new JButton("Order");
            orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Create and set up the order table
            String[] orderColumnNames = {"Menu Item", "Amount", "Price"};
            DefaultTableModel tableModel = new DefaultTableModel(orderColumnNames, 0);
            JTable orderTable = new JTable(tableModel);
            orderTable.setFillsViewportHeight(true);
            orderTable.setGridColor(Color.BLACK);
            orderTable.setShowGrid(true);
            JScrollPane scrollPane = new JScrollPane(orderTable);

            // Create and set up the total price label
            JLabel totalPriceLabel = new JLabel("Total Price: $0.0");
            totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Add action listener to the order button
            orderButton.addActionListener(e -> {
                String selectedItem = (String) dropDown.getSelectedItem();
                Integer selectedQuantity = (Integer) quantityDropDown.getSelectedItem();
                if (selectedItem != null && !selectedItem.equals("Select") && selectedQuantity != null && selectedQuantity > 0) {
                    double price = selectedQuantity * mainSystem.getMenuItemPrice(selectedItem, restID); // Example price calculation
                    boolean found = false;
                    double totalPrice = 0.0;

                    try {
                        mainSystem.orderMenuItemGUI(customerID, restID, date, time, selectedItem, selectedQuantity);


                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            if (tableModel.getValueAt(i, 0).equals(selectedItem)) {
                                int existingQuantity = (Integer) tableModel.getValueAt(i, 1);
                                double existingPrice = (Double) tableModel.getValueAt(i, 2);
                                tableModel.setValueAt(existingQuantity + selectedQuantity, i, 1);
                                tableModel.setValueAt(existingPrice + price, i, 2);
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            Object[] row = {selectedItem, selectedQuantity, price};
                            tableModel.addRow(row);
                        }

                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            totalPrice += (Double) tableModel.getValueAt(i, 2);
                        }

                        // Update total price
                        totalPriceLabel.setText("Total Price: $" + totalPrice);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(frame, exception.getMessage());
                    }





                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Order");
                }
            });

            // Create and set up the finish ordering button
            JButton finishOrderButton = new JButton("Finish Ordering");
            finishOrderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            finishOrderButton.addActionListener(e -> {
                frame.setVisible(false);
                makeReviewScreen(customerID, restID, date, time);
            });

            // Add components to the main panel
            mainPanel.add(dropDown);
            mainPanel.add(showDetailsButton);
            mainPanel.add(detailsScrollPane); // Add details scroll pane here
            mainPanel.add(quantityPanel);
            mainPanel.add(orderButton);
            mainPanel.add(scrollPane);
            mainPanel.add(totalPriceLabel); // Add total price label here
            mainPanel.add(finishOrderButton);

            // Add the main panel to the frame
            frame.add(mainPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    public static void makeReviewScreen(String custID, String restID, String date, String time) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Restaurant Review");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);
            frame.setLayout(new BorderLayout());

            // Main panel
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Score section
            JPanel scorePanel = new JPanel(new BorderLayout());
            scorePanel.setBorder(BorderFactory.createTitledBorder("Score"));
            JLabel scoreLabel = new JLabel("Rate from 1 to 50:");
            JSlider scoreSlider = new JSlider(1, 50);
            JLabel sliderValueLabel = new JLabel("25", SwingConstants.CENTER); // Initial value centered
            sliderValueLabel.setFont(new Font("Default", Font.PLAIN, 20));

            scoreSlider.addChangeListener(e -> {
                int value = scoreSlider.getValue();
                sliderValueLabel.setText(String.valueOf(value));
            });

            // Centering the label under the slider
            JPanel sliderValuePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            sliderValuePanel.add(sliderValueLabel);

            scorePanel.add(scoreLabel, BorderLayout.NORTH);
            scorePanel.add(scoreSlider, BorderLayout.CENTER);
            scorePanel.add(sliderValuePanel, BorderLayout.SOUTH);

            // Feedback section
            JPanel feedbackPanel = new JPanel(new BorderLayout());
            feedbackPanel.setBorder(BorderFactory.createTitledBorder("Feedback"));
            JTextArea feedbackTextArea = new JTextArea(5, 20);
            feedbackTextArea.setLineWrap(true);
            feedbackTextArea.setWrapStyleWord(true);
            feedbackPanel.add(new JScrollPane(feedbackTextArea), BorderLayout.CENTER);

            // Tags section
            JPanel tagsPanel = new JPanel(new BorderLayout());
            tagsPanel.setBorder(BorderFactory.createTitledBorder("Tags"));
            JTextField tagsTextField = new JTextField(15);
            tagsPanel.add(tagsTextField, BorderLayout.CENTER);

            // Buttons section
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton submitButton = new JButton("Submit");
            JButton mainPageButton = new JButton("Main Page");
            mainPageButton.addActionListener(e -> {
                // Action to go back to the main page
                frame.setVisible(false);
                mainPage();
            });
            buttonsPanel.add(submitButton);
            buttonsPanel.add(mainPageButton);

            submitButton.addActionListener(e -> {
                int score = scoreSlider.getValue();
                String tags = tagsTextField.getText();
                mainSystem.makeReview(custID, restID, date, time, score, tags);

                frame.setVisible(false);
                customerPage();
            });

            // Adding components to the main panel
            mainPanel.add(scorePanel);
            mainPanel.add(feedbackPanel);
            mainPanel.add(tagsPanel);
            mainPanel.add(buttonsPanel);

            // Adding main panel to the frame
            frame.add(mainPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

    public static void registerCustomerPage() {
        // create a page with text boxes to fill in the following fields for CustomerRegistry.createCustomer(String id, String fname, String lname, String city, String state, String zipCode, double funds)
        // have a submit button, which on-click makes the respective call
        // the call will either on-exception open a pop up which displays the exception message or on success (no exception) opens a pop up saying "Customer created successfully"
        // have a return button which on-click returns to the main page

        JFrame frame = new JFrame("Register Person");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Register Person", SwingConstants.CENTER);
        titleLabel.setFont(new FontUIResource("Default", Font.BOLD, 24));
        titleLabel.setBounds(0, 0, 600, 50);
        mainPanel.add(titleLabel);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new FontUIResource("Default", Font.BOLD, 12));
        idLabel.setBounds(20, 50, 100, 100);
        JTextField idField = new JTextField();
        idField.setBounds(105, 88, 480,20);
        mainPanel.add(idLabel);
        mainPanel.add(idField);

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(20);
        firstNameLabel.setFont(new FontUIResource("Default", Font.BOLD, 12));
        firstNameLabel.setBounds(20, 70, 100, 100);
        firstNameField.setBounds(105,108,480,20);
        mainPanel.add(firstNameLabel);
        mainPanel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new FontUIResource("Default", Font.BOLD, 12));
        lastNameLabel.setBounds(20, 90, 100, 100);
        JTextField lastNameField = new JTextField(20);
        lastNameField.setBounds(105,128,480,20);
        mainPanel.add(lastNameLabel);
        mainPanel.add(lastNameField);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(new FontUIResource("Default", Font.BOLD, 12));
        cityLabel.setBounds(20, 110, 100, 100);
        JTextField cityField = new JTextField(20);
        cityField.setBounds(105,148,480,20);
        mainPanel.add(cityLabel);
        mainPanel.add(cityField);

        JLabel stateLabel = new JLabel("State:");
        stateLabel.setFont(new FontUIResource("Default", Font.BOLD, 12));
        stateLabel.setBounds(20, 130, 100, 100);
        JTextField stateField = new JTextField(20);
        stateField.setBounds(105,168,480,20);
        mainPanel.add(stateLabel);
        mainPanel.add(stateField);

        JLabel zipCodeLabel = new JLabel("Zip Code:");
        zipCodeLabel.setFont(new FontUIResource("Default", Font.BOLD, 12));
        zipCodeLabel.setBounds(20, 150, 100, 100);
        JTextField zipCodeField = new JTextField(20);
        zipCodeField.setBounds(105,188,480,20);
        mainPanel.add(zipCodeLabel);
        mainPanel.add(zipCodeField);

        JLabel fundsLabel = new JLabel("Funds:");
        fundsLabel.setFont(new FontUIResource("Default", Font.BOLD, 12));
        fundsLabel.setBounds(20, 220, 100, 100);
        JTextField fundsField = new JTextField(20);
        fundsField.setBounds(105,258,480,20);
        mainPanel.add(fundsLabel);
        mainPanel.add(fundsField);

        JButton registerCustBtn = new JButton("Register Customer");
        registerCustBtn.setBounds(10,280,580,25);
        JButton returnButton = new JButton("Return to Main Page");

        JLabel startDateLabel = new JLabel("Start Date: ");
        startDateLabel.setFont(new FontUIResource("Default", Font.BOLD, 12));
        startDateLabel.setBounds(20, 300, 100, 100);
        JTextField startDateField = new JTextField();
        startDateField.setBounds(105,338,480,20);
        mainPanel.add(startDateLabel);
        mainPanel.add(startDateField);

        JLabel groupNameLabel = new JLabel("Group Name:");
        groupNameLabel.setFont(new FontUIResource("Default", Font.BOLD, 12));
        groupNameLabel.setBounds(20, 320, 100, 100);
        JTextField groupNameField = new JTextField(20);
        groupNameField.setBounds(105,358,480,20);
        mainPanel.add(groupNameLabel);
        mainPanel.add(groupNameField);

        JButton registerOwnerBtn = new JButton("Register Owner");
        registerOwnerBtn.setBounds(10,380,580,25);

        mainPanel.add(registerCustBtn);
        mainPanel.add(registerOwnerBtn);
        mainPanel.add(returnButton);
        returnButton.setBounds(55, 420, 170, 130);


        JLabel displayOwnerLabel = new JLabel("Group:");
        displayOwnerLabel.setFont(new FontUIResource("Default", Font.BOLD, 12));
        displayOwnerLabel.setBounds(380, 390, 100, 100);
        JTextField displayOwnerField = new JTextField();
        displayOwnerField.setBounds(430,430,110,20);
        JButton displayCust = new JButton("View All Customers");
        displayCust.setBounds(225, 420, 150, 130);
        JButton displayOwners = new JButton("View All Owners");
        displayOwners.setBounds(375, 450, 170, 100);

        mainPanel.add(displayCust);
        mainPanel.add(displayOwners);
        mainPanel.add(displayOwnerLabel);
        mainPanel.add(displayOwnerField);


        frame.add(mainPanel);

        registerCustBtn.addActionListener(e -> {
            String id = idField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String city = cityField.getText();
            String state = stateField.getText();
            String zipCode = zipCodeField.getText();

            if(id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || city.isEmpty() || state.isEmpty() || zipCode.isEmpty() || fundsField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Customer not created");
            } else {
                mainSystem.createCustomer(id, firstName, lastName, city, state, zipCode, Double.parseDouble(fundsField.getText()));
                JOptionPane.showMessageDialog(frame, "Customer created successfully");
            }
        });

        registerOwnerBtn.addActionListener(e -> {
            String id = idField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String city = cityField.getText();
            String state = stateField.getText();
            String zipCode = zipCodeField.getText();
            String sd = startDateField.getText();
            String gn = groupNameField.getText();

            if(id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || city.isEmpty() || state.isEmpty() || zipCode.isEmpty() || sd.isEmpty() || gn.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Owner not created");
            } else {
                mainSystem.createOwner(id, firstName, lastName, city, state, zipCode, sd, gn);
                JOptionPane.showMessageDialog(frame, "Owner created successfully");
            }
        });

        returnButton.addActionListener(e -> {
            frame.setVisible(false);
            mainPage();
        });

        displayCust.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, mainSystem.displayAllCustomers());
        });

        displayOwners.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, mainSystem.displayOwners(displayOwnerField.getText()));
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        mainSystem = new ReservationSystem();
        Initialization.initialize(mainSystem);
        SwingUtilities.invokeLater(MainUI::mainPage);
    }
}    
