package food_ordering_system.Controller;

import java.util.ArrayList;
import java.util.List;

public class VendorController {
    public int createItem(){
        return 1;
    }

    public int updateItem(){
        return 1;
    }

    public int deleteItem(){
        return 1;
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

    public void viewOrderHistory(){

    }

    public void viewRevenueDashboard(){

    }

    public void addRating(){
        //read from reveiws.txt where targedID matches vendorID
        List<Double> ratings = new ArrayList<>();
        // will loop while reading the txt file linearly
        ratings.add(0.0);//TODO: Add real data

        // Get average rating to display for customer
        double sum = 0;
        double rating;

        if (ratings.isEmpty()) {//to avoid accidentally divide by 0
            rating = 0; // Or some default value
        } else {
            for (double rate : ratings) {
                sum += rate;
            }
            rating = sum / ratings.size();
        }
    }
}
