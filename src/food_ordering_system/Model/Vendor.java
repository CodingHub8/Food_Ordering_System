package food_ordering_system.Model;

import java.util.ArrayList;
import java.util.List;

public class Vendor extends User{
    private double rating;

    public Vendor(String name, String password) {
        super(name, password);
    }

    @Override
    public String generateID() {
        return "";
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
