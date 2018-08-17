/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.interfaces.IDiskon;
import kiraju.model.Diskon;
import kiraju.property.DiskonPajakProperty;
import kiraju.util.Choice;
import kiraju.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author arvita
 */
public class DiskonModel implements IDiskon{
    
    private final static Logger LOGGER = Logger.getLogger(DiskonModel.class);

    @Override
    public ObservableList<DiskonPajakProperty> getAllProperty() {
        ObservableList<DiskonPajakProperty> dataProp = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Diskon.class);
            criteria.addOrder(Order.desc("status"));
            criteria.addOrder(Order.asc("id"));
            List<Diskon> resultList = criteria.list();
            if(null != resultList && !resultList.isEmpty()) {
                for(Diskon diskon : resultList) {
                    DiskonPajakProperty property = new DiskonPajakProperty();
                    property.setId(diskon.getId());
                    property.setNama(diskon.getNama());
                    property.setTipe(diskon.getTipe());
                    property.setBilangan(diskon.getBilangan());
                    property.setStatus(diskon.getStatus());
                    dataProp.add(property);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return dataProp;
    }

    @Override
    public void saveOrupdate(Diskon diskon) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(diskon);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert or update to database", e);
        }
        session.close();
    }

    @Override
    public ObservableList<Choice> getAllActive() {
        ObservableList<Choice> data = FXCollections.observableArrayList();
        data.add(new Choice(0, ""));
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Diskon.class);
            criteria.add(Restrictions.eq("status", Boolean.TRUE));
            criteria.addOrder(Order.asc("id"));
            List<Diskon> resultList = criteria.list();
            if(null != resultList && !resultList.isEmpty()) {
                for(Diskon diskon : resultList) {
                    data.add(new Choice(diskon.getId(), diskon.getNama()));
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return data;
    }

    @Override
    public Diskon getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Diskon diskon = new Diskon();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Diskon.class);
            criteria.add(Restrictions.eq("id", id));
            List<Diskon> resultList = criteria.list();
            if(null != resultList && !resultList.isEmpty()) {
                diskon = (Diskon) resultList.get(0);
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return diskon;
    }
    
}
