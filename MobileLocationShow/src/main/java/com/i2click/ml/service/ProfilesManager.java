package com.i2click.ml.service;

import java.util.List;

import com.i2click.ml.entity.ProfilesEntity;
import java.util.ArrayList;
import java.util.HashMap;

public interface ProfilesManager {

    public ArrayList<Object> locationList();
    
    public ProfilesEntity login(String username, String password);
    
    public boolean logout(int sessionkey);
    
    public boolean isAlreadyExist(String username);
    
    public boolean isValidSessionKey(String sessionKey);
    
    public ProfilesEntity findBySessionKey(String sessionKey);
    
    public ProfilesEntity findByUsername(String username);
    
    public void addProfiles(ProfilesEntity Profiles);

    public List<ProfilesEntity> getAllProfiless();

    public void deleteProfiles(Integer ProfilesId);
}
