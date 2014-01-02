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
public interface AbstractModel {

    public void setDisabled(boolean disabled);
    public boolean isDisabled();
    
    public boolean isChanged();
    public void setChanged(boolean changed);
}
