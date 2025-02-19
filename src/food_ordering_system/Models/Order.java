package food_ordering_system.Models;

import java.util.List;

public class Order {
    private String orderID;
    private String customerID;
    private String vendorID;
    private List<String> itemIDs;
    private double totalAmount;
    private String status;
    private String option;
    private String timestamp;

    public Order(String orderID, String customerID, String vendorID, List<String> itemIDs, double totalAmount, String status, String option, String timestamp) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.vendorID = vendorID;
        this.itemIDs = itemIDs;
        this.totalAmount = totalAmount;
        this.status = status;
        this.option = option;
        this.timestamp = timestamp;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public List<String> getItemIDs() {
        return itemIDs;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOption() {
        return option;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getFilePath() {
        return "src/food_ordering_system/Data/orders.txt";
    }
}
