package com.i2click.ml.dao;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import com.i2click.ml.entity.LocationLogEntity;
//import java.util.List;
//import org.hibernate.Query;
//
//@Repository
//public class LocationLogDaoImpl implements LocationLogDAO {
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @Override
//    public void save(LocationLogEntity entity) {
//        this.sessionFactory.getCurrentSession().save(entity);
//    }
//    
//    @Override
//    public void update(LocationLogEntity entity) {
//        this.sessionFactory.getCurrentSession().update(entity);
//    }
//
//    @Override
//    public boolean isExist(int profileId) {
//        String queryString = "FROM LocationLogEntity WHERE profileid = :profileid";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
//        query.setParameter("profileid", profileId+"");
//        List list = query.list();
//        
//        if(list.isEmpty()){
//            return false;
//        }else{
//            return true;
//        }
//    }
//    
//    @Override
//    public LocationLogEntity getLEObject(int profileId) {
//        String queryString = "FROM LocationLogEntity WHERE profileid = :profileid";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
//        query.setParameter("profileid", profileId+"");
//        List list = query.list();
//        
//        if (list.isEmpty()) {
//            return null;
//        } else {
//            return (LocationLogEntity) list.get(0);
//        }
//    }
//}
