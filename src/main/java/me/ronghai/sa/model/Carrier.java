/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ronghai
 */
@Entity(name="carriers")
public class Carrier implements Serializable, AbstractModel {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    
    @Column(name = "website")
    private String website;
    
    @Column(name = "track_url")
    private String trackURL;
    
    @Column(name =  "track_method")
    private String trackMethod;
    
    @Column(name = "disabled")
    private boolean disabled;

    public boolean isDisabled() {
        return disabled;
    }

    @Column(name = "add_time", nullable=true)
    @Temporal(TemporalType.TIME)
    private Date addTime;

    @Column(name = "update_time", nullable=true)
    @Temporal(TemporalType.TIME)
    private Date updateTime;

    @Column(name = "note")
    private String note;

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Carrier() {
    }

    public Carrier(Long id, String name, String website, String trackURL, String trackMethod, boolean disabled, Date addTime, Date updateTime, String note) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.trackURL = trackURL;
        this.trackMethod = trackMethod;
        this.disabled = disabled;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.note = note;
    }
    
    public Carrier(String name, String website, String trackURL, String trackMethod, boolean disabled, Date addTime, Date updateTime, String note) {
        this.name = name;
        this.website = website;
        this.trackURL = trackURL;
        this.trackMethod = trackMethod;
        this.disabled = disabled;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTrackURL() {
        return trackURL;
    }

    public void setTrackURL(String trackURL) {
        this.trackURL = trackURL;
    }

    public String getTrackMethod() {
        return trackMethod;
    }

    public void setTrackMethod(String trackMethod) {
        this.trackMethod = trackMethod;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    private transient  boolean changed;
    @Override
    public boolean isChanged() {
        return changed;
    }
    @Override
    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
