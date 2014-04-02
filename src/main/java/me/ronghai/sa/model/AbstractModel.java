/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.model;

/**
 *
 * @author L5M
 */
public abstract class AbstractModel {

    public abstract void setDisabled(boolean disabled);
    public abstract boolean isDisabled();
    
    public abstract boolean isChanged();
    public abstract void setChanged(boolean changed);
    
    public abstract Long getId() ;
    public Long getRecid(){
        return this.getId();
    }
    
}
