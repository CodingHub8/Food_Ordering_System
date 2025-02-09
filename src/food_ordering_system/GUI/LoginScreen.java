package food_ordering_system.GUI;

import food_ordering_system.Controller.LoginController;
import food_ordering_system.Utilities.LoadData;

import javax.swing.*;
import java.awt.Color;
import java.util.Arrays;

public class LoginScreen extends javax.swing.JFrame {
    
    public LoginScreen() {
        new LoadData().generateData();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Food Ordering System");
        initComponents();
        setPasswordPlaceholder();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLogin = new javax.swing.JPanel();
        txtUserID = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlLogin.setLayout(new java.awt.GridLayout(3, 0));

        txtUserID.setText("Enter your user ID");
        txtUserID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserIDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUserIDFocusLost(evt);
            }
        });
        pnlLogin.add(txtUserID);

        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPasswordFocusLost(evt);
            }
        });
        pnlLogin.add(txtPassword);

        btnLogin.setBackground(new java.awt.Color(51, 204, 0));
        btnLogin.setForeground(new java.awt.Color(0, 0, 0));
        btnLogin.setText("Login");
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.setPreferredSize(new java.awt.Dimension(50, 23));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        pnlLogin.add(btnLogin);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String userID = txtUserID.getText();
        String password = new String(txtPassword.getPassword());

        boolean authenticated = new LoginController().authenticate(userID, password);

        if (authenticated) {
            dispose();
            if (userID.startsWith("AD")) { //admin
                new AdministratorDashboard(userID);
            } else if (userID.startsWith("C")) {//customer
                new CustomerDashboard();
            } else if (userID.startsWith("R")) {//runner
                new DeliveryRunnerDashboard();
            } else if (userID.startsWith("V")) {//vendor
                new VendorDashboard();
            } else if (userID.startsWith("MAN")) {//manager
                new ManagerDashboard();
            }
        } else if (userID.equalsIgnoreCase("Enter your user ID") || password.equalsIgnoreCase("Enter your password")) {
            JOptionPane.showMessageDialog(null, "Please fill in all credentials", "Error", JOptionPane.WARNING_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null, "Invalid user ID or password", "Invalid", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtUserIDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserIDFocusGained
        if(txtUserID.getText().equals("Enter your user ID")){
            txtUserID.setForeground(Color.black);
            txtUserID.setText("");
        }
    }//GEN-LAST:event_txtUserIDFocusGained

    private void txtUserIDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserIDFocusLost
        if(txtUserID.getText().isEmpty()){
            txtUserID.setForeground(new Color(153, 153, 153));
            txtUserID.setText("Enter your user ID");
        }
    }//GEN-LAST:event_txtUserIDFocusLost

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        txtPassword.setEchoChar('*');
        String password = String.valueOf(txtPassword.getPassword());

        if(password.equalsIgnoreCase("Enter your password")){
            txtPassword.setText("");
            txtPassword.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPasswordFocusGained

    private void txtPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusLost
        setPasswordPlaceholder();
    }//GEN-LAST:event_txtPasswordFocusLost
    
    private void setPasswordPlaceholder(){
        String password = String.valueOf(txtPassword.getPassword());
    
        if(password.equalsIgnoreCase("Enter your password") || password.equalsIgnoreCase("") ){
            txtPassword.setText("Enter your password");
            txtPassword.setEchoChar((char)0);
            txtPassword.setForeground(new Color(153, 153, 153));
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserID;
    // End of variables declaration//GEN-END:variables
}
