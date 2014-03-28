package com.ecbeta.common.core.viewer;

import java.io.OutputStream;
import java.io.Serializable;

public abstract class BaseViewer implements Serializable {
    public void export(OutputStream out , String fileName){
        
    }

    public String toHTML(){
        return "";
    }
}
