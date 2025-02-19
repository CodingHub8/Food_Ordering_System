package food_ordering_system.Models;

public class MenuItem {
    private String ID;
    private String vendorID;
    private String name;
    private double price;
    private String description;

    public MenuItem(String ID, String vendorID, String name, double price, String description) {
        this.ID = ID;
        this.vendorID = vendorID;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getID() {
        return ID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
