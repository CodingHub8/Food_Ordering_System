package food_ordering_system.Model;

import java.util.List;

public class Customer extends User{

    private double credit;
    private List<Order> orderHistory;
    private List<Transaction> transactionHistory;

    public Customer(String name, String password) {
        super(name, password);
    }

    @Override
    public String generateID() {
        return "";
    }

    public void viewMenu(){

    }

    public int placeOrder(Order order){
        return 1;
    }

    public int cancelOrder(Order order){
        return 1;
    }

    public void checkOrderStatus(Order order){

    }

    public void viewOrderHistory(){

    }

    public void viewTransactionHistory(){

    }

    public int provideReview(){
        return 1;
    }

    public int reorder(){
        return 1;
    }
}
