package food_ordering_system.Model;

public class Review {
    private String reviewID;
    private String orderID;
    private String custID;
    private String targetID;//runner or vendor
    private double rating;// 0.0 to 5.0
    private String comment;

    public Review(String orderID, String custID, double rating, String targetID, String comment) {
        this.reviewID = generateReviewID();
        this.orderID = orderID;
        this.custID = custID;
        this.rating = rating;
        this.targetID = targetID;
        this.comment = comment;
    }

    private String generateReviewID() {
        return "";
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getTargetID() {
        return targetID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
