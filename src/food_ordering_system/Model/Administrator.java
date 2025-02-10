package food_ordering_system.Model;

public class Administrator extends User{
    public Administrator(String name, String password) {
        super(name, password);
    }

    @Override
    public String generateID() {
        return "";
    }
}
