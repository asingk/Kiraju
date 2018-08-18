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
import kiraju.interfaces.IPemasok;
import kiraju.model.Pemasok;
import kiraju.property.PemasokProperty;
import kiraju.util.Choice;
import kiraju.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author arvita
 */
public class PemasokModel implements IPemasok{
    
    private final static Logger LOGGER = Logger.getLogger(PemasokModel.class);

    @Override
    public ObservableList<PemasokProperty> getAllProp() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<PemasokProperty> dataProperty = FXCollections.observableArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Pemasok.class);
            criteria.addOrder(Order.desc("status"));
            criteria.addOrder(Order.asc("id"));
            List resultList = criteria.list();
            if(null != resultList){
                for(Object o : resultList){
                    Pemasok pemasok = (Pemasok) o;
                    PemasokProperty pemasokProperty = new PemasokProperty();
                    pemasokProperty.setId(pemasok.getId());
                    pemasokProperty.setNama(pemasok.getNama());
                    pemasokProperty.setAlamat(pemasok.getAlamat());
                    pemasokProperty.setTelp(pemasok.getTelp());
                    pemasokProperty.setEmail(pemasok.getEmail());
                    pemasokProperty.setStatus(pemasok.getStatus());
                    dataProperty.add(pemasokProperty);
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
    public void saveOrUpdate(Pemasok pemasok) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(pemasok);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert or update to database", e);
        }
        session.close();
    }

    @Override
    public List<Choice> searchPemasokByNama(String nama) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Choice> data = new ArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Pemasok where lower(nama) like :nama and status is true");
            query.setParameter("nama", "%"+nama.toLowerCase()+"%");
            query.setMaxResults(10);
            List resultList = query.list();
            if(null != resultList){
                for(Object o : resultList){
                    Pemasok pemasok = (Pemasok) o;
                    data.add(new Choice(pemasok.getId(), pemasok.getNama()));
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
    public List<Choice> getAllOrderByName() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Choice> data = new ArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Pemasok.class);
            criteria.add(Restrictions.eq("status", true));
            criteria.addOrder(Order.asc("nama"));
            List resultList = criteria.list();
            if(null != resultList){
                for(Object o : resultList){
                    Pemasok pemasok = (Pemasok) o;
                    data.add(new Choice(pemasok.getId(), pemasok.getNama()));
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
