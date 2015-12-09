package com.weinyc.sa.core.delegate;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public interface ProcessDelegate extends Serializable{
    public void beforeBinding (HttpServletRequest request, String btnClicked, String actionMethod);
    public void afterBinding (HttpServletRequest request, String btnClicked, String actionMethod);
    public void afterProcessing (HttpServletRequest request, String btnClicked, String actionMethod);
    public void beforeProcessing (HttpServletRequest request, String btnClicked, String actionMethod) ;

}
