package com.i2click.ml.dao;

import com.i2click.ml.entity.ProfilesEntity;
import com.i2click.ml.entity.FacebookEntity;
import com.i2click.ml.entity.LocationEntity;

public interface RESTfulDAO {

    public void login(ProfilesEntity Profiles);

    public void registration(ProfilesEntity Profiles);

    public void setFBInfo(FacebookEntity Facebook);

    public void setGPSData(LocationEntity Location);
    
    public void activation(ProfilesEntity Profiles);
}