package com.ecbeta.common.core.viewer.bean;

import com.ecbeta.common.constants.Constants;
import java.io.Serializable;
import java.util.List;

import com.ecbeta.common.util.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;


public class NavigationBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<NavigationBean> children;
    public List<NavigationBean> getChildren () {
        return children;
    }
    public void setChildren (List<NavigationBean> children) {
        this.children = children;
    }
    
    private int[] navTier;
    public String getNavTier(String join){
        return StringUtils.join(navTier, join);
    }
    public void setNavTier (int[] navTier) {
        this.navTier = navTier;
    }
    private String worker;
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
    
    
    public String getUrl(){
         return getUrl("");
    }
    
    @JsonIgnore
    public String getUrl(String contextPath){
        StringBuilder url = new StringBuilder();
        if(StringUtils.isNotEmpty(contextPath)){
            url.append(contextPath);
        }
        url.append("/").append(Constants.CORE_SERVLET).append("/?");
        url.append(Constants.REQUEST_WORKER).append("=").append(this.worker).append("&"); 
        url.append(Constants.NAV_TIERS).append("=").append(this.getNavTier("_"));
        return url.toString();
    }
    
}
