/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecbeta.view.tag;

import com.ecbeta.common.core.servlet.CoreServlet;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public abstract class AbstractTag extends BodyTagSupport implements Serializable {
      private static final Logger logger =   Logger.getLogger(AbstractTag.class.getName()) ;
    
     public AbstractTag println (Object ... objs) {
        if (objs != null) {
            for (Object o : objs) {
                this.getWriter().println(o);
            }
        } 
        return this;
    }
    public JspWriter getWriter () {
        JspWriter writer = this.pageContext.getOut();
        return writer;
    }
    
     public String getContextPath() {
        return ((HttpServletRequest) this.pageContext.getRequest()).getContextPath();
    }

    protected HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        return request;
    }
}
