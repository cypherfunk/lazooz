package com.i2click.ml.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.i2click.ml.config.SpringMongoConfig;
import com.i2click.ml.entity.ProfilesEntity;


@Repository
public class ProfilesDaoImpl implements ProfilesDAO {

	ApplicationContext ctx = 
            new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

    @Override
    public ArrayList<Object> locationList(){
		return null;
//        String queryString = "FROM ProfilesEntity AS pe, LocationEntity AS le WHERE pe.pkid = le.profileid";
//        //String queryString = "FROM ProfilesEntity";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
//        //List list = query.list();
//        query.setReadOnly(true);
//        // MIN_VALUE gives hint to JDBC driver to stream results
//        query.setFetchSize(Integer.MIN_VALUE);
//        ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
//        ArrayList<Object> hashMapList = new ArrayList<Object>();
//        // iterate over results
//        while (results.next()) {
//            Object obj = results.get();
//            hashMapList.add(obj);
//            //hashMap.put("demo", results.getString(0));
//            // process row then release reference
//            // you may need to flush() as well
//        }
//        results.close();
//        return hashMapList;
    }
    
    @Override
    public ProfilesEntity login(String username, String password) {
		return null;
//        String queryString = "FROM ProfilesEntity WHERE username = :username AND password = :password";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
//        query.setParameter("username", username);
//        query.setParameter("password", password);
//        List list = query.list();
//
//        if (list.size() > 0) {
//            ProfilesEntity profile = (ProfilesEntity) list.get(0);
//            if (profile.getSessionkey() == null || profile.getSessionkey().isEmpty()) {
//                String sessionKey = UUID.randomUUID().toString();
//                System.out.println("sessionKey :: " + sessionKey);
//                String updateQueryString = "UPDATE ProfilesEntity SET sessionkey = :sessionkey WHERE pkid = :userid";
//                Query updateQuery = this.sessionFactory.getCurrentSession().createQuery(updateQueryString);
//                updateQuery.setParameter("sessionkey", sessionKey);
//                updateQuery.setParameter("userid", profile.getPkid());
//                int result = updateQuery.executeUpdate();
//                if (result > 0) {
//                    profile.setSessionkey(sessionKey);
//                }
//            }
//            return profile;
//        } else {
//            return null;
//        }
    }
    
    @Override
    public boolean logout(int profileId) {
		return false;
//        String updateQueryString = "UPDATE ProfilesEntity SET sessionkey = :sessionkey WHERE pkid = :userid";
//        Query updateQuery = this.sessionFactory.getCurrentSession().createQuery(updateQueryString);
//        updateQuery.setParameter("sessionkey", "");
//        updateQuery.setParameter("userid", profileId);
//        int result = updateQuery.executeUpdate();
//        if (result > 0) {
//            return true;
//        } else {
//            return false;
//        }
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
    	
    	Query searchUserQuery = new Query(Criteria.where("mobilenumber").is(mobilenumber));
    	List<ProfilesEntity> userList = mongoOperation.find(searchUserQuery, ProfilesEntity.class);

    	
       

        if (userList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public ProfilesEntity findBySessionKey(String sessionKey) {
    	Query searchUserQuery = new Query(Criteria.where("sessionkey").is(sessionKey));
    	List<ProfilesEntity> userList = mongoOperation.find(searchUserQuery, ProfilesEntity.class);


        if (userList.isEmpty()) {
            return null;
        } else {
            return (ProfilesEntity) userList.get(0);
        }
    }
    
    @Override
    public ProfilesEntity findByUsername(String username) {
    	Query searchUserQuery = new Query(Criteria.where("username").is(username));
    	List<ProfilesEntity> userList = mongoOperation.find(searchUserQuery, ProfilesEntity.class);

        if (userList.isEmpty()) {
            return null;
        } else {
            return (ProfilesEntity) userList.get(0);
        }
    }

    @Override
    public boolean isValidSessionKey(String sessionKey) {
    	Query searchUserQuery = new Query(Criteria.where("sessionkey").is(sessionKey));
    	List<ProfilesEntity> userList = mongoOperation.find(searchUserQuery, ProfilesEntity.class);


        if (userList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void addProfiles(ProfilesEntity profile) {
    	mongoOperation.save(profile);
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProfilesEntity> getAllProfiless() {
    	
        return mongoOperation.findAll(ProfilesEntity.class);
    }

    @Override
    public void deleteProfiles(Integer profileId) {
     //   ProfilesEntity profile = (ProfilesEntity) sessionFactory.getCurrentSession().load(ProfilesEntity.class, profileId);
     //   if (null != profile) {
     //       this.sessionFactory.getCurrentSession().delete(profile);
       // }
    }
}
