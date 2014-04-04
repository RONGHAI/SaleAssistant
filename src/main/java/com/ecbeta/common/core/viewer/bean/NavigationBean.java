package com.ecbeta.common.core.viewer.bean;

import com.ecbeta.common.constants.Constants;
import java.io.Serializable;
import java.util.List;

import com.ecbeta.common.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import org.codehaus.jackson.annotate.JsonIgnore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NavigationBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<NavigationBean> children;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public List<NavigationBean> getChildren () {
        return children;
    }
    public void setChildren (List<NavigationBean> children) {
        this.children = children;
    }
    
    private int[] navTier;

    public int[] getNavTier() {
        return navTier;
    }
    public String getNavTier(String join){
        return StringUtils.join(navTier, join);
    }
    public void setNavTier (int[] navTier) {
        this.navTier = navTier;
    }
    
    
    public int[] getParentNavTier() {
        int _navTier[] = this.navTier.clone();
        for(int i = _navTier.length - 1; i >= 0; i--){
            if(_navTier[i] != 0){
                _navTier[i] = 0;
                break;
            }
        }
        if(Arrays.equals(_navTier , navTier) || _navTier[0] == 0){
            return null;
        }
        return _navTier;
    }
    public String getParentNavTier(String join){
        int [] p = getParentNavTier();
        if(p == null){
            return null;
        }
        return StringUtils.join(p, join);
    }
    
    private String worker;
    private String label;
    private int order;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

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
    
    public void addChild(NavigationBean bean){
        if(this.children == null){
            this.children = new ArrayList<>();
        }
        this.children.add(bean);
    }
    
    
    @JsonIgnore
    public String getUrl(String contextPath){
        if(StringUtils.isEmpty(contextPath) || StringUtils.isEmpty(this.worker)){
            return "";
        }
        StringBuilder url = new StringBuilder();
        if(StringUtils.isNotEmpty(contextPath)){
            url.append(contextPath);
        }
        url.append("/").append(Constants.CORE_SERVLET).append("/?");
        //url.append(Constants.REQUEST_WORKER).append("=").append(this.worker).append("&"); 
        url.append(Constants.NAV_TIERS).append("=").append(this.getNavTier("_"));
        return url.toString();
    }
    
    public JSONObject toJson(String idPrefix, String contextPath,boolean withUri) {
        JSONObject json = new JSONObject();
        json.put("id", getNavTierID(idPrefix, navTier));
        json.put("text", this.getLabel());
        json.put("expanded", false);
        json.put("navTier", this.getNavTier("_"));
        if(withUri){
            json.put("data-url", this.getUrl(contextPath));
        }
        if(StringUtils.isNotEmpty(icon)){
            json.put("icon", icon);
        }
        if (this.children != null && this.children.size() > 0) {
            //json.put("group", true);
            json.put("nodes", toJson(this.children, idPrefix, contextPath,withUri));
        }
        return json;
    }

    public static JSONArray toJson(List<NavigationBean> beans, String idPrefix, String contextPath, boolean withUri) { 
        JSONArray array = new JSONArray();
        for (NavigationBean b : beans) {
            array.add(b.toJson(idPrefix, contextPath, withUri));
        }
        return array;
    }
    
    public static String getNavTierID(String idPrefix, int[] navTier){
        return idPrefix+"-" + StringUtils.join(navTier, "-");
    }
}
