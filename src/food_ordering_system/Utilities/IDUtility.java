package food_ordering_system.Utilities;

import java.io.*;
import java.util.*;

public class IDUtility {
    public String generateUserID(String userType, String userFilePath) {
        String prefix = userType.equals("Customer") ? "C" :
                userType.equals("Vendor") ? "V" :
                        userType.equals("Runner") ? "R" : null;

        if (prefix == null) return null;

        int nextID = 1;  // Default to C001, R001, V001
        if (userFilePath == null) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(userFilePath))) {
            String lastLine = null, line;
            while ((line = br.readLine()) != null) {
                lastLine = line;
            }

            if (lastLine != null) {
                String[] parts = lastLine.split(",");
                if (parts[0].startsWith(prefix)) {
                    int lastNum = Integer.parseInt(parts[0].substring(1)); // Extract number
                    nextID = lastNum + 1; // Increment
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file for ID generation: " + e.getMessage());
        }

        return String.format("%s%03d", prefix, nextID); // Format as C001, R002, etc.
    }

    public String generateTransactionID() {
        String transactionFilePath = "src/food_ordering_system/Data/transactions.txt";
        int transactionCount = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(transactionFilePath))) {
            String line;
            br.readLine();//skip header
            while ((line = br.readLine()) != null) {
                transactionCount++;
            }
        } catch (IOException e) {
            // If the file does not exist, start from 1
        }

        return String.format("T%03d", transactionCount);
    }

    public String generateNotificationID(){
        String notificationFilePath = "src/food_ordering_system/Data/notifications.txt";
        int notificationCount = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(notificationFilePath))) {
            String line;
            br.readLine();//skip header
            while ((line = br.readLine()) != null) {
                notificationCount++;
            }
        } catch (IOException e) {
            // If the file does not exist, start from 1
        }

        return String.format("NOTIF%03d", notificationCount);
    }

    public String generateItemID(String vendorID, String itemsFilePath) {
        if (itemsFilePath == null || vendorID == null) return null;

        int nextID = 1; // Default to V###I001
        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].trim().equals(vendorID)) { // Match Vendor ID
                    String itemID = parts[0].trim(); // Get Item ID
                    int lastNum = Integer.parseInt(itemID.substring(itemID.indexOf("I") + 1)); // Extract the numeric part
                    nextID = Math.max(nextID, lastNum + 1); // Update nextID
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file for ID generation: " + e.getMessage());
        }

        return String.format("%sI%03d", vendorID, nextID); // Format as V###I###
    }

    public String generateOrderID() {
        String ordersFilePath = "src/food_ordering_system/Data/orders.txt";
        int orderCount = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            br.readLine();//skip header
            while ((line = br.readLine()) != null) {
                orderCount++;
            }
        } catch (IOException e) {
            // If the file does not exist, start from 1
        }

        return String.format("ORD%03d", orderCount);
    }

    public List<String> parseItemIDs(String input) {
        // Remove the square brackets (if present)
        String trimmed = input.trim();
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            trimmed = trimmed.substring(1, trimmed.length() - 1);
        }

        // Split by whitespace (one or more spaces)
        String[] items = trimmed.split("\\s+");

        // Return as List<String>
        return Arrays.asList(items);
    }
}
