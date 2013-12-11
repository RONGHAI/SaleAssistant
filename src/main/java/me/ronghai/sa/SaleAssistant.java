/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import me.ronghai.sa.bean.DataWrapperBean;
import static me.ronghai.sa.core.dispatcher.SaleAssistansDispatcher.doDispatch;
import me.ronghai.sa.view.frame.SaleAssistantFrame;
import me.ronghai.sa.view.action.callback.DispatcherCallBack;
/**
 *
 * @author L5M
 */

public class SaleAssistant {
    private static String OS = System.getProperty("os.name").toLowerCase();
    public static void main(String args[]){        
        if ((OS.indexOf("mac") >= 0)) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                Logger.getLogger(SaleAssistant.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
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
        }
        
        


        doDispatch("saleAssistantController.initView", null, null, null, new DispatcherCallBack(){
            @Override
            public void callback(String action, final DataWrapperBean wrapper) {
                if("initView".equals(action)){
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            ( (SaleAssistantFrame)wrapper.get("view")).setVisible(true);
                        }
                    }); 
                }
             }
            
        });
    }
}
