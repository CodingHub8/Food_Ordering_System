package food_ordering_system.Utilities;

import java.io.*;

public class IDGenerator {
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

    public String generateItemID(String vendorID, String itemsFilePath){
        String prefix = vendorID;

        int nextID = 1;  // Default to C001, R001, V001
        if (itemsFilePath == null) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
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
}
