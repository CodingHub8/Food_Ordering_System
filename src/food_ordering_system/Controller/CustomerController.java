package food_ordering_system.Controller;

import food_ordering_system.Models.*;
import food_ordering_system.Utilities.CustomTableModel;
import food_ordering_system.Utilities.IDUtility;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CustomerController {
    private final String transactionFilePath = "src/food_ordering_system/Data/transactions.txt";
    private final String complaintFilePath = "src/food_ordering_system/Data/complaints.txt";
    private Customer customer = new Customer("", "", "", 0.0);
    private MenuItem menuItem = new MenuItem();
    private Vendor vendor = new Vendor("", "", "", 0.0);
    private Runner runner = new Runner("", "", "", 0.0);
    private Review review = new Review();
    private Order order = new Order();
    private List<Order> orders = new ArrayList<>();

    public Customer getCustomerDetails(String custID){
        try (BufferedReader br = new BufferedReader(new FileReader(customer.getFilePath()))) {
            String line;
            br.readLine();//skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts[0].equals(custID)){
                    for(int i = 0; i < parts.length; i++){
                        parts[i] = parts[i].trim();
                    }

                    customer = new Customer(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading ile: " + e.getMessage());
        }
        return customer;
    }

    public String getReviews(){
        String reviews = "";
        try (BufferedReader br = new BufferedReader(new FileReader(review.getFilePath()))) {
            String line;
            br.readLine();// skip header
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                //e.g C001 - ORD001: Food is good (V001 was given 4.5 rating)
                reviews += row[2].trim() + " - " + row[1].trim() + ": " + row[5].trim() + " (" + row[3].trim() + " was given " + row[4].trim() + ")\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews.trim();
    }

    private List<String[]> readTransactionHistory(String custID){
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(transactionFilePath))) {
            String line;
            br.readLine();// skip header
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if(row[1].trim().equals(custID)){
                    data.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public TableModel loadTransactionData(String custID) {
        List<String[]> data = readTransactionHistory(custID);

        String header = "Transaction ID, Customer ID, Amount, Timestamp";
        String[] columnNames = header.split(",");

        for(int i = 0; i < columnNames.length; i++){
            columnNames[i] = columnNames[i].trim();
        }

        return new CustomTableModel(data, columnNames);
    }

    private List<String[]> readMenuData(){
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(menuItem.getFilePath()))) {
            String line;
            br.readLine();// skip header
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public TableModel loadMenuData() {
        List<String[]> data = readMenuData();

        String header = "Item ID, Vendor ID, Name, Price (RM), Description";
        String[] columnNames = header.split(",");

        for(int i = 0; i < columnNames.length; i++){
            columnNames[i] = columnNames[i].trim();
        }

        return new CustomTableModel(data, columnNames);
    }

    public TableModel loadActiveOrders(String custID){
        List<String[]> data = readOrders(custID, "Pending", "Preparing");

        String header = "Order ID, CustomerID, Vendor ID, itemID(s), Total Amount (RM), Status, Option, Timestamp";
        String[] columnNames = header.split(",");

        for(int i = 0; i < columnNames.length; i++){
            columnNames[i] = columnNames[i].trim();
        }

        return new CustomTableModel(data, columnNames);
    }

    private List<String[]> readOrders(String custID, String... status){
        List<String[]> data = new ArrayList<>();
        orders.clear();//prevent duplicate entry

        try (BufferedReader br = new BufferedReader(new FileReader(order.getFilePath()))) {
            String line;
            br.readLine();// skip header
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                List<String> itemIDs = new IDUtility().parseItemIDs(row[3]);

                for(int i = 0; i < row.length; i++){
                    row[i] = row[i].trim();
                }

                order = new Order(row[0], row[1], row[2], itemIDs, Double.parseDouble(row[4]), row[5], row[6], row[7]);

                if(row[1].equals(custID)) {
                    if(status.length > 1){
                        if(row[5].equals(status[0]) || row[5].equals(status[1])){
                            orders.add(order);
                            data.add(row);
                        }
                    } else {
                        if(row[5].equals(status[0])){
                            orders.add(order);
                            data.add(row);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public TableModel loadOrderHistory(String custID){
        List<String[]> data = readOrders(custID, "Complete");
        String header = "Order ID, CustomerID, Vendor ID, itemID(s), Total Amount (RM), Status, Option, Timestamp";
        String[] columnNames = header.split(",");
        for(int i = 0; i < columnNames.length; i++){
            columnNames[i] = columnNames[i].trim();
        }

        return new CustomTableModel(data, columnNames);
    }

    private List<Order> readExistingOrder(){
        List<Order> existingOrders = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(order.getFilePath()))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");

                for (int i = 0; i < row.length; i++){
                    row[i] = row[i].trim();
                }
                String existingItemIDs = row[3].substring(1, row[3].length() - 1);
                List<String> existingItems = Arrays.asList(existingItemIDs.split(" "));

                existingOrders.add(new Order(row[0], row[1], row[2], existingItems, Double.parseDouble(row[4]), row[5], row[6], row[7])); // Convert line to Order object
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading orders file: " + e.getMessage());
        }
        return existingOrders;
    }

    public void addOrder(String custID, String vendorID, String itemIDs, Double amount, String option){
        List<Order> existingOrders = readExistingOrder();

        //write new data
        String newOrderID = new IDUtility().generateOrderID();
        itemIDs = itemIDs.substring(1, itemIDs.length() - 1);
        List<String> items = Arrays.asList(itemIDs.split(" "));

        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());

        if(option.equals("Delivery")){
            amount += 5.00;
        }

        existingOrders.add(new Order(newOrderID, custID, vendorID, items, amount, "Pending", option, timestamp));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(order.getFilePath()))) {
            bw.write("Order ID, CustomerID, Vendor ID, itemID(s), Total Amount (RM), Status, Option, Timestamp");//header
            bw.newLine();

            for (Order order : existingOrders) {
                itemIDs = "[";
                for(String itemID : order.getItemIDs()){
                    itemIDs += itemID + " ";
                }
                itemIDs = itemIDs.trim() + "]";

                bw.write(order.getOrderID() + ", " + order.getCustomerID() + ", " + order.getVendorID() +
                        ", " + itemIDs + ", " + order.getTotalAmount() + ", " + order.getStatus() +
                        ", " + order.getOption() + ", " + order.getTimestamp());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing orders file: " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Successfully reordered", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addComplaints(String custID, String title, String message) {
        List<String> fileContents = new ArrayList<>();
        String lastComplaintID = "COMP000";
        String managerReply = ""; // Default reply for new complaints

        // Step 1: Read the existing file and get the last Complaint ID
        try (BufferedReader br = new BufferedReader(new FileReader(complaintFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContents.add(line);

                // Extract last Complaint ID
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",", 5); // Split into max 5 parts
                    if (parts[0].startsWith("COMP")) {
                        lastComplaintID = parts[0].trim();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading complaints file: " + e.getMessage());
        }

        // Step 2: Generate the next Complaint ID
        int nextID = Integer.parseInt(lastComplaintID.substring(4)) + 1;
        String newComplaintID = String.format("COMP%03d", nextID);

        // Step 3: Create the new complaint entry
        String newComplaint = String.format(
                "%s, %s, \"%s\", \"%s\", \"%s\"",
                newComplaintID, custID, title, message, managerReply
        );
        fileContents.add(newComplaint);

        // Step 4: Write the updated contents back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(complaintFilePath))) {
            for (String line : fileContents) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing complaints file: " + e.getMessage());
        }
    }

    public ComboBoxModel<String> loadOrderIDs(String custID) {
        JComboBox<String> orderIDs = new JComboBox<>();

        orderIDs.addItem("Select Order ID");
        readOrders(custID, "Complete");

        for(Order order : orders){
            if(order.getCustomerID().equals(custID)){
                orderIDs.addItem(order.getOrderID());
            }
        }
        return orderIDs.getModel();
    }

    public Order getOrderForReorder(String orderID, String custID) {
        readOrders(custID, "Complete");
        for(Order order : orders){
            if(order.getOrderID().equals(orderID)){
                return order;
            }
        }

        return null;
    }

    public void loadTargetIDs(JComboBox<String> cboTargetIDs) {
        cboTargetIDs.removeAllItems();
        cboTargetIDs.addItem("Select Target ID");

        try (BufferedReader br = new BufferedReader(new FileReader(runner.getFilePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("Runner ID")) {
                    continue; // Skip empty lines and header row
                }

                // Split the line into columns
                String[] parts = line.split(",", 4); // Maximum split into 4 parts
                String runnerID = parts[0].trim();

                // Add Runner ID to combo box
                cboTargetIDs.addItem(runnerID);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading runner file: " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(vendor.getFilePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("Vendor ID")) {
                    continue; // Skip empty lines and header row
                }

                // Split the line into columns
                String[] parts = line.split(",", 4); // Maximum split into 4 parts
                String vendorID = parts[0].trim();

                // Add Vendor ID to combo box
                cboTargetIDs.addItem(vendorID);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading vendor file: " + e.getMessage());
        }
    }

    public void addReview(String orderID, String custID, String targetID, String rating, String comment) {
        List<String> fileContents = new ArrayList<>();
        String newReviewID = "REV001";

        // Step 1: Read the file to get the current reviews and generate the next Review ID
        try (BufferedReader br = new BufferedReader(new FileReader(review.getFilePath()))) {
            String line;
            String lastReviewID = null;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.startsWith("Review ID")) {
                    fileContents.add(line); // Keep track of all lines
                    lastReviewID = line.split(",")[0].trim(); // Get the last Review ID
                }
            }

            // Generate the next Review ID if there are existing entries
            if (lastReviewID != null && lastReviewID.startsWith("REV")) {
                int lastNum = Integer.parseInt(lastReviewID.substring(3)); // Extract the number part
                newReviewID = String.format("REV%03d", lastNum + 1); // Increment and format
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading reviews file: " + e.getMessage());
        }

        // Step 2: Add the new review to the in-memory list
        String newReviewEntry = String.join(", ",
                newReviewID, orderID, custID, targetID, rating, comment
        );
        fileContents.add(newReviewEntry);

        // Step 3: Write all reviews (including the new one) back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(review.getFilePath()))) {
            bw.write("Review ID, Order ID, Customer ID, Target ID, Rating, Comment"); // Write header
            bw.newLine();
            for (String line : fileContents) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to reviews file: " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Review Submitted", "Review Submitted", JOptionPane.INFORMATION_MESSAGE);
    }
}
