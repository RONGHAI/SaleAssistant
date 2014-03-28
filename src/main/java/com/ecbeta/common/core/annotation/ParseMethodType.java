/**
 * 
 */
package com.ecbeta.common.core.annotation;

/**
 * @author Ronghai
 * Nov 16, 2012 9:25:31 AM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 */
public enum ParseMethodType {
    StateBean(true/* getStateBean() */), SingleRadioTag(true), MultipleCheckboxTag(true), BooleanCheckboxTag(true), ParseRequest, ParseRequestWithAction, Param, AutoCall;
    
    private final boolean tag;
    ParseMethodType(){
        this.tag = false;
    }
    ParseMethodType(boolean tag){
        this.tag = tag;
    }
    public boolean isTag() {
        return tag;
    }
}
