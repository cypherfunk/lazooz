/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i2click.ml.dao;

import com.i2click.ml.entity.LocationEntity;
import com.i2click.ml.entity.ProfilesEntity;

/**
 *
 * @author Nazmul
 */
public class UserLocation {
    private ProfilesEntity profilesEntity = new ProfilesEntity();
    private LocationEntity LocationEntity = new LocationEntity();

    /**
     * @return the profilesEntity
     */
    public ProfilesEntity getProfilesEntity() {
        return profilesEntity;
    }

    /**
     * @param profilesEntity the profilesEntity to set
     */
    public void setProfilesEntity(ProfilesEntity profilesEntity) {
        this.profilesEntity = profilesEntity;
    }

    /**
     * @return the LocationEntity
     */
    public LocationEntity getLocationEntity() {
        return LocationEntity;
    }

    /**
     * @param LocationEntity the LocationEntity to set
     */
    public void setLocationEntity(LocationEntity LocationEntity) {
        this.LocationEntity = LocationEntity;
    }
}
