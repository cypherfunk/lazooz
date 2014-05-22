package com.i2click.ml.service;

import com.i2click.ml.dao.LocationLogDAO;
import com.i2click.ml.entity.LocationLogEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LocationLogManagerImpl implements LocationLogManager {

    @Autowired
    private LocationLogDAO locationLogDAO;

    @Override
    public void save(LocationLogEntity entity) {
    	locationLogDAO.save(entity);
    }

    @Override
    public void update(LocationLogEntity entity) {
    	locationLogDAO.update(entity);
    }

    @Override
    public boolean isExist(int profileId) {
        return locationLogDAO.isExist(profileId);
    }
    
    @Override
    public LocationLogEntity getLEObject(int profileId) {
        return locationLogDAO.getLEObject(profileId);
    }

}
