/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i2click.ml.service;

import com.i2click.ml.entity.FacebookEntity;

/**
 *
 * @author Nazmul
 */
public interface FacebookManager {

    public void save(FacebookEntity entity);

    public void update(FacebookEntity entity);

    public boolean isExist(int profileId);
    
    public FacebookEntity getFBObject(int profileId);
}