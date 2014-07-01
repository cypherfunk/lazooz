package com.i2click.ml.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.i2click.ml.config.SpringMongoConfig;
import com.i2click.ml.entity.LocationEntity;

import java.util.ArrayList;
import java.util.List;


@Repository
public class LocationDaoImpl implements LocationDAO {

	ApplicationContext ctx = 
            new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    

    @Override
    public void save(LocationEntity entity) {
    	mongoOperation.save(entity);
    }
    
    @Override
    public void update(LocationEntity entity) {
    	Query searchLocationQuery = new Query(Criteria.where("profileid").is(entity.getProfileid()));
    	Update update = new Update();
    	update.set("longtitude", entity.getLongitude());
    	mongoOperation.updateFirst(searchLocationQuery,update,LocationEntity.class);
    }

    @Override
    public boolean isExist(int profileId) {
    	Query searchLocationQuery = new Query(Criteria.where("profileid").is(profileId));
    	List<LocationEntity> userLocation = mongoOperation.find(searchLocationQuery, LocationEntity.class);
        
        
        if(userLocation.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    @Override
    public LocationEntity getLEObject(int profileId) {
    	Query searchLocationQuery = new Query(Criteria.where("profileid").is(profileId));
    	List<LocationEntity> userLocations = mongoOperation.find(searchLocationQuery, LocationEntity.class);
        
        if (userLocations.isEmpty()) {
            return null;
        } else {
            return (LocationEntity) userLocations.get(0);
        }
    }
}
