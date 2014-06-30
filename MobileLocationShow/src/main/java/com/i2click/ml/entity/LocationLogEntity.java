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

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Nazmul
 */
@Document(collection = "locations_log")

public class LocationLogEntity  {
    
    @Id
   
    
    private Integer pkid;
    
    
    private String profileid;
    
    
    private String latitude;
    
    
    private String longitude;
    
   
    private String last_datetime;

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
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the last_datetime
     */
    public String getLast_datetime() {
        return last_datetime;
    }

    /**
     * @param last_datetime the last_datetime to set
     */
    public void setLast_datetime(String last_datetime) {
        this.last_datetime = last_datetime;
    }

    
}
