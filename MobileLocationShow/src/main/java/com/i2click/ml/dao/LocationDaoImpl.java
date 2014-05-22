package com.i2click.ml.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.i2click.ml.entity.LocationEntity;
import java.util.List;
import org.hibernate.Query;

@Repository
public class LocationDaoImpl implements LocationDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(LocationEntity entity) {
        this.sessionFactory.getCurrentSession().save(entity);
    }
    
    @Override
    public void update(LocationEntity entity) {
        this.sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public boolean isExist(int profileId) {
        String queryString = "FROM LocationEntity WHERE profileid = :profileid";
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        query.setParameter("profileid", profileId+"");
        List list = query.list();
        
        if(list.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    @Override
    public LocationEntity getLEObject(int profileId) {
        String queryString = "FROM LocationEntity WHERE profileid = :profileid";
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        query.setParameter("profileid", profileId+"");
        List list = query.list();
        
        if (list.isEmpty()) {
            return null;
        } else {
            return (LocationEntity) list.get(0);
        }
    }
}
