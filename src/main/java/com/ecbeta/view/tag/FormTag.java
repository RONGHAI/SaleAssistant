/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecbeta.view.tag;

import static com.ecbeta.common.constants.Constants.*;
import com.ecbeta.common.core.AbstractWorker;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class FormTag  extends AbstractTag{

    public FormTag() {
        super();
    }
    
    
    private AbstractWorker worker;

    public AbstractWorker getWorker() {
        return worker;
    }

    public void setWorker(AbstractWorker worker) {
        this.worker = worker;
    }
 
 
    @Override
    public int doEndTag() throws JspException {
        int returnValue = super.doEndTag();
        this.println("</form>", "</div>"); 
        return returnValue;
    }

    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();
        HttpServletRequest request = this.getRequest();
        
        StringBuilder sb = new StringBuilder();
        sb.append("<dvi id='form' > ").append("\n");
        sb.append("<form class='formnomargin' name='").append(worker.getFORM_NAME()).append("'  id='").append(worker.getFORM_NAME()).append("'  action='"+CORE_SERVLET+"' method='post'>").append("\n");
        sb.append("     <input type='hidden' name='" + REQUEST_WORKER + "' id='" + REQUEST_WORKER + "' value='").append(worker.getClass().getName()).append("' />").append("\n");
        sb.append("     <input type='hidden' name='" + SRC_JSP + "' id='" + SRC_JSP + "' value='").append(worker.getJspGoto()).append("' />").append("\n");
        sb.append("     <input type='hidden' name='"+BTN_OPTION+"' id='"+BTN_OPTION+"'  value='' /> ").append("\n");
        sb.append("     <input type='hidden' name='" + NAV_TIERS + "' id='" + NAV_TIERS + "' value='").append(worker.getNavTier()).append("' />").append("\n");

        this.println(sb); 
        return EVAL_BODY_INCLUDE;
    } 
    
    @Override
    public void release() { 
        this.worker = null;
        super.release();
    }
 
}
