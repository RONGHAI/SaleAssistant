package com.weinyc.sa.core.bean;

import java.io.Serializable;

public class BaseServicerParaBean implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String panel;
 
    
    private boolean displayStage;

    public boolean isDisplayStage () {
        return displayStage;
    }

    public void setDisplayStage (boolean displayStage) {
        this.displayStage = displayStage;
    }
    
    
    private boolean selectionPanelStatusOpen = true;

    public boolean isSelectionPanelStatusOpen () {
        return selectionPanelStatusOpen;
    }

    public void setSelectionPanelStatusOpen (boolean selectionPanelStatusOpen) {
        this.selectionPanelStatusOpen = selectionPanelStatusOpen;
    }

    public String getPanel () {
        return panel;
    }

    public void setPanel (String panel) {
        this.panel = panel;
    }
    
 
}
