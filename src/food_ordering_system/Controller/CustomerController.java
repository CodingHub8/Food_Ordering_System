package food_ordering_system.Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private final String reviewsFilePath = "src/food_ordering_system/Data/reviews.txt";
    private final String vendorsFilePath = "src/food_ordering_system/Data/vendors.txt";
    private final String runnersFilePath = "src/food_ordering_system/Data/delivery_runners.txt";

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
