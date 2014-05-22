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
@Table(name="lk_facebook")
public class FacebookEntity  {
    
    @Id
    @Column(name="pkid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pkid;
    
    @Column(name="profileid")
    private String profileid;
    
    @Column(name="access_token")
    private String access_token;
    
    @Column(name="fbuid")
    private String fbuid;
    
    @Column(name="data")
    private String data;

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
     * @return the profileid
     */
    public String getProfileid() {
        return profileid;
    }

    /**
     * @param profileid the profileid to set
     */
    public void setProfileid(String profileid) {
        this.profileid = profileid;
    }

    /**
     * @return the fbuid
     */
    public String getFbuid() {
        return fbuid;
    }

    /**
     * @param fbuid the fbuid to set
     */
    public void setFbuid(String fbuid) {
        this.fbuid = fbuid;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the access_token
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     * @param access_token the access_token to set
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    

}
