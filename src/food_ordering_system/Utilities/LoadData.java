package food_ordering_system.Utilities;

import java.io.*;
import java.nio.file.*;

public class LoadData {
    private String filePath = "src/food_ordering_system/Data/";

    public void generateData() {
        createFileWithContent(filePath + "administrators.txt", getAdministratorData());
        createFileWithContent(filePath + "complaints.txt", getComplaintData());
        createFileWithContent(filePath + "customers.txt", getCustomerData());
        createFileWithContent(filePath + "delivery_runners.txt", getDeliveryRunnerData());
        createFileWithContent(filePath + "managers.txt", getManagerData());
        createFileWithContent(filePath + "menu_items.txt", getMenuItemData());
        createFileWithContent(filePath + "notifications.txt", getNotificationData());
        createFileWithContent(filePath + "orders.txt", getOrderData());
        createFileWithContent(filePath + "reviews.txt", getReviewData());
        createFileWithContent(filePath + "tasks.txt", getTaskData());
        createFileWithContent(filePath + "transactions.txt", getTransactionData());
        createFileWithContent(filePath + "vendors.txt", getVendorData());
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
                C001, John Doe, password123,50.00
                
                """;
    }

    private String getVendorData() {
        return """
                Vendor ID, Name, Password, Rating
                V001, Pizza Place, vendorpass1, 2.0
                V002, Burger Joint, vendorpass2, 4.3
                V003, Sushi Bar, vendorpass3, 4.1
                
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
                Order ID, Vendor ID, itemID(s), Total Amount (RM), Status, Option, Timestamp
                ORD001, V001, [NG1 MG1 NG2], 14.00, Complete, Dine-In, 13/2/2025 14:32
                ORD002, V001, [NG1 MG1], 15.00, Complete, Dine-In, 14/2/2025 14:34
                ORD003, V001, [NG1 MG1], 8.00, Complete, Dine-In, 15/2/2025 14:34
                ORD004, V001, [NG1 MG1], 22.00, Complete, Delivery, 14/3/2025 14:34
                ORD005, V002, [NG3 NG4], 11.00, Complete, Dine-In, 15/4/2025 14:34
                ORD006, V002, [NG5 MG2], 35.50, Complete, Delivery, 15/4/2025 15:30
                ORD007, V003, [MG1 NG2], 20.00, Complete, Dine-In, 16/4/2025 12:00
                ORD008, V003, [MG3 NG6], 18.50, Complete, Delivery, 10/6/2025 18:45
                ORD009, V003, [NG1 NG7], 27.00, Complete, Delivery, 12/6/2025 16:10
                ORD010, V003, [NG2 MG4], 13.00, Complete, Dine-In, 20/6/2025 13:00
                ORD011, V001, [MG5 MG6], 9.00, Complete, Dine-In, 21/6/2025 14:45
                ORD012, V002, [NG8 NG9], 45.00, Complete, Delivery, 15/7/2025 10:00
                ORD013, V001, [NG5 MG7], 50.00, Complete, Delivery, 20/7/2025 11:15
                ORD014, V003, [MG8 NG10], 33.00, Complete, Dine-In, 18/8/2025 19:00
                ORD015, V002, [NG11 MG9], 30.00, Complete, Dine-In, 22/9/2025 14:30
                ORD016, V003, [MG10 NG12], 42.00, Complete, Delivery, 5/11/2025 20:45
                ORD017, V001, [NG13 MG11], 26.50, Complete, Delivery, 10/11/2025 22:10
                ORD018, V002, [NG14 MG12], 12.00, Complete, Dine-In, 23/11/2025 16:00
                ORD019, V001, [NG15 MG13], 7.50, Complete, Delivery, 25/11/2025 14:00
                ORD020, V003, [NG16 MG14], 55.00, Complete, Delivery, 10/12/2025 10:45
                
                """;
    }

    private String getReviewData() {
        return """
                Review ID, Order ID, Customer ID, Target ID, Rating, Comment
                REV001, ORD001, C001, V001, 4.6, Food is good
                REV002, ORD001, C001, R001, 4.9, Fast Delivery
                
                """;
    }

    private String getTaskData() {
        return """
                Task ID, Order ID, Runner ID, Status, Location, Timestamp
                TK001, ORD002, R002, Pending, 14/2/2025 12:15
                TK002, ORD004, R003, Complete, 16/2/2025 13:20
                
                """;
    }

    private String getComplaintData(){
        return """
                Complaint ID, Customer ID, Title, Message, Manager Reply
                COMP001, C001, "Cluttered Table Arrangements", "Please arrange the table properly, like in grid or whatever.", "We will assign people to rearrange the table arrangements soon"
                COMP002, C001, "Vendor Bad Attitude", "Most of the vendors here treat their customers like shit. But they cook good though", "We will apply rules for the vendor to treat their customers properly. Thank you!"
                COMP003, C001, "Foul Trash Stench", "There is a foul odour because of the dumpster nearby. It is cutting off my appetite", "We will call the authorities. Thank you for reporting!"
                
                """;
    }

    private String getNotificationData(){
        return """
                Notification ID, Target ID, Title, Message, isViewed
                NOTIF001,C001,Credit Top Up,Successfully Top Up RM10.00 at 11/02/2025 00:49,false
                
                """;
    }

    private String getTransactionData(){
        return """
                Transaction ID, Customer ID, Amount, Timestamp
                T001,C001,20.00,11/02/2025 00:00
                T002,C001,10.00,11/02/2025 00:49
                
                """;
    }
}
