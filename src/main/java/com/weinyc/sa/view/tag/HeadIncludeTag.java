/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weinyc.sa.view.tag;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.common.constants.Constants;

import javax.servlet.jsp.JspException;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class HeadIncludeTag extends AbstractTag {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public HeadIncludeTag() {
        super();
    }

    private AbstractController controller;

    public AbstractController getController() {
        return controller;
    }

    public void setController(AbstractController controller) {
        this.controller = controller;
    }
    private String jqueryVersion = "2.1.0";
    private String w2uiVersion = "release";

    public String getW2uiVersion() {
        return w2uiVersion;
    }

    public void setW2uiVersion(String w2uiVersion) {
        this.w2uiVersion = w2uiVersion;
    }
    private boolean dev = true;

    public String getJqueryVersion() {
        return jqueryVersion;
    }

    public void setJqueryVersion(String jqueryVersion) {
        this.jqueryVersion = jqueryVersion;
    }

    public boolean isDev() {
        return dev;
    }

    public void setDev(boolean dev) {
        this.dev = dev;
    }

    public AbstractController getWorker() {
        return controller;
    }

    public void setWorker(AbstractController worker) {
        this.controller = worker;
    }

    @Override
    public int doEndTag() throws JspException {
        int returnValue = super.doEndTag();

        return returnValue;
    }

    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();
        ///HttpServletRequest request = this.getRequest();

        StringBuilder sb = new StringBuilder();
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /> \n");
        sb.append("<script src=\"").append(this.getContextPath()).append("/resources/js/jquery-").append(jqueryVersion).append(".js\"></script> \n");
        sb.append("<script src=\"").append(this.getContextPath()).append("/resources/js/salesassistant.js?").append(dev ? ("time=" + System.currentTimeMillis()) : "").append("\"></script> \n");
        sb.append("<script src=\"").append(this.getContextPath()).append("/resources/js/salesassistant.i18n.js?").append(dev ? ("time=" + System.currentTimeMillis()) : "").append("\"></script> \n");
        sb.append("<script src=\"").append(this.getContextPath()).append("/resources/js/salesassistant.grid.js?").append(dev ? ("time=" + System.currentTimeMillis()) : "").append("\"></script> \n");
        sb.append("<script src=\"").append(this.getContextPath()).append("/resources/js/w2ui-").append(w2uiVersion).append(".js\"></script> \n");
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"").append(this.getContextPath()).append("/resources/css/w2ui-").append(w2uiVersion).append(".css\"/> \n");
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"").append(this.getContextPath()).append("/resources/css/sa.css\"/> \n");
        sb.append("<script > \n");
        sb.append("$(document).ready(function() { \n");
        if(controller != null){
            sb.append("     ").append("").append(" "+Constants.SAJS_PREFIX+".init('").append(this.controller.getUrl()).append("','" + Constants.BTN_OPTION + "','").append(this.controller.getFORM_NAME()).append("','")
                .append(Constants.REFRESH_TYPE).append("'); \n");
         }
        sb.append("      var sa = window.sa = "+Constants.SAJS_PREFIX+"; ");
        sb.append("}); \n");
        sb.append("</script> \n");
        this.println(sb);
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public void release() {
        this.controller = null;
        super.release();
    }

}
