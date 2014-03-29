/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecbeta.view.html;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ronghai
 */
public class HTMLElement implements Serializable{
    private String innerHTML;
    
    
    private final Map<String, String> attribtues;
    private final String tag;
    private List<HTMLElement> children; 
    
    public HTMLElement addChild(String tag){
        if(this.children == null){
            this.children = new ArrayList<>();
        }
        HTMLElement ch = new HTMLElement(tag);
        this.children.add(ch);
        return ch;
    }

    public String getInnerHTML() {
        return innerHTML;
    }

    public void setInnerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
    }
    
    public HTMLElement(String tag) {
        this.tag = tag;
        this.attribtues = new HashMap<>();
    }
    
    public HTMLElement set(String att, String value){
        this.attribtues.put(att, value);
        return this;
    }
    public HTMLElement remove(String att){
        this.attribtues.remove(att);
        return this;
    }
    
    public HTMLElement add(String att, String value){
        String old = this.attribtues.get(att);
        if(old == null){
            old = "";
        }
        old += " "+ value;
        return this.set(att, old);
    }
    
    public String toHTML(){
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(tag);
        
        for(Map.Entry<String, String> ent : this.attribtues.entrySet()){
            sb.append(ent.getKey()).append("=\"");
            sb.append(ent.getValue());
            sb.append("\" ");
        }
        
        sb.append(">");
        if(StringUtils.isNotEmpty(innerHTML)){
            sb.append(innerHTML);
        }
        if(children != null){
            for(HTMLElement h : children){
                sb.append(h.toHTML());
            }
        }
        
        sb.append("</").append(tag).append(">");
              
        return sb.toString();
    }
}
