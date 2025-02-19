package food_ordering_system.Controller;

import food_ordering_system.Models.*;

import java.io.*;
import java.util.Arrays;

public class LoginController {
    private User user;

    public boolean authenticate(String userID, String password) {
        if (userID == null || userID.isEmpty() || password == null || password.isEmpty()) {
            return false; // Reject empty credentials
        }

        String filePath = getFilePath(userID);

        if (filePath == null) {
            return false; // Invalid role
        }

        User user = getUserData(filePath, userID, password); // Authenticate user

        return user != null; // Authentication successful
    }

    private String getFilePath(String userID) {
        if (userID.startsWith("AD")){
            user = new Admin(userID, "", "");
        } else if (userID.startsWith("C")){
            user = new Customer(userID, "", "", 0.0);
        } else if (userID.startsWith("R")){
            user = new Runner(userID, "", "", 0.0);
        } else if (userID.startsWith("V")){
            user = new Vendor(userID, "", "", 0.0);
        } else if (userID.startsWith("MAN")){
            user = new Manager(userID, "", "");
        } else {
            return null;
        }

        return user.getFilePath();
    }

    private User getUserData(String filePath, String userID, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();//skip header
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(","); // Assuming CSV format

                String storedUserID = userDetails[0].trim();
                String storeUserName = userDetails[1].trim();
                String storedPassword = userDetails[2].trim();
                double storedExtraValue = 0.0;
                if(userDetails.length > 3){
                    storedExtraValue = Double.parseDouble(userDetails[3].trim());
                }

                if (storedUserID.equals(userID) && storedPassword.equals(password)) {
                    if (userID.startsWith("AD")){
                        return new Admin(storedUserID, storeUserName, storedPassword);
                    } else if (userID.startsWith("C")){
                        return new Customer(storedUserID, storeUserName, storedPassword, storedExtraValue);
                    } else if (userID.startsWith("R")){
                        return new Runner(storedUserID, storeUserName, storedPassword, storedExtraValue);
                    } else if (userID.startsWith("V")){
                        return new Vendor(storedUserID, storeUserName, storedPassword, storedExtraValue);
                    } else if (userID.startsWith("MAN")){
                        return new Admin(storedUserID, storeUserName, storedPassword);
                    } else {
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // User not found or error occurred
    }
}
