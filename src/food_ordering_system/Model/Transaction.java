package food_ordering_system.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String transactionID;
    private String custID;
    private double amount;
    private String transactionTime;

    public Transaction(String custID, double amount) {
        this.transactionID = generateTransactionID();
        this.custID = custID;
        this.transactionTime = getCurrentTime();
        this.amount = amount;
    }

    private String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    private String generateTransactionID() {
        return "";
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionTime() {
        return transactionTime;
    }
}
