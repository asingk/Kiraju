/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.interfaces.IMetodePembayaran;
import kiraju.model.MetodePembayaran;
import kiraju.property.MetodePembayaranProperty;
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
public class MetodePembayaranModel implements IMetodePembayaran{
    
    private final static Logger LOGGER = Logger.getLogger(MetodePembayaranModel.class);

    @Override
    public List<MetodePembayaran> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List resultList = new ArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(MetodePembayaran.class);
            criteria.addOrder(Order.desc("status"));
            criteria.addOrder(Order.asc("id"));
            resultList = criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return resultList;
    }

    @Override
    public ObservableList<String> getAllNama() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<String> dataProperty = FXCollections.observableArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(MetodePembayaran.class);
            criteria.add(Restrictions.eq("status", Boolean.TRUE));
            criteria.addOrder(Order.asc("id"));
            List<MetodePembayaran> resultList = criteria.list();
            if(null != resultList && !resultList.isEmpty()) {
                for(MetodePembayaran mp : resultList) {
                    dataProperty.add(mp.getNama());
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return dataProperty;
    }

    @Override
    public int getIdByName(String nama) {
//        System.out.println("kiraju1.implement.MetodePembayaranModel.getIdByName() nama = " + nama);
        int id = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(MetodePembayaran.class).add(Restrictions.eq("nama", nama));
            List<MetodePembayaran> obj = criteria.list();
//            for(int i = 0; i < obj.size(); i++){
                id = obj.get(0).getId();
//            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
//        System.out.println("kiraju1.implement.MetodePembayaranModel.getIdByName() id = " + id);
        return id;
    }

    @Override
    public ObservableList<MetodePembayaranProperty> getAllProperty() {
        ObservableList<MetodePembayaranProperty> dataProperty = FXCollections.observableArrayList();
        List<MetodePembayaran> resultList = getAll();
            if(null != resultList){
                for(MetodePembayaran metodePembayaran : resultList){
                    MetodePembayaranProperty property = new MetodePembayaranProperty();
                    property.setId(metodePembayaran.getId());
                    property.setNama(metodePembayaran.getNama());
                    property.setStatus(metodePembayaran.getStatus());
                    dataProperty.add(property);
                }
            }
        return dataProperty;
    }

    @Override
    public void insertOrUpdate(MetodePembayaran metodePembayaran) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(metodePembayaran);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert or update to database", e);
        }
        session.close();
    }
    
}
