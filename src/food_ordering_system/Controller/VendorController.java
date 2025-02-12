package food_ordering_system.Controller;

import food_ordering_system.Utilities.IDGenerator;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.io.*;
import java.text.*;
import java.util.*;

public class VendorController {
    private final String reviewsFilePath = "src/food_ordering_system/Data/reviews.txt";
    private final String vendorsFilePath = "src/food_ordering_system/Data/vendors.txt";
    private final String ordersFilePath = "src/food_ordering_system/Data/orders.txt";
    private final String itemsFilePath = "src/food_ordering_system/Data/menu_items.txt";

    public String[] getVendorDetails(String vendorID){
        String[] vendor = new String[4];

        try (BufferedReader br = new BufferedReader(new FileReader(vendorsFilePath))) {
            String line;
            br.readLine();//skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts[0].equals(vendorID)){
                    vendor[0] = parts[0];// ID
                    vendor[1] = parts[1];// name
                    vendor[2] = parts[2];// password
                    vendor[3] = parts[3];// rating
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading reviews file: " + e.getMessage());
        }

        return vendor;
    }

    public String getReviews(String vendorID){
         String reviews = "";

        try (BufferedReader br = new BufferedReader(new FileReader(reviewsFilePath))) {
            String line;
            br.readLine();//skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts[3].trim().equals(vendorID)){
                    for(int i = 0; i < parts.length; i++){
                        parts[i] = parts[i].trim();
                    }

                    reviews += parts[2] + ": " + parts[5] + " (" + parts[4] + " rating)" + "\n";
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading reviews file: " + e.getMessage());
        }
        return reviews;
    }

    public List<Double> getRevenues(String vendorID, String timestamp) {
        String ordersFilePath = "src/food_ordering_system/Data/orders.txt";

        // Data structure to store revenues by days, months, or quarters
        Map<String, Double> revenueMap = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            br.readLine();// skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String orderTimestamp = parts[6].trim(); // Extract timestamp
                double orderAmount = Double.parseDouble(parts[3]); // Extract total amount

                // Parse date from timestamp
                String key = getString(timestamp, orderTimestamp);

                // Add revenue to the respective key
                if(parts[1].trim().equals(vendorID)){
                    revenueMap.put(key, revenueMap.getOrDefault(key, 0.0) + orderAmount);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading or parsing orders file: " + e.getMessage());
        }

        // Add revenues from map to list in the correct order
        return new ArrayList<>(revenueMap.values());
    }

    private static String getString(String timestamp, String orderTimestamp) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = sdf.parse(orderTimestamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String key;
        switch (timestamp) {
            case "Days" -> {
                // Group revenues by specific day (dd/MM/yyyy)
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
                key = dayFormat.format(date);
            }
            case "Months" -> {
                // Group revenues by month (MM/yyyy)
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");
                key = monthFormat.format(date);
            }
            case "Quarters" -> {
                // Group revenues by quarter (Q/yyyy)
                int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
                int quarter = (month - 1) / 3 + 1; // Calculate quarter
                int year = calendar.get(Calendar.YEAR);
                key = "Q" + quarter + "/" + year;
            }
            default -> throw new IllegalArgumentException("Invalid timestamp type: " + timestamp);
        }
        return key;
    }

    public void createItem(String vendorID, String itemName, String itemPrice, String itemDesc){
        if (vendorID == null) {
            System.out.println("Invalid file path or vendor ID.");
            return;
        }

        String newItemID = new IDGenerator().generateItemID(vendorID, itemsFilePath); // Generate new Item ID

        // Create a new item line
        String newItem = String.format("%s, %s, %s, %.2f, %s", newItemID, vendorID, itemName, Double.parseDouble(itemPrice), itemDesc);

        List<String> items = new ArrayList<>();
        items.add(newItem);

        // Read all existing items
        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                items.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading items file: " + e.getMessage());
        }

        // Sort the items by Item ID
        items.sort((item1, item2) -> {
            String id1 = item1.split(",")[0].trim();
            String id2 = item2.split(",")[0].trim();
            return id1.compareTo(id2);
        });

        // Write the sorted items back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(itemsFilePath))) {
            for (String item : items) {
                bw.write(item);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing items file: " + e.getMessage());
        }
    }

    public void updateItem(String vendorID, String itemID, String newItemName, String newItemPrice, String newItemDesc){
        List<String> fileContents = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].trim().equals(itemID) && parts[1].trim().equals(vendorID)) {
                    parts[2] = newItemName;
                    parts[3] = newItemPrice;
                    parts[4] = newItemDesc;
                    line = String.join(",", parts);
                    updated = true;
                }
                fileContents.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating item: " + e.getMessage());
        }

        if (updated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(itemsFilePath))) {
                for (String line : fileContents) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error saving updated item data: " + e.getMessage());
            }
        }
    }

    public void deleteItem(String vendorID, String itemID){
        List<String> fileContents = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(itemID)) {
                    fileContents.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }

        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(itemsFilePath))) {
                for (String line : fileContents) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error saving updated user data: " + e.getMessage());
            }
        }
    }

    public int acceptOrder(){
        return 1;
    }

    public int cancelOrder(){
        return 1;
    }

    public int updateOrderStatus(){
        return 1;
    }

    public String[] viewItem(String vendorID, String itemID) {
        String[] itemData = new String[5];

        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            br.readLine();  // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts[0].trim().startsWith(vendorID) && parts[0].trim().equals(itemID) && parts[1].trim().equals(vendorID)) {//found
                    itemData[0] = parts[0].trim();//item ID
                    itemData[1] = parts[1].trim();//vendor ID
                    itemData[2] = parts[2].trim();//name
                    itemData[3] = parts[3].trim();//price
                    itemData[4] = parts[4].trim();//description
                    return itemData;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;//not found
    }

    static class CustomTableModel extends AbstractTableModel {
        private List<String[]> data;
        private String[] columnNames;

        public CustomTableModel(List<String[]> data, String[] columnNames) {
            this.data = data;
            this.columnNames = columnNames;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }

    public TableModel loadData(String vendorID) {
        List<String[]> data = readDataFromFile(vendorID);
        String header = "Order ID, Vendor ID, itemID(s), Total Amount (RM), Status, Option, Timestamp";
        String[] columnNames = header.split(",");
        for(int i = 0; i < columnNames.length; i++){
            columnNames[i] = columnNames[i].trim();
        }

        return new CustomTableModel(data, columnNames);
    }

    private List<String[]> readDataFromFile(String vendorID) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            br.readLine();// skip header
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");

                if(row[1].trim().equals(vendorID)){
                    data.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
