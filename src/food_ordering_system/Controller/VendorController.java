package food_ordering_system.Controller;

import food_ordering_system.Models.*;
import food_ordering_system.Utilities.CustomTableModel;
import food_ordering_system.Utilities.IDUtility;

import javax.swing.table.TableModel;
import java.io.*;
import java.text.*;
import java.util.*;

public class VendorController {
    private final String reviewsFilePath = "src/food_ordering_system/Data/reviews.txt";
    private final String vendorsFilePath = "src/food_ordering_system/Data/vendors.txt";
    private final String ordersFilePath = "src/food_ordering_system/Data/orders.txt";
    private final String itemsFilePath = "src/food_ordering_system/Data/menu_items.txt";
    private List<Order> orders = new ArrayList<>();
    private List<MenuItem> items = new ArrayList<>();

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
            throw new RuntimeException("Error reading ile: " + e.getMessage());
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
            throw new RuntimeException("Error reading file: " + e.getMessage());
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
                String orderTimestamp = parts[7].trim(); // Extract timestamp
                double orderAmount = Double.parseDouble(parts[4]); // Extract total amount

                // Parse date from timestamp
                String key = getDateFormat(timestamp, orderTimestamp);

                // Add revenue to the respective key
                if(parts[2].trim().equals(vendorID)){
                    revenueMap.put(key, revenueMap.getOrDefault(key, 0.0) + orderAmount);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading or parsing file: " + e.getMessage());
        }

        // Add revenues from map to list in the correct order
        return new ArrayList<>(revenueMap.values());
    }

    private static String getDateFormat(String timestamp, String orderTimestamp) throws ParseException {
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

    private String getItems(){
        String header = "";
        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            header = br.readLine();//skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                for(int i = 0; i < parts.length; i++){
                    parts[i] = parts[i].trim();
                }
                items.add(new MenuItem(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]));
            }
        } catch (IOException e) {
            System.out.println("Error reading items file: " + e.getMessage());
        }
        return header;
    }

    public void createItem(String vendorID, String itemName, String itemPrice, String itemDesc){
        if (vendorID == null) {
            System.out.println("Invalid file path or vendor ID.");
            return;
        }

        String newItemID = new IDUtility().generateItemID(vendorID, itemsFilePath); // Generate new Item ID

        // Read all existing items
        String header = getItems();

        //add the new item into the list
        items.add(new MenuItem(newItemID, vendorID, itemName, Double.parseDouble(itemPrice), itemDesc));

        // Sort the items by Item ID
        items.sort((item1, item2) -> {
            String id1 = item1.getID().split(",")[0].trim();
            String id2 = item2.getID().split(",")[0].trim();
            return id1.compareTo(id2);
        });

        // Write the sorted items back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(itemsFilePath))) {
            bw.write(header);//write header
            bw.newLine();
            for (MenuItem item : items) {
                bw.write(item.getID() + ", " + item.getVendorID() + ", " + item.getName() + ", " + item.getPrice() + ", " + item.getDescription());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing items file: " + e.getMessage());
        }
    }

    public void updateItem(String vendorID, String itemKey, String newItemName, String newItemPrice, String newItemDesc){
        boolean updated = false;
        String header = getItems();

        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if ((parts[0].trim().equals(itemKey) || parts[2].trim().equalsIgnoreCase(itemKey)) && parts[1].trim().equals(vendorID)) {
                    items.get(i - 1).setName(newItemName);
                    items.get(i - 1).setPrice(Double.parseDouble(newItemPrice));
                    items.get(i - 1).setDescription(newItemDesc);
                    updated = true;
                    break;
                }
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating item: " + e.getMessage());
        }

        if (updated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(itemsFilePath))) {
                bw.write(header);//header
                bw.newLine();
                for (MenuItem item : items) {
                    bw.write(item.getID() + ", " + item.getVendorID() + ", " + item.getName() + ", " + item.getPrice() + ", " + item.getDescription());
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error saving updated item data: " + e.getMessage());
            }
        }
    }

    public void deleteItem(String vendorID, String itemKey){
        List<String> fileContents = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.contains(itemKey) && !vendorID.equalsIgnoreCase(itemKey)) {
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
                throw new RuntimeException("Error saving updated data: " + e.getMessage());
            }
        }
    }

    public String[] viewItem(String vendorID, String itemKey) {
        String[] itemData = new String[5];

        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            br.readLine();  // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts[0].trim().startsWith(vendorID) && (parts[0].trim().equals(itemKey) || parts[2].trim().equalsIgnoreCase(itemKey))
                   && parts[1].trim().equals(vendorID)) {//found
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

    public TableModel loadData(String vendorID, String... status) {
        List<String[]> data = new ArrayList<>();

        for(Order order : readOrderData(vendorID, status)){
            String[] row = new String[8];
            row[0] = order.getOrderID();
            row[1] = order.getCustomerID();
            row[2] = order.getVendorID();
            row[3] = "[" + String.join(" ", order.getItemIDs()) + "]";
            row[4] = String.valueOf(order.getTotalAmount());
            row[5] = order.getStatus();
            row[6] = order.getOption();
            row[7] = order.getTimestamp();
            data.add(row);
        }

        String header = "Order ID, Customer ID, Vendor ID, itemID(s), Total Amount (RM), Status, Option, Timestamp";
        String[] columnNames = header.split(",");

        for(int i = 0; i < columnNames.length; i++){
            columnNames[i] = columnNames[i].trim();
        }

        return new CustomTableModel(data, columnNames);
    }

    private List<Order> readOrderData(String vendorID, String... status) {
        orders.clear();//to prevent duplicate entry

        try (BufferedReader br = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            br.readLine();// skip header
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                List<String> itemIDs = new IDUtility().parseItemIDs(row[3]);

                for(int i = 0; i < row.length; i++){
                    row[i] = row[i].trim();
                }

                if(row[2].equals(vendorID)) {
                    if(status.length > 1){
                        if(row[5].equals(status[0]) || row[5].equals(status[1])){
                            orders.add(new Order(row[0], row[1], row[2], itemIDs, Double.parseDouble(row[4]), row[5], row[6], row[7]));
                        }
                    } else {
                        if(row[5].equals(status[0])){
                            orders.add(new Order(row[0], row[1], row[2], itemIDs, Double.parseDouble(row[4]), row[5], row[6], row[7]));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void updateOrderStatusInFile(String orderID, String newStatus) {
        List<String> fileContents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                for(Order order : orders){
                    if(order.getOrderID().equals(orderID)){
                        order.setStatus(newStatus);//update the list
                        break;
                    }
                }

                if (parts[0].trim().equals(orderID)) {
                    parts[5] = newStatus; // Update status
                    line = String.join(",", parts);
                }
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ordersFilePath))) {
            for (String contentLine : fileContents) {
                bw.write(contentLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
