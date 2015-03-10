/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecbeta.view.tag;

import static com.ecbeta.common.core.viewer.bean.NavigationBean.getNavTierID;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;

import com.ecbeta.common.constants.Constants;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import com.ecbeta.common.util.JSONUtils;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class NavigationTag extends AbstractTag {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public NavigationTag() {
        super();
    }

    private List<NavigationBean> navigationBeans;
    private String bodyString;
    private int[] navTier;
    private String navPrefix = "level";
    private boolean w2ui = false;

    public boolean isW2ui() {
        return w2ui;
    }

    public void setW2ui(boolean w2ui) {
        this.w2ui = w2ui;
    }

    public String getNavPrefix() {
        return navPrefix;
    }

    public void setNavPrefix(String navPrefix) {
        this.navPrefix = navPrefix;
    }

    public int[] getNavTier() {
        return navTier;
    }

    public void setNavTier(int[] navTier) {
        this.navTier = navTier;
    }
    

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
        if(this.getId() == null || "".equals(this.getId())){
            this.setId("navigationbar");
        }
        return returnValue;
    }
 
    
    public String createW2UI(String contextPath){
         StringBuilder sb = new StringBuilder();
         
        String navs = JSONUtils.toString(NavigationBean.toJson(this.navigationBeans, navPrefix, contextPath, true));
     
       /* JSONObject json = new JSONObject();
        json.put("nodes", NavigationBean.toJson(this.navigationBeans, navPrefix, contextPath, true));
        json.put("name", id);*/
        
        StringBuilder onclick = new StringBuilder();
        onclick.append("function (event) {\n");
        onclick.append("    if(event.type === 'click'){   \n");
        onclick.append("       var et = w2ui.").append(id).append(".get(event.target);\n");
        onclick.append("       sa.runApp(et);\n");
        onclick.append("    };\n");
        onclick.append("}\n"); 
       // json.put("onClick" , onclick.toString() );
        
        sb.append("{\n" );  
        //sb.append("        ").append(json.toString());
        sb.append("         name: ").append("'").append(id).append("'").append(", \n");
        sb.append("         nodes: ").append(navs).append(", \n");
        sb.append("         onClick: ").append(onclick).append("").append("\n");
        sb.append("}\n" ); 
        return sb.toString();
    }
    
    private Object renderW2UI(String contextPath, String bodyString) throws JspException {
        StringBuilder sb = new StringBuilder();
        sb.append("<script type=\"text/javascript\">//<![CDATA[ \n");
        sb.append("$(function () {\n");
        sb.append(" sa.").append(id).append("=").append(this.createW2UI(contextPath)).append("; ");
        sb.append("});\n");
        sb.append("//]]>\n");
        sb.append("</script>\n");
        return sb.toString();
    }

    private Object renderHTML(String contextPath, String bodyString) throws JspException {
        
        if(w2ui){
            return renderW2UI(contextPath, bodyString);
        }
        
        
        StringBuilder sb = new StringBuilder();
       // String id = this.getId();
        
        
         
     
        
        sb.append("<div id=\"").append(id).append("\" style=\"height: 300px; width: 180px; float: left\"></div>");
        
        sb.append("<script type=\"text/javascript\">//<![CDATA[ \n");
        sb.append("$(function () {\n" ); 
        sb.append("    $('#").append(id).append("').w2sidebar(\n\n");
        sb.append( createW2UI(contextPath) );
        sb.append("    );\n\n" );
        sb.append("});\n" );
        
        
        sb.append("$(document).ready(function () {\n" ); 
        if(navTier != null){
             sb.append(""+Constants.SAJS_PREFIX+".selectSidebar('").append(id).append("','").append(getNavTierID(navPrefix, navTier)).append("');\n");
        }
       
        sb.append("});\n" );
        sb.append("//]]>\n");
        sb.append("</script>\n");
         
        return sb.toString();
    }
    
    
   
 
}
