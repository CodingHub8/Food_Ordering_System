package food_ordering_system.Model;

import java.util.ArrayList;
import java.util.List;

public class DeliveryRunner extends User{

    private List<Task> taskList;//active task
    private List<Task> taskHistory;//get from tasks.txt where runnerID matches
    private List<Double> ratings;
    private double rating;

    public DeliveryRunner(String name, String password) {
        super(name, password);
    }

    @Override
    public String generateID() {
        return "";
    }

    public void viewTask(){

    }

    public int acceptTask(){
        return 1;
    }

    public int declineTask(){
        return 1;
    }

    public int updateTaskStatus(){
        return 1;
    }

    public void viewTaskHistory(){

    }

    public void viewRevenueDashboard(){

    }

    public void addRating(){
        //read from reveiws.txt where targedID matches vendorID
        ratings = new ArrayList<>();
        // will loop while reading the txt file linearly
        ratings.add(0.0);

        // Get average rating to display for customer
        double sum = 0;

        if (ratings.isEmpty()) {//to avoid accidentally divide by 0
            rating = 0; // Or some default value
        } else {
            for (double rate : ratings) {
                sum += rate;
            }
            rating = sum / ratings.size();
        }
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
