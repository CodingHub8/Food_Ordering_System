package food_ordering_system.Controller;

import javax.swing.table.TableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private final String reviewsFilePath = "src/food_ordering_system/Data/reviews.txt";
    private final String vendorsFilePath = "src/food_ordering_system/Data/vendors.txt";
    private final String runnersFilePath = "src/food_ordering_system/Data/delivery_runners.txt";
    private final String menuItemsFilePath = "src/food_ordering_system/Data/menu_items.txt";

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

        return new VendorController.CustomTableModel(data, columnNames);
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
}
