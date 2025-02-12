package food_ordering_system.Utilities;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Notifications {
    private final String notificationFilePath = "src/food_ordering_system/Data/notifications.txt";

    public void printReceipt(String userID) {
        List<String> fileContents = new ArrayList<>();
        boolean foundUnread = false;
        String notificationMessage = null;
        String notificationID = null;

        // Read notifications.txt and find unread notifications
        try (BufferedReader br = new BufferedReader(new FileReader(notificationFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[1].equals(userID) && parts[4].equals("false")) { // Unread notification
                    notificationMessage = parts[3]; // Message
                    notificationID = parts[0]; // Notification ID
                    parts[4] = "true"; // Mark as read
                    foundUnread = true;
                }
                fileContents.add(String.join(",", parts));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading notifications file: " + e.getMessage());
        }

        // Show notification popup if unread notifications exist
        if (foundUnread) {
            JOptionPane.showMessageDialog(null, notificationMessage, "Transaction Receipt", JOptionPane.INFORMATION_MESSAGE);

            // Update notifications.txt after user acknowledges
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(notificationFilePath))) {
                for (String line : fileContents) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error updating notifications file: " + e.getMessage());
            }
        }
    }
}
