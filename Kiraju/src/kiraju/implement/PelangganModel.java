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
import kiraju.interfaces.IPelanggan;
import kiraju.model.Pelanggan;
import kiraju.property.PelangganProperty;
import kiraju.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 *
 * @author arvita
 */
public class PelangganModel implements IPelanggan{
    
    private final static Logger LOGGER = Logger.getLogger(PelangganModel.class);

    @Override
    public ObservableList<PelangganProperty> getAllProp() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<PelangganProperty> dataProperty = FXCollections.observableArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Pelanggan.class);
            criteria.addOrder(Order.desc("status"));
            criteria.addOrder(Order.asc("dtStart"));
            List resultList = criteria.list();
            if(null != resultList){
                for(Object o : resultList){
                    Pelanggan pelanggan = (Pelanggan) o;
                    PelangganProperty pelangganProp = new PelangganProperty();
                    pelangganProp.setId(String.valueOf(pelanggan.getId()));
                    pelangganProp.setNama(pelanggan.getNama());
                    pelangganProp.setAlamat(pelanggan.getAlamat());
                    pelangganProp.setTelp(pelanggan.getNoTelp());
                    pelangganProp.setStatus(pelanggan.getStatus());
                    pelangganProp.setTanggal(pelanggan.getDtStart());
                    dataProperty.add(pelangganProp);
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
    public void insertOrUpdate(Pelanggan pelanggan) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(pelanggan);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        }
        session.close();
    }

    @Override
    public List<String> searchPelangganById(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<String> data = new ArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Pelanggan where status = :status and id like :id");
            query.setParameter("status", Boolean.TRUE);
            query.setParameter("id", id.toUpperCase()+"%");
            query.setMaxResults(10);
            List<Pelanggan> resultList = query.list();
            if(null != resultList) {
                for(Pelanggan pelanggan : resultList) {
                    data.add(pelanggan.getId() + " - " + pelanggan.getNama());
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return data;
    }
    
}
