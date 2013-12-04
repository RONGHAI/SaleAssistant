/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author L5M
 */
@Entity
@Table(name = "clients")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id"),
    @NamedQuery(name = "Client.findByName", query = "SELECT c FROM Client c WHERE c.name = :name"),
    @NamedQuery(name = "Client.findByWangwang", query = "SELECT c FROM Client c WHERE c.wangwang = :wangwang"),
    @NamedQuery(name = "Client.findByQq", query = "SELECT c FROM Client c WHERE c.qq = :qq"),
    @NamedQuery(name = "Client.findByBirthday", query = "SELECT c FROM Client c WHERE c.birthday = :birthday"),
    @NamedQuery(name = "Client.findByGender", query = "SELECT c FROM Client c WHERE c.gender = :gender"),
    @NamedQuery(name = "Client.findByPhone", query = "SELECT c FROM Client c WHERE c.phone = :phone"),
    @NamedQuery(name = "Client.findByDisabled", query = "SELECT c FROM Client c WHERE c.disabled = :disabled"),
    @NamedQuery(name = "Client.findByAddTime", query = "SELECT c FROM Client c WHERE c.addTime = :addTime"),
    @NamedQuery(name = "Client.findByUpdateTime", query = "SELECT c FROM Client c WHERE c.updateTime = :updateTime")})
public class Client implements Serializable, AbstractModel {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "wangwang")
    private String wangwang;
    @Basic(optional = false)
    @Column(name = "qq")
    private int qq;
    @Basic(optional = false)
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Basic(optional = false)
    @Column(name = "gender")
    private String gender;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @Column(name = "disabled")
    private boolean disabled;
    @Basic(optional = false)
    @Column(name = "add_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;
    @Basic(optional = false)
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Lob
    @Column(name = "note")
    private String note;

    public Client() {
    }

    public Client(Integer id) {
        this.id = id;
    }

    public Client(Integer id, String name, String wangwang, int qq, Date birthday, String gender, String phone, boolean disabled, Date addTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.wangwang = wangwang;
        this.qq = qq;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.disabled = disabled;
        this.addTime = addTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWangwang() {
        return wangwang;
    }

    public void setWangwang(String wangwang) {
        this.wangwang = wangwang;
    }

    public int getQq() {
        return qq;
    }

    public void setQq(int qq) {
        this.qq = qq;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getDisabled() {
        return disabled;
    }

    /**
     *
     * @param disabled
     */
    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "me.ronghai.sa.model.Client[ id=" + id + " ]";
    }

}
