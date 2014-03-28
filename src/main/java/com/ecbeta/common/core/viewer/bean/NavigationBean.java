package com.ecbeta.common.core.viewer.bean;

import java.io.Serializable;
import java.util.List;

import com.ecbeta.common.util.StringUtils;


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
    
    
}
