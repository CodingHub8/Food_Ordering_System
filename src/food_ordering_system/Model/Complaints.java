package food_ordering_system.Model;

public class Complaints {
    private String complaintID;
    private String custID;
    private String title;
    private String message;

    public Complaints(String custID, String title, String message) {
        this.complaintID = generateComplaintID();
        this.custID = custID;
        this.title = title;
        this.message = message;
    }

    private String generateComplaintID() {
        return "";
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
