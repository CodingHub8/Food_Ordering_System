package food_ordering_system.Models;

public abstract class User {
    private String ID;
    private String name;
    private String password;
    private double value;

    public User(String ID, String name, String password, double value) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.value = value;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getValue() {
        // Extra value for Customer, Vendor and Runner
        return value;
    }

    public abstract String getFilePath();
}
