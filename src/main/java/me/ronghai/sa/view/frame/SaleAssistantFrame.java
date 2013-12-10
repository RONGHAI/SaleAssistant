/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.view.frame;

import java.awt.Container;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.ronghai.sa.util.SaleAssistantResource;
import me.ronghai.sa.view.panel.AbstractPanel;
import me.ronghai.sa.view.panel.PanelFactory;
import me.ronghai.sa.view.dialog.AboutDialog;
import static me.ronghai.sa.util.SaleAssistantResource.*;
/**
 *
 * @author L5M
 */
public class SaleAssistantFrame extends javax.swing.JFrame {
private static Logger logger =   Logger.getLogger(SaleAssistantFrame.class.getName()) ;
    /**
     * Creates new form SaleAssistant
     */
    public SaleAssistantFrame() {
        initComponents();
       //
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainMenu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();
        _s = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        orderMenu = new javax.swing.JMenu();
        addOrderMenuItem = new javax.swing.JMenuItem();
        purchaseOrderMenuItem = new javax.swing.JMenuItem();
        shippingMenuItem = new javax.swing.JMenuItem();
        basicInfoMenu = new javax.swing.JMenu();
        clientMenuItem = new javax.swing.JMenuItem();
        categoryMenuItem = new javax.swing.JMenuItem();
        productMenuItem = new javax.swing.JMenuItem();
        carrierMenuItem = new javax.swing.JMenuItem();
        merchantMenuItem = new javax.swing.JMenuItem();
        accountMenuItem = new javax.swing.JMenuItem();
        statisticsMenu = new javax.swing.JMenu();
        saleStatisticsMenuItem = new javax.swing.JMenuItem();
        clientRankingMenuItem = new javax.swing.JMenuItem();
        settingMenu = new javax.swing.JMenu();
        currencyMenuItem = new javax.swing.JMenuItem();
        updateExRateMenuItem = new javax.swing.JMenuItem();
        propertiesMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(400, 300));

        fileMenu.setText(SaleAssistantResource.getString("File"));
        fileMenu.setActionCommand("File");

        aboutMenuItem.setText(getString("About"));
        aboutMenuItem.setActionCommand("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(aboutMenuItem);
        fileMenu.add(_s);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setText(getString("Exit"));
        exitMenuItem.setActionCommand("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        mainMenu.add(fileMenu);

        orderMenu.setText(getString("Order"));
        orderMenu.setActionCommand("Order");

        addOrderMenuItem.setText(getString("Add Order"));
        addOrderMenuItem.setActionCommand("addOrder");
        addOrderMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        orderMenu.add(addOrderMenuItem);

        purchaseOrderMenuItem.setText(getString("Purchase Order"));
        purchaseOrderMenuItem.setActionCommand("purchaseOrder");
        purchaseOrderMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        orderMenu.add(purchaseOrderMenuItem);

        shippingMenuItem.setText(getString("Shipping"));
        shippingMenuItem.setToolTipText("");
        shippingMenuItem.setActionCommand("me.ronghai.sa.panel.ShippingManagementPanel");
        shippingMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        orderMenu.add(shippingMenuItem);

        mainMenu.add(orderMenu);

        basicInfoMenu.setText(getString("Basic Info"));
        basicInfoMenu.setActionCommand("BasicInfo");

        clientMenuItem.setText(getString("Client Management"));
        clientMenuItem.setActionCommand("me.ronghai.sa.panel.ClientManagementPanel");
        clientMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        basicInfoMenu.add(clientMenuItem);

        categoryMenuItem.setText(getString("Category Management"));
        categoryMenuItem.setActionCommand("me.ronghai.sa.panel.CategoryManagementPanel");
        categoryMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        basicInfoMenu.add(categoryMenuItem);

        productMenuItem.setText(getString("Product Management"));
        productMenuItem.setToolTipText("");
        productMenuItem.setActionCommand("me.ronghai.sa.panel.ProductManagementPanel");
        productMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        basicInfoMenu.add(productMenuItem);

        carrierMenuItem.setText(getString("Carrier Management"));
        carrierMenuItem.setActionCommand("me.ronghai.sa.panel.CarrierManagementPanel");
        carrierMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        basicInfoMenu.add(carrierMenuItem);

        merchantMenuItem.setText(getString("Merchant Management"));
        merchantMenuItem.setActionCommand("me.ronghai.sa.panel.MerchantManagementPanel");
        merchantMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        basicInfoMenu.add(merchantMenuItem);

        accountMenuItem.setText(getString("Account Management"));
        accountMenuItem.setActionCommand("me.ronghai.sa.panel.AccountManagementPanel");
        accountMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        basicInfoMenu.add(accountMenuItem);

        mainMenu.add(basicInfoMenu);

        statisticsMenu.setText(getString("Statistic"));

        saleStatisticsMenuItem.setText(getString("Sale Statistics"));
        saleStatisticsMenuItem.setActionCommand("me.ronghai.sa.panel.SaleStatisticsPanel");
        saleStatisticsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        statisticsMenu.add(saleStatisticsMenuItem);

        clientRankingMenuItem.setText(getString("Client Ranking"));
        clientRankingMenuItem.setToolTipText("");
        clientRankingMenuItem.setActionCommand("me.ronghai.sa.panel.ClientRankingPanel");
        clientRankingMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        statisticsMenu.add(clientRankingMenuItem);

        mainMenu.add(statisticsMenu);

        settingMenu.setText(getString("Setting"));

        currencyMenuItem.setText(getString("Currecy Management"));
        currencyMenuItem.setActionCommand("me.ronghai.sa.panel.CurrencyManagementPanel");
        currencyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        settingMenu.add(currencyMenuItem);

        updateExRateMenuItem.setText(getString("Update ExRate"));
        updateExRateMenuItem.setToolTipText("");
        updateExRateMenuItem.setActionCommand("UpdateExRate");
        updateExRateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateExRateMenuItemActionPerformed(evt);
            }
        });
        settingMenu.add(updateExRateMenuItem);

        propertiesMenuItem.setText(getString("Properties"));
        propertiesMenuItem.setActionCommand("me.ronghai.sa.panel.PropertiesManagementPanel");
        propertiesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        settingMenu.add(propertiesMenuItem);

        mainMenu.add(settingMenu);

        setJMenuBar(mainMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void menuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemActionPerformed
        // TODO add your handling code here:
        
        if(evt.getSource() instanceof javax.swing.JMenuItem){
            javax.swing.JMenuItem menuItem =  (javax.swing.JMenuItem)evt.getSource();
            logger.log(Level.INFO, evt.getActionCommand());
            logger.log(Level.INFO, menuItem.getText());
            AbstractPanel panel = PanelFactory.getPanel(evt, evt.getActionCommand(), menuItem.getText());
            
            if(panel != null){
                javax.swing.GroupLayout layout = _getContentPaneLayout();
                layout.setHorizontalGroup(
                     layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                     .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                         .addContainerGap(0,0)
                         .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                         .addGap(0, 0,  Short.MAX_VALUE))
                 );
                 layout.setVerticalGroup(
                     layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                     .addGroup(layout.createSequentialGroup()
                         .addGap(0, 0,  Short.MAX_VALUE)
                         .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                         .addContainerGap(0,0))
                 );
            } 
        }
       
                 
    }//GEN-LAST:event_menuItemActionPerformed

    private void updateExRateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateExRateMenuItemActionPerformed
        // TODO add your handling code here:
          System.out.println(evt.getActionCommand());
    }//GEN-LAST:event_updateExRateMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        new AboutDialog(this, false).setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed
    
     private javax.swing.GroupLayout _getContentPaneLayout() {
        Container container =  getContentPane();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(container);
        container.removeAll();
        container.setLayout(layout);
        return layout;
     }
     

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu.Separator _s;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem accountMenuItem;
    private javax.swing.JMenuItem addOrderMenuItem;
    private javax.swing.JMenu basicInfoMenu;
    private javax.swing.JMenuItem carrierMenuItem;
    private javax.swing.JMenuItem categoryMenuItem;
    private javax.swing.JMenuItem clientMenuItem;
    private javax.swing.JMenuItem clientRankingMenuItem;
    private javax.swing.JMenuItem currencyMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar mainMenu;
    private javax.swing.JMenuItem merchantMenuItem;
    private javax.swing.JMenu orderMenu;
    private javax.swing.JMenuItem productMenuItem;
    private javax.swing.JMenuItem propertiesMenuItem;
    private javax.swing.JMenuItem purchaseOrderMenuItem;
    private javax.swing.JMenuItem saleStatisticsMenuItem;
    private javax.swing.JMenu settingMenu;
    private javax.swing.JMenuItem shippingMenuItem;
    private javax.swing.JMenu statisticsMenu;
    private javax.swing.JMenuItem updateExRateMenuItem;
    // End of variables declaration//GEN-END:variables
}
