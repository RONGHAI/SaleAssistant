/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.view.tag;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public abstract class AbstractTag extends BodyTagSupport implements Serializable {
     private static final Logger logger =   Logger.getLogger(AbstractTag.class.getName()) ;
    
     public AbstractTag println (Object ... objs)  {
        if (objs != null) {
            for (Object o : objs) {
                try {
                    this.getWriter().println(o);
                } catch (IOException ex) {
                   logger.log(Level.WARNING, null, ex);
                }
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
