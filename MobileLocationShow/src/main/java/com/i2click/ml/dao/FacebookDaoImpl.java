package com.i2click.ml.dao;

//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import com.i2click.ml.entity.FacebookEntity;
//import java.util.List;
//import org.hibernate.Query;
//
//@Repository
//public class FacebookDaoImpl implements FacebookDAO {
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @Override
//    public void save(FacebookEntity facebookEntity) {
//        this.sessionFactory.getCurrentSession().save(facebookEntity);
//    }
//    
//    @Override
//    public void update(FacebookEntity facebookEntity) {
//        this.sessionFactory.getCurrentSession().update(facebookEntity);
//    }
//
//    @Override
//    public boolean isExist(int profileId) {
//        String queryString = "FROM FacebookEntity WHERE profileid = :profileid";
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
//    public FacebookEntity getFBObject(int profileId) {
//        String queryString = "FROM FacebookEntity WHERE profileid = :profileid";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
//        query.setParameter("profileid", profileId+"");
//        List list = query.list();
//        
//        if (list.isEmpty()) {
//            return null;
//        } else {
//            return (FacebookEntity) list.get(0);
//        }
//    }
//}
