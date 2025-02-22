package food_ordering_system.GUI;

import food_ordering_system.Controller.VendorController;
import food_ordering_system.Utilities.*;
import food_ordering_system.Models.MenuItem;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VendorDashboard extends javax.swing.JFrame {
    private String timestamp = "Days";//by default
    private GraphPanel graphPanel;
    private final VendorController vendorController = new VendorController();
    private List<Double> revenues;
    private String[] vendorDetail;
    private String vendorID;

    public VendorDashboard(String vendorID) {
        this.vendorID = vendorID;
        setTitle("Vendor Dashboard");
        vendorDetail = vendorController.getVendorDetails(vendorID);

        revenues = vendorController.getRevenues(vendorID, timestamp);
        graphPanel = new GraphPanel(revenues, "Revenue (RM)", timestamp);

        initComponents();
        lblVendorID.setText("ID: " + vendorID);
        lblVendorName.setText("Name: " + vendorDetail[1]);
        lblVendorRating.setText("Rating: " + vendorDetail[3]);
        txtReviews.setText("");
        txtReviews.append(vendorController.getReviews(vendorID));

        pnlGraph.add(graphPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        new LoginRedirect().logout(this);
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
        java.awt.GridBagConstraints gridBagConstraints;

        frmItemManagement = new javax.swing.JFrame();
        cboSelectAction = new javax.swing.JComboBox<>();
        pnlSearchBar = new javax.swing.JPanel();
        txtSearchItemKey = new javax.swing.JTextField();
        btnSearchItem = new javax.swing.JButton();
        txtItemID = new javax.swing.JTextField();
        txtItemName = new javax.swing.JTextField();
        txtItemPrice = new javax.swing.JTextField();
        txtItemDesc = new javax.swing.JTextField();
        btnConfirm = new javax.swing.JButton();
        frmOrderManagement = new javax.swing.JFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        frmOrderHistory = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrderHistory = new javax.swing.JTable();
        pnlDashboard = new javax.swing.JPanel();
        pnlGraph = new javax.swing.JPanel();
        pnlButton = new javax.swing.JPanel();
        btnDays = new javax.swing.JButton();
        btnMonths = new javax.swing.JButton();
        btnQuarters = new javax.swing.JButton();
        pnlNavigator = new javax.swing.JPanel();
        lblVendorID = new javax.swing.JLabel();
        lblVendorName = new javax.swing.JLabel();
        lblVendorRating = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnManageItems = new javax.swing.JButton();
        btnManageOrders = new javax.swing.JButton();
        btnOrderHistory = new javax.swing.JButton();
        scrlPnlReviews = new javax.swing.JScrollPane();
        txtReviews = new javax.swing.JTextArea();

        frmItemManagement.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                frmItemManagementWindowOpened(evt);
            }
        });
        frmItemManagement.getContentPane().setLayout(new javax.swing.BoxLayout(frmItemManagement.getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        cboSelectAction.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select action", "Add Item", "View Item", "Update Item", "Delete Item" }));
        cboSelectAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSelectActionActionPerformed(evt);
            }
        });
        frmItemManagement.getContentPane().add(cboSelectAction);

        pnlSearchBar.setLayout(new javax.swing.BoxLayout(pnlSearchBar, javax.swing.BoxLayout.LINE_AXIS));

        txtSearchItemKey.setText("Search Item ID or Item Name");
        txtSearchItemKey.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchItemKeyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchItemKeyFocusLost(evt);
            }
        });
        pnlSearchBar.add(txtSearchItemKey);

        btnSearchItem.setIcon(new javax.swing.JLabel() {
            public javax.swing.Icon getIcon() {
                try {
                    return new javax.swing.ImageIcon(
                        new java.net.URL("https://img.icons8.com/cotton/16/search--v2.png")
                    );
                } catch (java.net.MalformedURLException e) {
                }
                return null;
            }
        }.getIcon());
        btnSearchItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchItemActionPerformed(evt);
            }
        });
        pnlSearchBar.add(btnSearchItem);

        frmItemManagement.getContentPane().add(pnlSearchBar);

        txtItemID.setEditable(false);
        txtItemID.setText("ID");
        txtItemID.setFocusable(false);
        frmItemManagement.getContentPane().add(txtItemID);

        txtItemName.setText("Name");
        txtItemName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtItemNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtItemNameFocusLost(evt);
            }
        });
        frmItemManagement.getContentPane().add(txtItemName);

        txtItemPrice.setText("Price (RM)");
        txtItemPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtItemPriceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtItemPriceFocusLost(evt);
            }
        });
        frmItemManagement.getContentPane().add(txtItemPrice);

        txtItemDesc.setText("Description");
        txtItemDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtItemDescFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtItemDescFocusLost(evt);
            }
        });
        frmItemManagement.getContentPane().add(txtItemDesc);

        btnConfirm.setBackground(new java.awt.Color(0, 204, 0));
        btnConfirm.setForeground(new java.awt.Color(0, 0, 0));
        btnConfirm.setText("Confirm");
        btnConfirm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });
        frmItemManagement.getContentPane().add(btnConfirm);

        frmOrderManagement.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                frmOrderManagementWindowOpened(evt);
            }
        });

        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Customer ID", "Vendor ID", "Item ID(s)", "Amount (RM)", "Status", "Option", "Timestamp"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrders.setShowGrid(true);
        tblOrders.getTableHeader().setReorderingAllowed(false);
        tblOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblOrders);

        frmOrderManagement.getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tblOrderHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Customer ID", "Vendor ID", "Item ID(s)", "Amount (RM)", "Status", "Option", "Timestamp"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrderHistory.setShowGrid(true);
        jScrollPane1.setViewportView(tblOrderHistory);

        frmOrderHistory.getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vendor");
        setBackground(Color.decode("#500073"));
        setMinimumSize(new java.awt.Dimension(1200, 250));
        setName("vendorFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1200, 400));
        getContentPane().setLayout(new java.awt.GridLayout(1, 3));

        pnlDashboard.setBackground(Color.decode("#500073"));
        pnlDashboard.setLayout(new java.awt.BorderLayout());

        pnlGraph.setBackground(Color.decode("#500073"));
        pnlGraph.setLayout(new javax.swing.BoxLayout(pnlGraph, javax.swing.BoxLayout.LINE_AXIS));
        pnlDashboard.add(pnlGraph, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(Color.decode("#500073"));

        btnDays.setText("Days");
        btnDays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaysActionPerformed(evt);
            }
        });
        pnlButton.add(btnDays);

        btnMonths.setText("Months");
        btnMonths.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonthsActionPerformed(evt);
            }
        });
        pnlButton.add(btnMonths);

        btnQuarters.setText("Quarters");
        btnQuarters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuartersActionPerformed(evt);
            }
        });
        pnlButton.add(btnQuarters);

        pnlDashboard.add(pnlButton, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(pnlDashboard);

        pnlNavigator.setBackground(Color.decode("#500073"));
        pnlNavigator.setForeground(new java.awt.Color(255, 255, 255));
        pnlNavigator.setNextFocusableComponent(btnManageItems);
        java.awt.GridBagLayout pnlNavigatorLayout = new java.awt.GridBagLayout();
        pnlNavigatorLayout.columnWidths = new int[] {0};
        pnlNavigatorLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        pnlNavigator.setLayout(pnlNavigatorLayout);

        lblVendorID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblVendorID.setForeground(new java.awt.Color(255, 255, 255));
        lblVendorID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVendorID.setText("ID: <Vendor ID>");
        lblVendorID.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        pnlNavigator.add(lblVendorID, gridBagConstraints);

        lblVendorName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblVendorName.setForeground(new java.awt.Color(255, 255, 255));
        lblVendorName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVendorName.setText("Name: <Vendor Name>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        pnlNavigator.add(lblVendorName, gridBagConstraints);

        lblVendorRating.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblVendorRating.setForeground(new java.awt.Color(255, 255, 255));
        lblVendorRating.setText("Overall Rating: <rating>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        pnlNavigator.add(lblVendorRating, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        pnlNavigator.add(jSeparator1, gridBagConstraints);

        btnManageItems.setBackground(Color.decode("#F14A00"));
        btnManageItems.setText("Manage Items");
        btnManageItems.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnManageItems.setMinimumSize(new java.awt.Dimension(140, 25));
        btnManageItems.setPreferredSize(new java.awt.Dimension(140, 50));
        btnManageItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageItemsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        pnlNavigator.add(btnManageItems, gridBagConstraints);

        btnManageOrders.setBackground(Color.decode("#F14A00"));
        btnManageOrders.setText("Manage Orders");
        btnManageOrders.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnManageOrders.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnManageOrders.setMinimumSize(new java.awt.Dimension(140, 25));
        btnManageOrders.setPreferredSize(new java.awt.Dimension(140, 50));
        btnManageOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageOrdersActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        pnlNavigator.add(btnManageOrders, gridBagConstraints);

        btnOrderHistory.setBackground(Color.decode("#F14A00"));
        btnOrderHistory.setText("Check Order History");
        btnOrderHistory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOrderHistory.setMinimumSize(new java.awt.Dimension(140, 25));
        btnOrderHistory.setPreferredSize(new java.awt.Dimension(140, 50));
        btnOrderHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderHistoryActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        pnlNavigator.add(btnOrderHistory, gridBagConstraints);

        getContentPane().add(pnlNavigator);

        scrlPnlReviews.setBackground(Color.decode("#500073"));
        scrlPnlReviews.setPreferredSize(new java.awt.Dimension(400, 200));

        txtReviews.setEditable(false);
        txtReviews.setColumns(20);
        txtReviews.setRows(5);
        txtReviews.setText("Customer reviews will appear here\ne.g.\nC001: Good Food (4.2 rating)\nC002: Yummers (5.0 rating)\nC003: Food too salty (2.3 rating)");
        txtReviews.setFocusable(false);
        scrlPnlReviews.setViewportView(txtReviews);

        getContentPane().add(scrlPnlReviews);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnManageItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageItemsActionPerformed
        frmItemManagement.setVisible(true);
        frmItemManagement.setTitle("Manage Items");

        initializeItemManagement();

        frmItemManagement.setSize(new Dimension(600, 220));
        frmItemManagement.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnManageItemsActionPerformed

    private void btnDaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaysActionPerformed
        timestamp = "Days";
        revenues = vendorController.getRevenues(vendorID, timestamp);
        graphPanel.setData(revenues, "Days");
        graphPanel.invalidate();
        graphPanel.repaint();
    }//GEN-LAST:event_btnDaysActionPerformed

    private void btnMonthsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonthsActionPerformed
        timestamp = "Months";
        revenues = vendorController.getRevenues(vendorID, timestamp);
        graphPanel.setData(revenues, "Months");
        graphPanel.invalidate();
        graphPanel.repaint();
    }//GEN-LAST:event_btnMonthsActionPerformed

    private void btnQuartersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuartersActionPerformed
        timestamp = "Quarters";
        revenues = vendorController.getRevenues(vendorID, timestamp);
        graphPanel.setData(revenues, "Quarters");
        graphPanel.invalidate();
        graphPanel.repaint();
    }//GEN-LAST:event_btnQuartersActionPerformed

    private void initializeItemManagement(){
        pnlSearchBar.setVisible(false);
        txtSearchItemKey.setText("Search Item ID");

        txtItemID.setVisible(false);
        txtItemName.setText("Name");
        txtItemName.setEditable(false);
        txtItemName.setVisible(false);
        txtItemName.setFocusable(false);
        txtItemPrice.setText("Price (RM)");
        txtItemPrice.setEditable(false);
        txtItemPrice.setVisible(false);
        txtItemPrice.setFocusable(false);
        txtItemDesc.setText("Description");
        txtItemDesc.setEditable(false);
        txtItemDesc.setVisible(false);
        txtItemDesc.setFocusable(false);

        btnConfirm.setVisible(false);
        btnConfirm.setAlignmentX(CENTER_ALIGNMENT);
        btnConfirm.setBackground(Color.GREEN);
        btnConfirm.setForeground(Color.BLACK);
    }

    private void btnOrderHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderHistoryActionPerformed
        tblOrderHistory.setModel(vendorController.loadData(vendorID, "Complete"));

        frmOrderHistory.setVisible(true);
        frmOrderHistory.setTitle("Order History");
        frmOrderHistory.pack();
        frmOrderHistory.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnOrderHistoryActionPerformed

    private void cboSelectActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSelectActionActionPerformed
        String action = String.valueOf(cboSelectAction.getSelectedItem());
        initializeItemManagement();

        switch (action){
            case "Add Item" -> {
                txtItemID.setText("Next item ID: " + new IDUtility().generateItemID(vendorID, "src/food_ordering_system/Data/menu_items.txt"));
                
                txtItemID.setVisible(true);
                txtItemName.setVisible(true);
                txtItemName.setEditable(true);
                txtItemName.setFocusable(true);
                txtItemPrice.setVisible(true);
                txtItemPrice.setEditable(true);
                txtItemPrice.setFocusable(true);
                txtItemDesc.setEditable(true);
                txtItemDesc.setVisible(true);
                txtItemDesc.setFocusable(true);
                btnConfirm.setVisible(true);
            }

            case "View Item" -> {
                pnlSearchBar.setVisible(true);
                txtItemName.setVisible(true);
                txtItemPrice.setVisible(true);
                txtItemDesc.setVisible(true);
            }

            case "Update Item" -> {
                pnlSearchBar.setVisible(true);
                txtItemName.setVisible(true);
                txtItemName.setEditable(true);
                txtItemName.setFocusable(true);
                txtItemPrice.setVisible(true);
                txtItemPrice.setEditable(true);
                txtItemPrice.setFocusable(true);
                txtItemDesc.setEditable(true);
                txtItemDesc.setVisible(true);
                txtItemDesc.setFocusable(true);
                btnConfirm.setVisible(true);
            }

            case "Delete Item" -> {
                pnlSearchBar.setVisible(true);
                txtItemName.setVisible(true);
                txtItemName.setFocusable(false);
                txtItemName.setEditable(false);
                txtItemPrice.setVisible(true);
                txtItemPrice.setFocusable(false);
                txtItemPrice.setEditable(false);
                txtItemDesc.setVisible(true);
                txtItemDesc.setFocusable(false);
                txtItemDesc.setEditable(false);

                btnConfirm.setBackground(Color.RED);
                btnConfirm.setForeground(Color.WHITE);
                btnConfirm.setVisible(true);
            }
        }
    }//GEN-LAST:event_cboSelectActionActionPerformed

    private void txtSearchItemKeyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchItemKeyFocusGained
        if(txtSearchItemKey.getText().equals("Search Item ID")){
            txtSearchItemKey.setText("");
        }
    }//GEN-LAST:event_txtSearchItemKeyFocusGained

    private void txtSearchItemKeyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchItemKeyFocusLost
        if(txtSearchItemKey.getText().isEmpty()){
            txtSearchItemKey.setText("Search Item ID");
        }
    }//GEN-LAST:event_txtSearchItemKeyFocusLost

    private void btnSearchItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchItemActionPerformed
        MenuItem itemData = vendorController.viewItem(vendorID, txtSearchItemKey.getText());

        if(itemData != null){
            txtItemName.setText(itemData.getName());
            txtItemPrice.setText(Double.toString(itemData.getPrice()));
            txtItemDesc.setText(itemData.getDescription());
        } else if (txtSearchItemKey.getText().equals("Search Item ID")){
            JOptionPane.showMessageDialog(null, "Enter item ID to search");
        } else {
            JOptionPane.showMessageDialog(null, "Item not found");
            txtItemName.setText("Name");
            txtItemPrice.setText("Price (RM)");
            txtItemDesc.setText("Description");
        }
    }//GEN-LAST:event_btnSearchItemActionPerformed

    private void txtItemNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemNameFocusGained
        if(txtItemName.getText().equals("Name")){
            txtItemName.setText("");
        }
    }//GEN-LAST:event_txtItemNameFocusGained

    private void txtItemNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemNameFocusLost
        if(txtItemName.getText().isEmpty()){
            txtItemName.setText("Name");
        }
    }//GEN-LAST:event_txtItemNameFocusLost

    private void txtItemPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemPriceFocusGained
        if(txtItemPrice.getText().equals("Price (RM)")){
            txtItemPrice.setText("");
        }
    }//GEN-LAST:event_txtItemPriceFocusGained

    private void txtItemPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemPriceFocusLost
        if(txtItemPrice.getText().isEmpty()){
            txtItemPrice.setText("Price (RM)");
        }
    }//GEN-LAST:event_txtItemPriceFocusLost

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        String action = String.valueOf(cboSelectAction.getSelectedItem());

        switch (action){
            case "Add Item" -> {
                if(txtItemName.getText().equals("Name") && !txtItemPrice.getText().chars().allMatch(Character::isDigit) &&
                        txtItemDesc.getText().equals("Description")){
                    JOptionPane.showMessageDialog(null, "Enter item details to add");
                    return;
                }
                
                int confirm = JOptionPane.showConfirmDialog(null, "Add Item", "Confirm creation", JOptionPane.YES_NO_OPTION);

                if(confirm == JOptionPane.YES_OPTION){
                    vendorController.createItem(vendorID, txtItemName.getText(), txtItemPrice.getText(), txtItemDesc.getText());
                    JOptionPane.showMessageDialog(null, "New item added");
                }
            }

            case "Update Item" -> {
                if(txtSearchItemKey.getText().equals("Search Item ID") && txtItemName.getText().equals("Name") &&
                        !txtItemPrice.getText().chars().allMatch(Character::isDigit) && txtItemDesc.getText().equals("Description")){{
                    JOptionPane.showMessageDialog(null, "Search item ID to update");
                    return;
                }}

                int confirm = JOptionPane.showConfirmDialog(null, "Update item info?", "Confirm update", JOptionPane.YES_NO_OPTION);

                if(confirm == JOptionPane.YES_OPTION){
                    vendorController.updateItem(vendorID, txtSearchItemKey.getText(), txtItemName.getText(), txtItemPrice.getText(), txtItemDesc.getText());
                    JOptionPane.showMessageDialog(null, "Item data updated");
                } else {
                    JOptionPane.showMessageDialog(null, "Enter item ID to search");
                }
            }

            case "Delete Item" -> {
                if(txtSearchItemKey.getText().equals("Search Item ID") && txtItemName.getText().equals("Name") &&
                        !txtItemPrice.getText().chars().allMatch(Character::isDigit) && txtItemDesc.getText().equals("Description")){{
                    JOptionPane.showMessageDialog(null, "Search item ID to delete");
                    return;
                }}

                int confirm = JOptionPane.showConfirmDialog(null, "Delete item?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if(confirm == JOptionPane.YES_OPTION){
                    vendorController.deleteItem(vendorID, txtSearchItemKey.getText());
                    JOptionPane.showMessageDialog(null, "Item data removed");
                    txtSearchItemKey.setText("Search Item ID");
                    txtItemName.setText("Name");
                    txtItemPrice.setText("Price (RM)");
                    txtItemDesc.setText("Description");
                }
            }
        }
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void txtItemDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemDescFocusGained
        if(txtItemDesc.getText().equals("Description")){
            txtItemDesc.setText("");
        }
    }//GEN-LAST:event_txtItemDescFocusGained

    private void txtItemDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemDescFocusLost
        if(txtItemDesc.getText().isEmpty()){
            txtItemDesc.setText("Description");
        }
    }//GEN-LAST:event_txtItemDescFocusLost

    private void frmItemManagementWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_frmItemManagementWindowOpened
        frmItemManagement.setVisible(true);
        frmItemManagement.setTitle("Manage Items");

        initializeItemManagement();

        frmItemManagement.setSize(new Dimension(600, 220));
        frmItemManagement.setLocationRelativeTo(null);
    }//GEN-LAST:event_frmItemManagementWindowOpened

    private void openManageOrderFrame(){
        tblOrders.setModel(vendorController.loadData(vendorID, "Pending", "Preparing"));

        frmOrderManagement.setVisible(true);
        frmOrderManagement.setTitle("Order Management");
        frmOrderManagement.pack();
        frmOrderManagement.setLocationRelativeTo(null);
    }
    
    private void btnManageOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageOrdersActionPerformed
        openManageOrderFrame();
    }//GEN-LAST:event_btnManageOrdersActionPerformed

    private void frmOrderManagementWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_frmOrderManagementWindowOpened
        openManageOrderFrame();
    }//GEN-LAST:event_frmOrderManagementWindowOpened

    private void tblOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdersMouseClicked
        // Add listener for cell edits
        int row = tblOrders.getSelectedRow();
        int col = tblOrders.getSelectedColumn();

        // Only handle clicks on the "Status" column
        if (col == 5) { // "Status" is column index 4
            String orderID = tblOrders.getValueAt(row, 0).toString(); // Get Order ID
            String custID = tblOrders.getValueAt(row, 1).toString();
            String currentStatus = tblOrders.getValueAt(row, col).toString().trim(); // Get current status

            if (currentStatus.equals("Pending")) {
                int choice = JOptionPane.showOptionDialog(
                        this,
                        "Update order status:",
                        "Order Status",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Reject", "Accept"},
                        "Reject"
                );

                if (choice == 0) { // Reject
                    vendorController.updateOrderStatusInFile(orderID, "Rejected");
                    tblOrders.setValueAt("Rejected", row, col); // Update table
                    new Notifications().orderNotification(orderID, custID, "Rejected");
                } else if (choice == 1) { // Accept
                    vendorController.updateOrderStatusInFile(orderID, "Preparing");
                    tblOrders.setValueAt("Preparing", row, col); // Update table
                    new Notifications().orderNotification(orderID, custID, "Preparing");
                }
            } else if (currentStatus.equals("Preparing")) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Mark order as complete?",
                        "Order Status",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    vendorController.updateOrderStatusInFile(orderID, "Complete");
                    tblOrders.setValueAt("Complete", row, col); // Update table
                    new Notifications().orderNotification(orderID, custID, "Complete");
                }
            }
            refreshOrdersTable();
        }
    }//GEN-LAST:event_tblOrdersMouseClicked

    private void refreshOrdersTable() {
        tblOrders.setModel(vendorController.loadData(vendorID, "Pending", "Preparing"));
        tblOrders.revalidate();
        tblOrders.repaint();
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
            java.util.logging.Logger.getLogger(VendorDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VendorDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VendorDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VendorDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VendorDashboard("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnDays;
    private javax.swing.JButton btnManageItems;
    private javax.swing.JButton btnManageOrders;
    private javax.swing.JButton btnMonths;
    private javax.swing.JButton btnOrderHistory;
    private javax.swing.JButton btnQuarters;
    private javax.swing.JButton btnSearchItem;
    private javax.swing.JComboBox<String> cboSelectAction;
    private javax.swing.JFrame frmItemManagement;
    private javax.swing.JFrame frmOrderHistory;
    private javax.swing.JFrame frmOrderManagement;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblVendorID;
    private javax.swing.JLabel lblVendorName;
    private javax.swing.JLabel lblVendorRating;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JPanel pnlGraph;
    private javax.swing.JPanel pnlNavigator;
    private javax.swing.JPanel pnlSearchBar;
    private javax.swing.JScrollPane scrlPnlReviews;
    private javax.swing.JTable tblOrderHistory;
    private javax.swing.JTable tblOrders;
    private javax.swing.JTextField txtItemDesc;
    private javax.swing.JTextField txtItemID;
    private javax.swing.JTextField txtItemName;
    private javax.swing.JTextField txtItemPrice;
    private javax.swing.JTextArea txtReviews;
    private javax.swing.JTextField txtSearchItemKey;
    // End of variables declaration//GEN-END:variables
}
