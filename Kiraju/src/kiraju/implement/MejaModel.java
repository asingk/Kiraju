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
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import kiraju.interfaces.IMeja;
import kiraju.model.Meja;
import kiraju.property.MejaProperty;
import kiraju.util.CommonConstant;
import kiraju.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author arvita
 */
public class MejaModel implements IMeja{
    
    private final static Logger logger = Logger.getLogger(MejaModel.class);

    @Override
    public List<Meja> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<Meja> resultList = new ArrayList();
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Meja.class);
            criteria.add(Restrictions.gt("id", (short) 0));
            criteria.addOrder(Order.asc("id"));
            resultList = criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public void update(Meja meja) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("update Meja set status = :status where id = :id");
            query.setParameter("status", meja.getStatus());
            query.setParameter("id", meja.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to update to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public ObservableList<MejaProperty> getAllProperty() {
        ObservableList<MejaProperty> dataProperty = FXCollections.observableArrayList();
        List<Meja> resultList = getAll();
        for(Meja meja : resultList){
            MejaProperty mejaProperty = new MejaProperty();
            mejaProperty.setId(meja.getId());
            mejaProperty.setNomor(meja.getNomor());
            switch(meja.getStatus()) {
                case CommonConstant.MEJA_TERSEDIA:
                    mejaProperty.setStatus(CommonConstant.ADMIN_MEJA_ACTIVE);
                    break;
                case CommonConstant.MEJA_TERISI:
                    mejaProperty.setStatus(CommonConstant.ADMIN_MEJA_ACTIVE);
                    break;
                case CommonConstant.MEJA_DISABLE:
                    mejaProperty.setStatus(CommonConstant.ADMIN_MEJA_NON_ACTIVE);
                    break;
                case CommonConstant.MEJA_INVISIBLE:
                    mejaProperty.setStatus(CommonConstant.ADMIN_MEJA_HIDDEN);
                    break;
                default:
                    break;
            }
            dataProperty.add(mejaProperty);
        }
        return dataProperty;
    }

    @Override
    public boolean updateAdminEdit(Meja meja, Stage stage){
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.update(meja);
            tx.commit();
        } catch (ConstraintViolationException cve) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Nomor Sudah Terpakai");
            alert.setContentText("Silahkan masukkan nomor yang lain");
            alert.showAndWait();
            result = false;
        } catch (HibernateException he) {
            logger.error("failed to update to database", he);
            result = false;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public String getNomorById(short id) {
        String namaMeja = "";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Meja.class);
            criteria.add(Restrictions.eq("id", id));
            List<Meja> resultList = criteria.list();
            for(Meja meja : resultList){
                namaMeja = meja.getNomor();
            }
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return namaMeja;
    }

    @Override
    public List<String> getAvailableForPindah(short id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List<String> mejaList = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Meja.class);
            criteria.add(Restrictions.gt("id", (short) 0));
            criteria.add(Restrictions.eq("status", (short) CommonConstant.MEJA_TERSEDIA));
            criteria.add(Restrictions.ne("id", id));
            criteria.addOrder(Order.asc("id"));
            List<Meja> resultList = criteria.list();
            resultList.forEach((meja) -> {
                mejaList.add(meja.getNomor());
            });
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return mejaList;
    }

    @Override
    public short getIdByNomor(String nomor) {
        short id = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Meja.class);
            criteria.add(Restrictions.eq("nomor", nomor));
            List<Meja> resultList = criteria.list();
            for(Meja meja : resultList){
                id = meja.getId();
            }
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return id;
    }
    
}
