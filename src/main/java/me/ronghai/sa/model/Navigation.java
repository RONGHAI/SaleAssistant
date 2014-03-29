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
@Entity(name="navigations")
public class Navigation  implements Serializable, AbstractModel {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    
    @Column(name = "tier_1")
    private long tier_1;

    public long getTier_1() {
        return tier_1;
    }

    public void setTier_1(long tier_1) {
        this.tier_1 = tier_1;
    }

    public long getTier_2() {
        return tier_2;
    }

    public void setTier_2(long tier_2) {
        this.tier_2 = tier_2;
    }

    public long getTier_3() {
        return tier_3;
    }

    public void setTier_3(long tier_3) {
        this.tier_3 = tier_3;
    }

    public long getTier_4() {
        return tier_4;
    }

    public void setTier_4(long tier_4) {
        this.tier_4 = tier_4;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
    
    @Column(name = "tier_2")
    private long tier_2;
    
    @Column(name = "tier_3")
    private long tier_3;
    
    @Column(name = "tier_4")
    private long tier_4;
    
    
    @Column(name = "label")
    private String label;
    
    @Column(name = "worker")
    private String worker;
    
    @Column(name = "[order]")
    private String order;
    
    @Column(name = "disabled")
    private boolean disabled;

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Column(name = "add_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "update_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "note")
    private String note;

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Navigation() {
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

