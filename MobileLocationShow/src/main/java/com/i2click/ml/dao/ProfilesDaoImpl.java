package com.i2click.ml.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.i2click.ml.entity.ProfilesEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.UUID;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

@Repository
public class ProfilesDaoImpl implements ProfilesDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public ArrayList<Object> locationList(){
        String queryString = "FROM ProfilesEntity AS pe, LocationEntity AS le WHERE pe.pkid = le.profileid";
        //String queryString = "FROM ProfilesEntity";
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        //List list = query.list();
        query.setReadOnly(true);
        // MIN_VALUE gives hint to JDBC driver to stream results
        query.setFetchSize(Integer.MIN_VALUE);
        ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
        ArrayList<Object> hashMapList = new ArrayList<Object>();
        // iterate over results
        while (results.next()) {
            Object obj = results.get();
            hashMapList.add(obj);
            //hashMap.put("demo", results.getString(0));
            // process row then release reference
            // you may need to flush() as well
        }
        results.close();
        return hashMapList;
    }
    
    @Override
    public ProfilesEntity login(String username, String password) {
        String queryString = "FROM ProfilesEntity WHERE username = :username AND password = :password";
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List list = query.list();

        if (list.size() > 0) {
            ProfilesEntity profile = (ProfilesEntity) list.get(0);
            if (profile.getSessionkey() == null || profile.getSessionkey().isEmpty()) {
                String sessionKey = UUID.randomUUID().toString();
                System.out.println("sessionKey :: " + sessionKey);
                String updateQueryString = "UPDATE ProfilesEntity SET sessionkey = :sessionkey WHERE pkid = :userid";
                Query updateQuery = this.sessionFactory.getCurrentSession().createQuery(updateQueryString);
                updateQuery.setParameter("sessionkey", sessionKey);
                updateQuery.setParameter("userid", profile.getPkid());
                int result = updateQuery.executeUpdate();
                if (result > 0) {
                    profile.setSessionkey(sessionKey);
                }
            }
            return profile;
        } else {
            return null;
        }
    }
    
    @Override
    public boolean logout(int profileId) {
        String updateQueryString = "UPDATE ProfilesEntity SET sessionkey = :sessionkey WHERE pkid = :userid";
        Query updateQuery = this.sessionFactory.getCurrentSession().createQuery(updateQueryString);
        updateQuery.setParameter("sessionkey", "");
        updateQuery.setParameter("userid", profileId);
        int result = updateQuery.executeUpdate();
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
/*
    @Override
    public boolean isAlreadyExist(String username) {
        String queryString = "FROM ProfilesEntity WHERE username = :username";
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        query.setParameter("username", username);
        List list = query.list();

        if (list.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
  */  
    @Override
    public boolean isAlreadyExist(String mobilenumber) {
        String queryString = "FROM ProfilesEntity WHERE mobilenumber = :mobilenumber";
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        query.setParameter("mobilenumber", mobilenumber);
        List list = query.list();

        if (list.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public ProfilesEntity findBySessionKey(String sessionKey) {
        String queryString = "FROM ProfilesEntity WHERE sessionkey = :sessionkey";
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        query.setParameter("sessionkey", sessionKey);
        List list = query.list();

        if (list.isEmpty()) {
            return null;
        } else {
            return (ProfilesEntity) list.get(0);
        }
    }
    
    @Override
    public ProfilesEntity findByUsername(String username) {
        String queryString = "FROM ProfilesEntity WHERE username = :username";
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        query.setParameter("username", username);
        List list = query.list();

        if (list.isEmpty()) {
            return null;
        } else {
            return (ProfilesEntity) list.get(0);
        }
    }

    @Override
    public boolean isValidSessionKey(String sessionKey) {
        String queryString = "FROM ProfilesEntity WHERE sessionkey = :sessionkey";
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        query.setParameter("sessionkey", sessionKey);
        List list = query.list();

        if (list.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void addProfiles(ProfilesEntity profile) {
    	
        this.sessionFactory.getCurrentSession().saveOrUpdate(profile);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProfilesEntity> getAllProfiless() {
        return this.sessionFactory.getCurrentSession().createQuery("from ProfilesEntity").list();
    }

    @Override
    public void deleteProfiles(Integer profileId) {
        ProfilesEntity profile = (ProfilesEntity) sessionFactory.getCurrentSession().load(ProfilesEntity.class, profileId);
        if (null != profile) {
            this.sessionFactory.getCurrentSession().delete(profile);
        }
    }
}
