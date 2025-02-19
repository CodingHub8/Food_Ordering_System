package food_ordering_system.Models;

public class Runner extends User {
    private String ID;
    private String name;
    private String password;
    private double rating;

    public Runner(String ID, String name, String password, double rating) {
        super(ID, name, password);
        this.rating = rating;
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
        return "src/food_ordering_system/Data/delivery_runners.txt";
    }

    public double getRating() {
        return rating;
    }
}
