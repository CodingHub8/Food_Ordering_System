package food_ordering_system.Model;

public class Administrator extends User{
    public Administrator(String name, String password) {
        super(name, password);
    }

    @Override
    public String generateID() {
        return "";
    }

    public int registerUser(){
        return 1;
    }

    public int updateUser(){
        return 1;
    }

    public int deleteUser(){
        return 1;
    }

    public int topupCredit(){
        return 1;
    }

    public int generateReceipt(){
        return 1;
    }

    public int sendReceipt(){
        return 1;
    }
}
