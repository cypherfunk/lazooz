/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i2click.ml.service;

import com.i2click.ml.dao.*;
import com.i2click.ml.entity.LocationEntity;

/**
 *
 * @author Nazmul
 */
public interface LocationManager {

    public void save(LocationEntity entity);

    public void update(LocationEntity entity);

    public boolean isExist(int profileId);
    
    public LocationEntity getLEObject(int profileId);
}