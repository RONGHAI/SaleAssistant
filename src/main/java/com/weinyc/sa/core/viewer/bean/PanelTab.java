package com.weinyc.sa.core.viewer.bean;

import java.io.Serializable;

public interface PanelTab extends Serializable{
    
    public int panelIndex();
    public String getLabel();
    public boolean isEnable();
    public int getPanelIndex();
}
