package food_ordering_system.Controller;

import java.io.*;

public class LoginController {
    private final String userDataPath = "D:\\User\\Documents\\Programming\\Java\\Netbeans\\Netbeans Projects\\Food_Ordering_System\\src\\food_ordering_system\\Data\\";

    public boolean authenticate(String userID, String password) {
        if (userID == null || userID.isEmpty() || password == null || password.isEmpty()) {
            return false; // Reject empty credentials
        }

        String filePath = getFilePath(userID);

        if (filePath == null) {
            return false; // Invalid role
        }

        String userData = getUserData(filePath, userID, password); // Authenticate user

        return true; // Authentication successful
    }

    private String getFilePath(String rolePrefix) {
        if (rolePrefix.startsWith("AD")){
            return (userDataPath + "administrators.txt");
        } else if (rolePrefix.startsWith("C")){
            return (userDataPath + "customers.txt");
        } else if (rolePrefix.startsWith("R")){
            return (userDataPath + "delivery_runners.txt");
        } else if (rolePrefix.startsWith("V")){
            return (userDataPath + "vendors.txt");
        } else if (rolePrefix.startsWith("MAN")){
            return (userDataPath + "managers.txt");
        } else {
            return null;
        }
    }

    private String getUserData(String filePath, String userID, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(","); // Assuming CSV format

                if (userDetails.length < 3) {
                    continue; // Skip malformed lines
                }

                String storedUserID = userDetails[0].trim();
                String storedPassword = userDetails[2].trim();

                if (storedUserID.equals(userID) && storedPassword.equals(password)) {
                    return line; // Return full user data
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // User not found or error occurred
    }
}
