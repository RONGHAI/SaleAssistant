/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.view.panel;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RONGHAI
 */
public class PanelFactory {
    private static final Logger logger =   Logger.getLogger(PanelFactory.class.getName()) ;
    private final static Map<String, AbstractPanel> _panelCache = new HashMap<>(16);
  
    public static AbstractPanel getPanel(ActionEvent evt, String actionCommand, String text) {
        String key = actionCommand+"_"+text;
        if(_panelCache.containsKey(key)){
            return _panelCache.get(key);
        }
        AbstractPanel panel = null; 
        try {
            ///Constructor c = Class.forName("Foo").getConstructor(String.class, Integer.TYPE);
            //Foo foo = (Foo) c.newInstance("example", 34);
            panel =  (AbstractPanel) Class.forName(actionCommand).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        _panelCache.put(key, panel);
        return panel;
    }
}
