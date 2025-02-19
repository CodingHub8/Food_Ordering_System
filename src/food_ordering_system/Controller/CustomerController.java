package food_ordering_system.Controller;

import food_ordering_system.Utilities.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private final String reviewsFilePath = "src/food_ordering_system/Data/reviews.txt";
    private final String vendorsFilePath = "src/food_ordering_system/Data/vendors.txt";
    private final String runnersFilePath = "src/food_ordering_system/Data/delivery_runners.txt";
    private final String menuItemsFilePath = "src/food_ordering_system/Data/menu_items.txt";
    private final String transactionFilePath = "src/food_ordering_system/Data/transactions.txt";
    private final String complaintFilePath = "src/food_ordering_system/Data/complaints.txt";
    private final String ordersFilePath = "src/food_ordering_system/Data/orders.txt";

    public String getReviews(){
        String reviews = "";
        try (BufferedReader br = new BufferedReader(new FileReader(reviewsFilePath));) {
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

    private List<String[]> readMenuDataFromFile(){
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(menuItemsFilePath))) {
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
        List<String[]> data = readMenuDataFromFile();

        String header = "Item ID, Vendor ID, Name, Price (RM), Description";
        String[] columnNames = header.split(",");

        for(int i = 0; i < columnNames.length; i++){
            columnNames[i] = columnNames[i].trim();
        }

        return new CustomTableModel(data, columnNames);
    }

    public String[] viewItems(){
        String[] itemData = new String[5];

        try (BufferedReader br = new BufferedReader(new FileReader(menuItemsFilePath))) {
            String line;
            br.readLine();  // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                itemData[0] = parts[0].trim();//item ID
                itemData[1] = parts[1].trim();//vendor ID
                itemData[2] = parts[2].trim();//name
                itemData[3] = parts[3].trim();//price
                itemData[4] = parts[4].trim();//description
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemData;//not found
    }

    public void addRating(String targetID) {
        List<Double> ratings = new ArrayList<>();
        List<String> fileContents = new ArrayList<>();
        boolean targetFound = false;
        String targetFilePath;

        // Determine if the target is a Vendor or Runner
        if (targetID.startsWith("V")) {
            targetFilePath = vendorsFilePath;
        } else if (targetID.startsWith("R")) {
            targetFilePath = runnersFilePath;
        } else {
            throw new IllegalArgumentException("Invalid Target ID: " + targetID);
        }

        // Read reviews.txt and collect ratings for the given target
        try (BufferedReader br = new BufferedReader(new FileReader(reviewsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[3].equals(targetID)) { // Check if review is for the target
                    ratings.add(Double.parseDouble(parts[4])); // Extract rating
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading reviews file: " + e.getMessage());
        }

        // Calculate average rating
        double averageRating = 0.0;
        if (!ratings.isEmpty()) {
            double sum = 0;
            for (double rate : ratings) {
                sum += rate;
            }
            averageRating = sum / ratings.size();
        }

        // Read the correct file (vendors.txt or delivery_runners.txt) and update the rating
        try (BufferedReader br = new BufferedReader(new FileReader(targetFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equals(targetID)) { // Found target (vendor or runner)
                    parts[3] = String.format("%.1f", averageRating); // Update rating
                    targetFound = true;
                }
                fileContents.add(String.join(",", parts));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }

        // Write the updated rating back to the correct file
        if (targetFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFilePath))) {
                for (String line : fileContents) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error updating rating: " + e.getMessage());
            }
        }
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

    public void addOrderIDs(JComboBox<String> cboOrderIDs, String custID) {
        cboOrderIDs.removeAllItems();
        cboOrderIDs.addItem("Select Order ID");
        try (BufferedReader br = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("Order ID")) {
                    continue; // Skip empty lines and header row
                }

                // Split the line into columns
                String[] parts = line.split(",", 8); // Maximum split into 8 parts
                String orderID = parts[0].trim();
                String customerID = parts[1].trim();

                // Add to combo box if Customer ID matches
                if (customerID.equals(custID)) {
                    cboOrderIDs.addItem(orderID);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading orders file: " + e.getMessage());
        }
    }

    public void addTargetIDs(JComboBox<String> cboTargetIDs) {
        cboTargetIDs.removeAllItems();
        cboTargetIDs.addItem("Select Target ID");
        try (BufferedReader br = new BufferedReader(new FileReader(runnersFilePath))) {
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

        try (BufferedReader br = new BufferedReader(new FileReader("vendor.txt"))) {
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
        String reviewsFilePath = "reviews.txt"; // Path to reviews.txt
        List<String> fileContents = new ArrayList<>();
        String newReviewID = "REV001";

        // Step 1: Read the file to get the current reviews and generate the next Review ID
        try (BufferedReader br = new BufferedReader(new FileReader(reviewsFilePath))) {
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reviewsFilePath))) {
            bw.write("Review ID, Order ID, Customer ID, Target ID, Rating, Comment"); // Write header
            bw.newLine();
            for (String line : fileContents) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to reviews file: " + e.getMessage());
        }
    }
}
