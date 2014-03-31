/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecbeta.common.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class JSONRefreshZones implements Serializable {

    private Map<String, String> contentRefreshZone;
    private List<String> scriptRefreshZone;
    private List<String> clearRefreshZone;

    public JSONRefreshZones() {
        this.contentRefreshZone = new HashMap<>();
        this.scriptRefreshZone = new ArrayList<>();
        this.clearRefreshZone = new ArrayList<>();
    }

    public JSONRefreshZones combine(JSONRefreshZones re) {
        if (re == null) {
            return this;
        }
        this.contentRefreshZone.putAll(re.contentRefreshZone);
        this.scriptRefreshZone.addAll(re.scriptRefreshZone);
        this.scriptRefreshZone.addAll(re.scriptRefreshZone);
        return this;
    }

    public JSONRefreshZones addContentRefreshZone(String zoneName, String content) {
        this.contentRefreshZone.put(zoneName, content);
        return this;
    }

    public JSONRefreshZones addClearRefreshZone(String... zoneNames) {
        for (String zoneName : zoneNames) {
            this.clearRefreshZone.add(zoneName);
        }
        return this;
    }

    public JSONRefreshZones addScriptRefreshZone(String content) {
        this.scriptRefreshZone.add(content);
        return this;
    }

    public JSONRefreshZones reset() {
        this.contentRefreshZone = new HashMap<>();
        this.scriptRefreshZone = new ArrayList<>();
        this.clearRefreshZone = new ArrayList<>();
        return this;
    }

    public Map<String, String> getContentRefreshZone() {
        return contentRefreshZone;
    }

    public void setContentRefreshZone(Map<String, String> contentRefreshZone) {
        this.contentRefreshZone = contentRefreshZone;
    }

    public List<String> getScriptRefreshZone() {
        return scriptRefreshZone;
    }

    public void setScriptRefreshZone(List<String> scriptRefreshZone) {
        this.scriptRefreshZone = scriptRefreshZone;
    }

    public List<String> getClearRefreshZone() {
        return clearRefreshZone;
    }

    public void setClearRefreshZone(List<String> clearRefreshZone) {
        this.clearRefreshZone = clearRefreshZone;
    }
    
    public String toString(){
        return super.toString();
    }
 
}
