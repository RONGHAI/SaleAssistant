/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.core.dispatcher;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import me.ronghai.sa.JSONObject;
import me.ronghai.sa.controller.AbstractController;
import me.ronghai.sa.core.callback.DispatcherCallBack;

/**
 *
 * @author L5M
 */
public class SaleAssistansDispatcher implements Serializable { 
    private static final long serialVersionUID = 1L;
    /**
     *
     * @param controller
     * @param action
     * @param frame
     * @param panel
     * @param json
     * @param callback
     */
    public static void doDispatch(AbstractController controller, String action, JFrame frame, JPanel panel, JSONObject json, DispatcherCallBack callback  ) {
        if(action == null ){ 
            if(callback != null){
                 callback.callback(action, null);          
            }
            return ;
        }
        AbstractController instance = controller;
         
        
        try {
             Method method = controller.getClass().getMethod("action",  JSONObject.class);
             json = (JSONObject) method.invoke(instance, json);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(SaleAssistansDispatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         if(callback != null){
             callback.callback(action, json);
         }
         
    }
}
