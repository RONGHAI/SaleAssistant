/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.controller;

import me.ronghai.sa.bean.DataWrapperBean;
import static me.ronghai.sa.core.context.SaleAssistanceApplicationContext.getBean;
import me.ronghai.sa.view.frame.SaleAssistantFrame;

/**
 *
 * @author RONGHAI
 */
public class SaleAssistantController implements AbstractController {
    
    public DataWrapperBean initView(DataWrapperBean param){
        DataWrapperBean wrapper = new DataWrapperBean(); 
        wrapper.put("view",  (SaleAssistantFrame)getBean("saleAssistantFrame"));
        return wrapper;
    }
    
    public DataWrapperBean exit(DataWrapperBean param){
        System.exit(0);
        return null;
    }
    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 

}
