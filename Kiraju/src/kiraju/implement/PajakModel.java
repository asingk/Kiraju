/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.interfaces.IPajak;
import kiraju.model.Pajak;
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
public class PajakModel implements IPajak{
    
    private final static Logger LOGGER = Logger.getLogger(PajakModel.class);

    @Override
    public ObservableList<DiskonPajakProperty> getAllProperty() {
        ObservableList<DiskonPajakProperty> dataProp = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Pajak.class);
            criteria.addOrder(Order.desc("status"));
            criteria.addOrder(Order.asc("id"));
            List<Pajak> resultList = criteria.list();
            if(null != resultList && !resultList.isEmpty()) {
                for(Pajak pajak : resultList) {
                    DiskonPajakProperty property = new DiskonPajakProperty();
                    property.setId(pajak.getId());
                    property.setNama(pajak.getNama());
                    property.setTipe(pajak.getTipe());
                    property.setBilangan(pajak.getBilangan());
                    property.setStatus(pajak.getStatus());
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
    public void saveOrupdate(Pajak pajak) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(pajak);
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
            Criteria criteria = session.createCriteria(Pajak.class);
            criteria.add(Restrictions.eq("status", Boolean.TRUE));
            criteria.addOrder(Order.asc("id"));
            List<Pajak> resultList = criteria.list();
            if(null != resultList && !resultList.isEmpty()) {
                for(Pajak pajak : resultList) {
                    data.add(new Choice(pajak.getId(), pajak.getNama()));
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
    public Pajak getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Pajak pajak = new Pajak();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Pajak.class);
            criteria.add(Restrictions.eq("id", id));
            List<Pajak> resultList = criteria.list();
            if(null != resultList && !resultList.isEmpty()) {
                pajak = (Pajak) resultList.get(0);
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return pajak;
    }
    
}
