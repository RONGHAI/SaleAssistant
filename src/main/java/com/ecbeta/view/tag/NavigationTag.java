/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecbeta.view.tag;

import com.ecbeta.common.core.viewer.bean.NavigationBean;
import static com.ecbeta.common.core.viewer.bean.NavigationBean.getNavTierID;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
    private int[] navTier;
    private String navPrefix = "level";

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
        String id = this.getId();
        
        
        
        JSONObject json = new JSONObject();
        json.put("nodes", NavigationBean.toJson(this.navigationBeans, navPrefix, contextPath, true));
        json.put("name", id);
        //json.put("onClick", "function(event){}");
        
        sb.append("<div id=\"").append(id).append("\" style=\"height: 300px; width: 180px; float: left\"></div>");
        sb.append("<div style=\"clear: both\"></div>");
        sb.append("<script type=\"text/javascript\">//<![CDATA[ \n");
        sb.append("$(document).ready(function () {\n" ); 
        sb.append("	$('#").append(id).append("').w2sidebar(");
        sb.append("             ").append(json.toString());
        sb.append("     );\n" );
        sb.append("});\n" );
        if(navTier != null){
              sb.append("w2ui.").append(id).append(".select('").append(getNavTierID(navPrefix, navTier)).append("');");
        }
        sb.append("w2ui.").append(id).append(".on('*', function (event) {\n");
        sb.append("         if(event.type === 'click'){   \n");
        sb.append("                 var url = w2ui.").append(id).append(".get(event.target)['data-url'];\n");
        sb.append("                 sa.runApp(url);\n");
        sb.append("         };\n");
        sb.append("});\n");
        sb.append("//]]>\n");
        sb.append("</script>\n");
        sb.append("<div id='navigation' > ");
        sb.append(render(this.navigationBeans, 0));
        sb.append("</div>");
        return sb.toString();
    }
    
    
   
 
}
