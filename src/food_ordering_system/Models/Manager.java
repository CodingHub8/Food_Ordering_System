package food_ordering_system.Models;

public class Manager extends User {
    private String ID;
    private String name;
    private String password;

    public Manager (String ID, String name, String password) {
        super(ID, name, password, 0.0);
        this.ID = ID;
        this.name = name;
        this.password = password;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getFilePath() {
        return "src/food_ordering_system/Data/managers.txt";
    }
}
