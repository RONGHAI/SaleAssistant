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
import me.ronghai.sa.bean.DataWrapperBean;
import me.ronghai.sa.controller.AbstractController;
import me.ronghai.sa.core.context.SaleAssistanceApplicationContext;
import me.ronghai.sa.view.action.callback.DispatcherCallBack;

/**
 *
 * @author L5M
 */
public final class SaleAssistansDispatcher implements Serializable { 
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger =   Logger.getLogger(SaleAssistansDispatcher.class.getName()) ;

    
    public final static void doDispatch(String controller, JFrame frame, JPanel panel, DataWrapperBean wrapper, DispatcherCallBack callback) {
        int dotPosition = controller.lastIndexOf('.');
        String action = null;
        if(dotPosition > 0){
            action = controller.substring(dotPosition + 1);
            controller = controller.substring(0, dotPosition);
        }
        logger.log(Level.INFO, "{0}/{1}", new Object[]{controller, action});
        doDispatch(SaleAssistanceApplicationContext.getBean(controller) , action, frame, panel, wrapper, callback);
    }
    /**
     *
     * @param controller
     * @param action
     * @param frame
     * @param panel
     * @param wrapper
     * @param callback
     */
    public final static void doDispatch(Object controller, String action, JFrame frame, JPanel panel, DataWrapperBean wrapper, DispatcherCallBack callback  ) {
        if(action == null ){ 
            if(callback != null){
                 callback.callback(action, null);          
            }
            return ;
        }        
        try {
             Method method = controller.getClass().getMethod(action,  DataWrapperBean.class);
             wrapper = (DataWrapperBean) method.invoke(controller, wrapper);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(SaleAssistansDispatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         if(callback != null){
             callback.callback(action, wrapper);
         }
         
    }
}
