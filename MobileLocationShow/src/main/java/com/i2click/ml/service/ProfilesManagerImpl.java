package com.i2click.ml.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.i2click.ml.dao.ProfilesDAO;
import com.i2click.ml.entity.ProfilesEntity;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Transactional
public class ProfilesManagerImpl implements ProfilesManager {

    @Autowired
    private ProfilesDAO profilesDAO;

    @Override
    public ArrayList<Object> locationList() {
        return profilesDAO.locationList();
    }
    
    @Override
    public ProfilesEntity login(String username, String password) {
        return profilesDAO.login(username, password);
    }
    
    @Override
    public boolean logout(int session) {
        return profilesDAO.logout(session);
    }
    /*
    @Override
    public boolean isAlreadyExist(String username) {
        return profilesDAO.isAlreadyExist(username);
    }
    */
    @Override
    public boolean isAlreadyExist(String mobilenumber) {
        return profilesDAO.isAlreadyExist(mobilenumber);
    }
    
    @Override
    public boolean isValidSessionKey(String sessionKey) {
        return profilesDAO.isValidSessionKey(sessionKey);
    }
    
    @Override
    public ProfilesEntity findBySessionKey(String sessionKey) {
        return profilesDAO.findBySessionKey(sessionKey);
    }
    
    @Override
    public ProfilesEntity findByUsername(String username) {
        return profilesDAO.findByUsername(username);
    }
    
    @Override
    public void addProfiles(ProfilesEntity Profiles) {
        profilesDAO.addProfiles(Profiles);
    }

    @Override
    public List<ProfilesEntity> getAllProfiless() {
        return profilesDAO.getAllProfiless();
    }

    @Override
    public void deleteProfiles(Integer ProfilesId) {
        profilesDAO.deleteProfiles(ProfilesId);
    }

    public void setProfilesDAO(ProfilesDAO profilesDAO) {
        this.profilesDAO = profilesDAO;
    }
}
