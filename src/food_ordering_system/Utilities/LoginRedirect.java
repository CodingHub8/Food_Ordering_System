package food_ordering_system.Utilities;

import food_ordering_system.GUI.LoginScreen;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginRedirect {
    public void logout(JFrame frame){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to log out?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    new LoginScreen().setVisible(true); // Open login page
                }
            }
        });

        frame.dispose();
    }
}
