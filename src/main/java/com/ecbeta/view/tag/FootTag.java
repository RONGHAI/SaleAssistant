/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecbeta.view.tag;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class FootTag  extends AbstractTag{

    public FootTag() {
        super();
    }
 
 
 
    @Override
    public int doEndTag() throws JspException {
        int returnValue = super.doEndTag();
        
        return returnValue;
    }

    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();
        HttpServletRequest request = this.getRequest();
        
        StringBuilder sb = new StringBuilder();
     
        this.println(sb); 
        return EVAL_BODY_INCLUDE;
    } 
    
    @Override
    public void release() { 
        this.worker = null;
        super.release();
    }
 
}
