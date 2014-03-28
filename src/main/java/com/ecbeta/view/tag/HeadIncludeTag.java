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
public class HeadIncludeTag  extends AbstractTag{

    public HeadIncludeTag() {
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
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /> \n");
        this.println(sb); 
        return EVAL_BODY_INCLUDE;
    } 
    
    @Override
    public void release() { 
        this.worker = null;
        super.release();
    }
 
}
