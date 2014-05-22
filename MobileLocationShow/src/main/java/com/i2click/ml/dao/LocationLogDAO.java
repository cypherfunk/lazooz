/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i2click.ml.dao;

import com.i2click.ml.entity.LocationLogEntity;

/**
 *
 * @author Nazmul
 */
public interface LocationLogDAO {

    public void save(LocationLogEntity entity);

    public void update(LocationLogEntity entity);

    public boolean isExist(int profileId);
    
    public LocationLogEntity getLEObject(int profileId);
}