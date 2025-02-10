package food_ordering_system.Controller;

import food_ordering_system.Utilities.IDGenerator;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdministratorController {
    private String userFilePath;

    private String getUserFilePath(String user) {
        String basePath = "src/food_ordering_system/Data/";
        return switch (user) {
            case "Customer" -> basePath + "customers.txt";
            case "Vendor" -> basePath + "vendors.txt";
            case "Runner" -> basePath + "delivery_runners.txt";
            default -> null;
        };
    }

    public void createUser(String user, String name, String password) {
        userFilePath = getUserFilePath(user);
        if (userFilePath == null) return;

        String userID = new IDGenerator().generateUserID(user, userFilePath);
        String defaultValue = "0.0";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFilePath, true))) {
            bw.write(userID + ", " + name + ", " + password + ", " + defaultValue);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }

    public String[] viewUser(String userID) {
        userFilePath = "src/food_ordering_system/Data/";
        String[] userData = new String[4];

        if (userID.startsWith("C")) {//customer
            userFilePath += "customers.txt";
        } else if (userID.startsWith("R")) {//runner
            userFilePath += "delivery_runners.txt";
        } else if (userID.startsWith("V")) {//vendor
            userFilePath += "vendors.txt";
        } else {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            br.readLine();  // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts[0].equals(userID)) {//found
                    userData[0] = parts[0].trim();//ID
                    userData[1] = parts[1].trim();//name
                    userData[2] = parts[2].trim();//password
                    userData[3] = parts[3].trim();//credit for customer rating for others
                    return userData;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;//not found
    }

    public void updateUser(String userID, String newName, String newPassword) {
        userFilePath = getUserFilePathFromID(userID);
        if (userFilePath == null) return;

        List<String> fileContents = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equals(userID)) {
                    parts[1] = newName;
                    parts[2] = newPassword;
                    line = String.join(",", parts);
                    updated = true;
                }
                fileContents.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }

        if (updated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFilePath))) {
                for (String line : fileContents) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error saving updated user data: " + e.getMessage());
            }
        }
    }

    public void deleteUser(String userID) {
        userFilePath = getUserFilePathFromID(userID);
        if (userFilePath == null) return;

        List<String> fileContents = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(userID)) {
                    fileContents.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }

        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFilePath))) {
                for (String line : fileContents) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error saving updated user data: " + e.getMessage());
            }
        }
    }

    private String getUserFilePathFromID(String userID) {
        if (userID.startsWith("C")) return "src/food_ordering_system/Data/customers.txt";
        if (userID.startsWith("V")) return "src/food_ordering_system/Data/vendors.txt";
        if (userID.startsWith("R")) return "src/food_ordering_system/Data/delivery_runners.txt";
        return null;
    }

    public void addCustomerCredit(String custID, double credit) {
        String customerFilePath = "src/food_ordering_system/Data/customers.txt";
        String transactionFilePath = "src/food_ordering_system/Data/transactions.txt";
        String notificationFilePath = "src/food_ordering_system/Data/notifications.txt";
        List<String> fileContents = new ArrayList<>();
        boolean customerFound = false;
        double updatedCredit = 0.0;

        // Read customers.txt and update credit
        try (BufferedReader br = new BufferedReader(new FileReader(customerFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equals(custID)) {
                    double currentCredit = Double.parseDouble(parts[3]); // Get current credit
                    updatedCredit = currentCredit + credit; // Add new credit
                    parts[3] = String.format("%.2f", updatedCredit); // Update credit in string format
                    customerFound = true;
                }
                fileContents.add(String.join(",", parts));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading customer file: " + e.getMessage());
        }

        // Save updated data back to customers.txt
        if (customerFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(customerFilePath))) {
                for (String line : fileContents) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error updating customer credit: " + e.getMessage());
            }

            // Generate transaction ID and timestamp
            String transactionID = new IDGenerator().generateTransactionID();
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());

            // Write transaction to transactions.txt
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionFilePath, true))) {
                bw.write(transactionID + "," + custID + "," + String.format("%.2f", credit) + "," + timestamp);
                bw.newLine();
            } catch (IOException e) {
                throw new RuntimeException("Error writing transaction: " + e.getMessage());
            }

            // Generate notification
            String notificationID = new IDGenerator().generateNotificationID();
            String message = String.format("Successfully Top Up RM%.2f at %s", credit, timestamp);
            String notificationEntry = String.format("%s,%s,Credit Top Up,%s,false", notificationID, custID, message);

            // Write notification to notifications.txt
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(notificationFilePath, true))) {
                bw.write(notificationEntry);
                bw.newLine();
            } catch (IOException e) {
                throw new RuntimeException("Error writing notification: " + e.getMessage());
            }

            // Generate and print receipt
            printReceipt(custID);
        }
    }

    private void printReceipt(String custID) {
        String notificationFilePath = "src/food_ordering_system/Data/notifications.txt";
        List<String> fileContents = new ArrayList<>();
        boolean foundUnread = false;
        String notificationMessage = null;
        String notificationID = null;

        // Read notifications.txt and find unread notifications
        try (BufferedReader br = new BufferedReader(new FileReader(notificationFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[1].equals(custID) && parts[4].equals("false")) { // Unread notification
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
