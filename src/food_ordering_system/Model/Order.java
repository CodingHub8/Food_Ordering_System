package food_ordering_system.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Order {
    private String orderID;
    private String custID;
    private String vendorID;
    private List<MenuItem> items;
    private double totalAmount;
    private String status;
    private String orderTime;
    private String deliveryOption;//Dine-in / Take away / Delivery

    public Order(String custID, String vendorID, List<MenuItem> items, String status, double totalAmount, String deliveryOption) {
        this.orderID = generateOrderID();
        this.custID = custID;
        this.vendorID = vendorID;
        this.items = items;
        this.status = status;
        this.totalAmount = totalAmount;
        this.orderTime = getCurrentTime();
        this.deliveryOption = deliveryOption;
    }

    private String generateOrderID() {
        return "";
    }

    private String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustID() {
        return custID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }
}
