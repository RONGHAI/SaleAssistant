/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.controller;

import me.ronghai.sa.JSONObject;
import me.ronghai.sa.core.context.SaleAssistanceApplicationContext;
import me.ronghai.sa.model.Client;
import me.ronghai.sa.view.delegate.JTableBindingDelegate;

/**
 *
 * @author L5M
 */
public class SaleAssistantController implements AbstractController , JTableBindingDelegate<Client>{
    
    public JSONObject initView(){
        JSONObject json = new JSONObject(); 
        json.put("view", SaleAssistanceApplicationContext.getBean("SaleAssistanceFrame")  );
        return json;
        
    }
    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*
    @Override
    public JTableBinding getJTableBinding(AutoBinding.UpdateStrategy strategy, List sourceList, JTable targetJTable, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */
    
}
