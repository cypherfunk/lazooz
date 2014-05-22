package com.i2click.ml.service;

import com.i2click.ml.dao.LocationDAO;
import com.i2click.ml.entity.LocationEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LocationManagerImpl implements LocationManager {

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public void save(LocationEntity entity) {
        locationDAO.save(entity);
    }

    @Override
    public void update(LocationEntity entity) {
        locationDAO.update(entity);
    }

    @Override
    public boolean isExist(int profileId) {
        return locationDAO.isExist(profileId);
    }
    
    @Override
    public LocationEntity getLEObject(int profileId) {
        return locationDAO.getLEObject(profileId);
    }

}
