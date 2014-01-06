/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.view.panel;

/**
 *
 * @author ronghai
 */
public class PriceCalculatorPanel extends javax.swing.JPanel {

    /**
     * Creates new form PriceCalculatorPanel
     */
    public PriceCalculatorPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelA = new javax.swing.JPanel();
        itemPrice = new javax.swing.JTextField();
        itemPriceLabel = new javax.swing.JLabel();
        itemCount = new javax.swing.JTextField();
        itemCountLabel = new javax.swing.JLabel();
        weight = new javax.swing.JTextField();
        weightLabel = new javax.swing.JLabel();
        taxCheckBox = new javax.swing.JCheckBox();
        weightUnitLabel = new javax.swing.JLabel();
        panelB = new javax.swing.JPanel();
        serverRate = new javax.swing.JTextField();
        serverRateLabel = new javax.swing.JLabel();
        exchangeRate = new javax.swing.JTextField();
        exchangeRateLabel = new javax.swing.JLabel();
        dutyCheckbox = new javax.swing.JCheckBox();
        panelD = new javax.swing.JPanel();
        calculateButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        itemPrice.setToolTipText("商品价格");
        itemPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPriceActionPerformed(evt);
            }
        });

        itemPriceLabel.setText("商品价格");

        itemCount.setToolTipText("商品价格");
        itemCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCountActionPerformed(evt);
            }
        });

        itemCountLabel.setText("商品数量");

        weight.setToolTipText("商品价格");
        weight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weightActionPerformed(evt);
            }
        });

        weightLabel.setText("重量");

        taxCheckBox.setText("州税");
        taxCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taxCheckBoxActionPerformed(evt);
            }
        });

        weightUnitLabel.setText("LB");

        javax.swing.GroupLayout panelALayout = new javax.swing.GroupLayout(panelA);
        panelA.setLayout(panelALayout);
        panelALayout.setHorizontalGroup(
            panelALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelALayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(itemPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(itemCountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemCount, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(taxCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(weightLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(weightUnitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );
        panelALayout.setVerticalGroup(
            panelALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelALayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(taxCheckBox)
                    .addComponent(weightLabel)
                    .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itemPriceLabel)
                    .addComponent(itemCountLabel)
                    .addComponent(itemCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weightUnitLabel))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        add(panelA);

        serverRate.setToolTipText("商品价格");
        serverRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverRateActionPerformed(evt);
            }
        });

        serverRateLabel.setText("代购费率");

        exchangeRate.setToolTipText("商品价格");
        exchangeRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exchangeRateActionPerformed(evt);
            }
        });

        exchangeRateLabel.setText("汇率");

        dutyCheckbox.setText("关税");
        dutyCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dutyCheckboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBLayout = new javax.swing.GroupLayout(panelB);
        panelB.setLayout(panelBLayout);
        panelBLayout.setHorizontalGroup(
            panelBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(dutyCheckbox)
                .addGap(224, 224, 224)
                .addComponent(serverRateLabel)
                .addGap(18, 18, 18)
                .addComponent(serverRate, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(exchangeRateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exchangeRate, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172))
        );
        panelBLayout.setVerticalGroup(
            panelBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(dutyCheckbox)
                    .addComponent(exchangeRateLabel)
                    .addComponent(exchangeRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serverRateLabel)
                    .addComponent(serverRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        add(panelB);

        panelD.setLayout(new javax.swing.BoxLayout(panelD, javax.swing.BoxLayout.LINE_AXIS));

        calculateButton.setText("计算");
        panelD.add(calculateButton);

        resetButton.setText("重置");
        panelD.add(resetButton);

        add(panelD);
    }// </editor-fold>//GEN-END:initComponents

    private void itemPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemPriceActionPerformed

    private void itemCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemCountActionPerformed

    private void weightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_weightActionPerformed

    private void taxCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taxCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_taxCheckBoxActionPerformed

    private void serverRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serverRateActionPerformed

    private void exchangeRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exchangeRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exchangeRateActionPerformed

    private void dutyCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dutyCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dutyCheckboxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateButton;
    private javax.swing.JCheckBox dutyCheckbox;
    private javax.swing.JTextField exchangeRate;
    private javax.swing.JLabel exchangeRateLabel;
    private javax.swing.JTextField itemCount;
    private javax.swing.JLabel itemCountLabel;
    private javax.swing.JTextField itemPrice;
    private javax.swing.JLabel itemPriceLabel;
    private javax.swing.JPanel panelA;
    private javax.swing.JPanel panelB;
    private javax.swing.JPanel panelD;
    private javax.swing.JButton resetButton;
    private javax.swing.JTextField serverRate;
    private javax.swing.JLabel serverRateLabel;
    private javax.swing.JCheckBox taxCheckBox;
    private javax.swing.JTextField weight;
    private javax.swing.JLabel weightLabel;
    private javax.swing.JLabel weightUnitLabel;
    // End of variables declaration//GEN-END:variables
}
