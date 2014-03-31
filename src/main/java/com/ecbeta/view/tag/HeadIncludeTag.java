/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecbeta.view.tag;

import com.ecbeta.common.constants.Constants;
import com.ecbeta.common.core.AbstractWorker;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class HeadIncludeTag extends AbstractTag {

    public HeadIncludeTag() {
        super();
    }

    private AbstractWorker worker;
    private String jqueryVersion = "1.9.1";
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

    public AbstractWorker getWorker() {
        return worker;
    }

    public void setWorker(AbstractWorker worker) {
        this.worker = worker;
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
        sb.append("<script src=\"resource/js/jquery-").append(jqueryVersion).append(".js\"></script> \n");
        sb.append("<script src=\"resource/js/saleassistant.js?").append(dev ? ("time=" + System.currentTimeMillis()) : "").append("\"></script> \n");
        sb.append("<script > \n");
        sb.append("$(document).ready(function() { \n");
        sb.append("     ").append(this.worker.getUrl()).append(" sale_assistant.init('" + "','" + Constants.BTN_OPTION + "','").append(this.worker.getFORM_NAME()).append("'); \n");
        sb.append("}); \n");
        sb.append("</script> \n");
        this.println(sb);
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public void release() {
        this.worker = null;
        super.release();
    }

}
