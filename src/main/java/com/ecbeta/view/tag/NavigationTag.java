/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecbeta.view.tag;

import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class NavigationTag extends AbstractTag {

    public NavigationTag() {
        super();
    }

    private List<NavigationBean> navigationBeans;
    private String bodyString;

    public List<NavigationBean> getNavigationBeans() {
        return navigationBeans;
    }

    public void setNavigationBeans(List<NavigationBean> navigationBeans) {
        this.navigationBeans = navigationBeans;
    }

    @Override
    public int doAfterBody() throws JspException {
        BodyContent bc = this.getBodyContent();
        this.bodyString = bc.getString();
        bc.clearBody();
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        int returnValue = super.doEndTag();
        this.println(this.renderHTML(this.getContextPath(), this.bodyString));
        return returnValue;
    }

    @Override
    public int doStartTag() throws JspException {
        int returnValue = super.doStartTag();

        return returnValue;
    }
 

    private String render(List<NavigationBean> navigationBeans, int level) {
        if (navigationBeans == null || navigationBeans.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < navigationBeans.size(); i++) {
            NavigationBean bean = navigationBeans.get(i);
            sb.append("<div ");
            sb.append(" data-level='").append(level).append("' ");
            sb.append(" > ");
            String url = bean.getUrl(this.getContextPath());
            sb.append("<a href='").append(url).append("' ");
            sb.append(" > ");
            sb.append(bean.getLabel()).append("</a>"); 
            if (bean.getChildren() != null) { 
                sb.append(render(bean.getChildren(), level+1)); 
            }
            sb.append("</div>");
        }
        return sb.toString();
    }

    private Object renderHTML(String contextPath, String bodyString) throws JspException {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id='navigation' > ");
        sb.append(render(this.navigationBeans, 0));
        sb.append("</div>");
        return sb.toString();
    }
    
    
   
 
}
