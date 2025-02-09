package food_ordering_system.Model;

public abstract class User {
    private String userID;
    private String name;
    private String password;

    public User(String name, String password) {
        this.userID = generateID();
        this.name = name;
        this.password = password;
    }

    public abstract String generateID();

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean login(){
        return false;
    }

    public void logout(){

    }
}
