/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i2click.ml.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Nazmul
 */
@Entity
@Table(name="profiles")
public class ProfilesEntity  {
    
    @Id
    @Column(name="pkid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pkid;
    
    @Column(name="username")
    private String username;
    
    @Column(name="password")
    private String password;
    
    @Column(name="fullname")
    private String fullname;
    
    @Column(name="sessionkey")
    private String sessionkey;
    
    @Column(name="hometown")
    private String hometown;
    
    @Column(name="age")
    private String age;
    
    @Column(name="isfbagree")
    private String isfbagree;
    
    @Column(name="created_datetime")
    private String created_datetime;
    
    @Column(name="mobilenumber")
    private String mobilenumber;
    
    @Column(name="ActivationCode")
    private String ActivationCode;
    
    @Column(name="Active")
    private Integer Active;
    
    @Column(name="zooz")
    private Integer zooz;
    
    @Column(name="publickey")
    private String publickey;
    /**
     * @return the pkid
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * @param pkid the pkid to set
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the sessionkey
     */
    public String getSessionkey() {
        return sessionkey;
    }

    /**
     * @param sessionkey the sessionkey to set
     */
    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    /**
     * @return the hometown
     */
    public String getHometown() {
        return hometown;
    }

    /**
     * @param hometown the hometown to set
     */
    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return the isfbagree
     */
    public String getIsfbagree() {
        return isfbagree;
    }

    /**
     * @param isfbagree the isfbagree to set
     */
    public void setIsfbagree(String isfbagree) {
        this.isfbagree = isfbagree;
    }

    /**
     * @return the created_datetime
     */
    public String getCreated_datetime() {
        return created_datetime;
    }

    /**
     * @param created_datetime the created_datetime to set
     */
    public void setCreated_datetime(String created_datetime) {
        this.created_datetime = created_datetime;
    }
    /**
     * @return the mobilenumber
     */
    public String getMobileNumber() {
        return mobilenumber;
    }

    /**
     * @param get  the mobilenumber to set
     */
    public void setMobileNumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
    
    /**
     * @return the ActivationCode
     */
    public String getActivationCode() {
        return ActivationCode;
    }

    /**
     * @param get  the ActivationCode to set
     */
    public void setActivationCode(String ActivationCode) {
        this.ActivationCode = ActivationCode;
    }
    
    /**
     * @return the Active
     */
    public Integer getActive() {
        return Active;
    }

    /**
     * @param get  the Active to set
     */
    public void setActive(Integer Active) {
        this.Active = Active;
    }
    
    /**
     * @return the zooz
     */
    public Integer getZooz() {
        return zooz;
    }

    /**
     * @param get  the zooz to set
     */
    public void setZooz(Integer zooz) {
        this.zooz = zooz;
    }
    
    /**
     * @return the publickey
     */
    public String getPubKey() {
        return publickey;
    }

    
    /**
     * @param get  the publickey to 
     */
    public void setPubKey(String publickey) {
        this.publickey = publickey;
    }

}
