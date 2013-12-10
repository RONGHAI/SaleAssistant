/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa;

import me.ronghai.sa.controller.SaleAssistantController;
import static me.ronghai.sa.core.dispatcher.SaleAssistansDispatcher.doDispatch;
import me.ronghai.sa.view.frame.SaleAssistantFrame;
import static me.ronghai.sa.core.context.SaleAssistanceApplicationContext.*;
import me.ronghai.sa.core.callback.DispatcherCallBack;
/**
 *
 * @author L5M
 */
public class SaleAssistant {
    public static void main(String args[]){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SaleAssistant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        doDispatch((SaleAssistantController)getBean("saleAssistantController") , "initView", null, null, null, new DispatcherCallBack(){
            @Override
            public void callback(String action, final JSONObject json) {
                if("initView".equals(action)){
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            ( (SaleAssistantFrame)json.get("view")).setVisible(true);
                        }
                    }); 
                }
             }
            
        });
    }
}
