package food_ordering_system.Models;

public class Review {
    private String ID;
    private String orderID;
    private String custID;
    private String targetID;
    private double rating;
    private String comments;

    public Review(String ID, String orderID, String custID, String targetID, double rating, String comments) {
        this.ID = ID;
        this.orderID = orderID;
        this.custID = custID;
        this.targetID = targetID;
        this.rating = rating;
        this.comments = comments;
    }

    public Review() {/*Default Constructor*/}

    public String getID() {
        return ID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustID() {
        return custID;
    }

    public String getTargetID() {
        return targetID;
    }

    public double getRating() {
        return rating;
    }

    public String getComments() {
        return comments;
    }

    public String getFilePath(){
        return "src/food_ordering_system/Data/reviews.txt";
    }
}
