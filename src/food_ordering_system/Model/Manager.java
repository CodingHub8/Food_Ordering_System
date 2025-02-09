package food_ordering_system.Model;

public class Manager extends User{
    public Manager(String name, String password) {
        super(name, password);
    }

    @Override
    public String generateID() {
        return "";
    }

    public void monitorVendorPerformance(){

    }

    public void monitoryRunnerPerformance(){

    }

    public int resolveComplaint(){
        return 1;
    }

    public int removeVendorItem(){
        return 1;
    }
}
