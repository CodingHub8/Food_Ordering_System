package food_ordering_system.Models;

public class Customer extends User {
    private String ID;
    private String name;
    private String password;
    private double credit;

    public Customer(String ID, String name, String password, double credit) {
        super(ID, name, password);
        this.credit = credit;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getFilePath() {
        return "src/food_ordering_system/Data/customers.txt";
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
