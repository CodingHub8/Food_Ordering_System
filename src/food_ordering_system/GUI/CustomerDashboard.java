package food_ordering_system.GUI;

import food_ordering_system.Controller.CustomerController;
import food_ordering_system.Utilities.*;

import java.awt.*;

import javax.swing.*;

public class CustomerDashboard extends javax.swing.JFrame {
    private CustomerController customerController = new CustomerController();

    public CustomerDashboard(String custID) {
        setTitle("Customer Interface");
        new Notifications().printReceipt(custID);
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        new LoginRedirect().logout(this);
        setVisible(true);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        frmViewMenu = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblViewMenu = new javax.swing.JTable();
        frmReadReviews = new javax.swing.JFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCustomerReviews = new javax.swing.JTextArea();
        frmOrderItem = new javax.swing.JFrame();
        frmTransactionHistory = new javax.swing.JFrame();
        frmProvideReview = new javax.swing.JFrame();
        frmProvideComplaint = new javax.swing.JFrame();
        pnlCustomer = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        btnViewMenu = new javax.swing.JButton();
        btnReadReviews = new javax.swing.JButton();
        btnManageOrders = new javax.swing.JButton();
        btnTransactionHistory = new javax.swing.JButton();
        btnReviews = new javax.swing.JButton();
        btnComplaint = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnLogout = new javax.swing.JButton();

        tblViewMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Vendor ID", "Name", "Price", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblViewMenu);

        frmViewMenu.getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        txtCustomerReviews.setEditable(false);
        txtCustomerReviews.setColumns(20);
        txtCustomerReviews.setRows(5);
        jScrollPane2.setViewportView(txtCustomerReviews);

        frmReadReviews.getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new ColorPalette().primaryColor);
        setPreferredSize(new java.awt.Dimension(500, 170));

        pnlCustomer.setBackground(new ColorPalette().primaryColor);
        java.awt.GridBagLayout pnlCustomerLayout = new java.awt.GridBagLayout();
        pnlCustomerLayout.columnWidths = new int[] {0, 5, 0, 5, 0};
        pnlCustomerLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        pnlCustomer.setLayout(pnlCustomerLayout);

        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setText("Welcome <customer name>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        pnlCustomer.add(lblWelcome, gridBagConstraints);

        btnViewMenu.setBackground(new ColorPalette().secondaryColor);
        btnViewMenu.setText("View Menu");
        btnViewMenu.setPreferredSize(new java.awt.Dimension(140, 30));
        btnViewMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewMenuActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        pnlCustomer.add(btnViewMenu, gridBagConstraints);

        btnReadReviews.setBackground(new ColorPalette().secondaryColor);
        btnReadReviews.setText("Customer Reviews");
        btnReadReviews.setPreferredSize(new java.awt.Dimension(140, 30));
        btnReadReviews.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadReviewsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        pnlCustomer.add(btnReadReviews, gridBagConstraints);

        btnManageOrders.setBackground(new ColorPalette().secondaryColor);
        btnManageOrders.setText("Orders");
        btnManageOrders.setPreferredSize(new java.awt.Dimension(140, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        pnlCustomer.add(btnManageOrders, gridBagConstraints);

        btnTransactionHistory.setBackground(new ColorPalette().secondaryColor);
        btnTransactionHistory.setText("Transaction History");
        btnTransactionHistory.setPreferredSize(new java.awt.Dimension(140, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        pnlCustomer.add(btnTransactionHistory, gridBagConstraints);

        btnReviews.setBackground(new ColorPalette().secondaryColor);
        btnReviews.setText("Provide Reviews");
        btnReviews.setPreferredSize(new java.awt.Dimension(140, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        pnlCustomer.add(btnReviews, gridBagConstraints);

        btnComplaint.setBackground(new ColorPalette().secondaryColor);
        btnComplaint.setText("File a complaint");
        btnComplaint.setPreferredSize(new java.awt.Dimension(140, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        pnlCustomer.add(btnComplaint, gridBagConstraints);

        jSeparator1.setPreferredSize(new java.awt.Dimension(0, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        pnlCustomer.add(jSeparator1, gridBagConstraints);

        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        pnlCustomer.add(btnLogout, gridBagConstraints);

        getContentPane().add(pnlCustomer, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginScreen().setVisible(true); // Open login page
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnViewMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewMenuActionPerformed
        tblViewMenu.setModel(customerController.loadMenuData());

        frmViewMenu.setVisible(true);
        frmViewMenu.setTitle("Menu List");
        frmViewMenu.pack();
        frmViewMenu.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnViewMenuActionPerformed

    private void btnReadReviewsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadReviewsActionPerformed
        txtCustomerReviews.setText(customerController.getReviews());
        
        frmReadReviews.setVisible(true);
        frmReadReviews.setTitle("Customer Reviews");
        frmReadReviews.setSize(new Dimension(400, 200));
        frmReadReviews.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnReadReviewsActionPerformed

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
            java.util.logging.Logger.getLogger(CustomerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerDashboard("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComplaint;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnManageOrders;
    private javax.swing.JButton btnReadReviews;
    private javax.swing.JButton btnReviews;
    private javax.swing.JButton btnTransactionHistory;
    private javax.swing.JButton btnViewMenu;
    private javax.swing.JFrame frmOrderItem;
    private javax.swing.JFrame frmProvideComplaint;
    private javax.swing.JFrame frmProvideReview;
    private javax.swing.JFrame frmReadReviews;
    private javax.swing.JFrame frmTransactionHistory;
    private javax.swing.JFrame frmViewMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel pnlCustomer;
    private javax.swing.JTable tblViewMenu;
    private javax.swing.JTextArea txtCustomerReviews;
    // End of variables declaration//GEN-END:variables
}
