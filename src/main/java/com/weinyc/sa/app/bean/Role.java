
package com.weinyc.sa.app.bean;

import net.sf.json.JSONObject;

/**
 *
 * @author L5M
 */
public enum Role {
    Admin("管理员",0), User("用户",1), Client("客户",2),    
    
    ;
    private final String label;
    private final int level;
    private Role(int level){
        this.label = this.name();
        this.level = level;
    }
    private Role(String label, int level){
        this.label = label;
        this.level = level;
    }
    public String getLabel(){
        return this.label;
    }
    public int getLevel(){
        return level;
    }
    
      public JSONObject toJSON() {
        Role r = this;
        JSONObject map = new JSONObject();
        map.put("id", r.name());
        map.put("name", r.getLabel());
        map.put("level", this.level);
        map.put("text", r.getLabel());
        return map;
    }
}
