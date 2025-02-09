package food_ordering_system.Utilities;

import java.io.*;
import java.nio.file.*;

public class LoadData {
    public void generateData() {
        createFileWithContent("src/food_ordering_system/Data/customers.txt", getCustomerData());
        createFileWithContent("src/food_ordering_system/Data/vendors.txt", getVendorData());
        createFileWithContent("src/food_ordering_system/Data/delivery_runners.txt", getDeliveryRunnerData());
        createFileWithContent("src/food_ordering_system/Data/administrators.txt", getAdministratorData());
        createFileWithContent("src/food_ordering_system/Data/managers.txt", getManagerData());
        createFileWithContent("src/food_ordering_system/Data/menu_items.txt", getMenuItemData());
        createFileWithContent("src/food_ordering_system/Data/orders.txt", getOrderData());
        createFileWithContent("src/food_ordering_system/Data/reviews.txt", getReviewData());
        createFileWithContent("src/food_ordering_system/Data/tasks.txt", getTaskData());
    }

    private static void createFileWithContent(String filePath, String content) {
        File file = new File(filePath);
        if (!file.exists() || isFileEmpty(filePath)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
                System.out.println("File created or updated: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isFileEmpty(String filePath) {
        try {
            return Files.size(Paths.get(filePath)) == 0;
        } catch (IOException e) {
            e.printStackTrace();
            return true; // Assume file is empty if an error occurs
        }
    }

    private String getCustomerData() {
        return """
                Customer ID, Name, Password, Credit
                C001, John Doe, password123, 50.0
                
                """;
    }

    private String getVendorData() {
        return """
                Vendor ID, Name, Password, Rating
                V001, Pizza Place, vendorpass1, 0.0
                V002, Burger Joint, vendorpass2, 0.0
                V003, Sushi Bar, vendorpass3, 0.0
                
                """;
    }

    private String getDeliveryRunnerData() {
        return """
                Runner ID, Name, Password, Rating
                R001, Emily White, runnerpass1, 0.0
                R002, Michael Green, runnerpass2, 0.0
                R003, Sarah Black, runnerpass3, 0.0
                
                """;
    }

    private String getAdministratorData() {
        return """
                Admin ID, Name, Password
                AD001, Syarif Abdul Hassan, 12345678
                
                """;
    }

    private String getManagerData() {
        return """
                Manager ID, Name, Password
                MAN001, Abdullah Kazim Yusof, 12345678
                
                """;
    }

    private String getMenuItemData() {
        return """
                Item ID, Vendor ID, Name, Price (RM), Description
                V001I001, V001, Nasi Goreng, 4.50, Nasi Goreng biasa
                V001I002, V001, Mee Goreng, 4.50, Mee Goreng biasa
                V002I001, V002, Sirap Ais, 1.50, Sirap biasa
                V002I002, V002, Teh Ais, 1.50, Teh ais
                
                """;
    }

    private String getOrderData() {
        return """
                Order ID, Customer ID, Vendor ID, itemID(s), Total Amount (RM), Status, Option, Timestamp
                ORD001, C001, V001, [V001I001 V002I002], 6.00, Complete, Dine-In, 13/2/2025 14:32
                
                """;
    }

    private String getReviewData() {
        return """
                Review ID, Order ID, Customer ID, Target ID, Rating, Comment
                REV001, ORD001, C001, V001, 4.6, "Food is good"
                REV002, ORD001, C001, R001, 4.9, "Fast Delivery"
                
                """;
    }

    private String getTaskData() {
        return """
                Task ID, Order ID, Runner ID, Status, Location, Timestamp
                
                TK001, ORD002, R002, Pending, 14/2/2025 12:15
                TK002, ORD004, R004, Complete, 16/2/2025 13:20
                """;
    }
}
