
package com.weinyc.sa.app.bean;

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
}
