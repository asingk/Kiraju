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
import kiraju.interfaces.IJenisMenu;
import kiraju.model.JenisMenu;
import kiraju.property.JenisMenuProperty;
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
public class JenisMenuModel implements IJenisMenu{
    private final static Logger logger = Logger.getLogger(JenisMenuModel.class);

    @Override
    public List<JenisMenu> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List resultList = new ArrayList();
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(JenisMenu.class);
            criteria.addOrder(Order.asc("deletedFlag"));
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
    public ObservableList<JenisMenuProperty> getAllProperty() {
        ObservableList<JenisMenuProperty> dataProperty = FXCollections.observableArrayList();
        List resultList = getAll();
            if(null != resultList){
                for(Object o : resultList){
                    JenisMenu jenisMenu = (JenisMenu) o;
                    JenisMenuProperty jenisMenuProp = new JenisMenuProperty();
                    jenisMenuProp.setId(jenisMenu.getId());
                    jenisMenuProp.setNama(jenisMenu.getNama());
                    jenisMenuProp.setDeletedFlag(jenisMenu.getDeletedFlag());
                    dataProperty.add(jenisMenuProp);
                }
            }
        return dataProperty;
    }

    @Override
    public short insert(JenisMenu jenisMenu, Stage stage) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.save(jenisMenu);
            tx.commit();
        } catch (ConstraintViolationException cve) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Nama Sudah Terpakai");
            alert.setContentText("Silahkan masukkan nama yang lain");
            alert.showAndWait();
        } catch (HibernateException e) {
            logger.error("failed to insert to database", e);
        } finally {
            session.close();
        }
        return jenisMenu.getId();
    }

    @Override
    public void update(JenisMenu jenisMenu, Stage stage) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.update(jenisMenu);
            tx.commit();
        } catch (ConstraintViolationException cve) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Nama Sudah Terpakai");
            alert.setContentText("Silahkan masukkan nama yang lain");
            alert.showAndWait();
        } catch (HibernateException e) {
            logger.error("failed to update to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(short id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update JenisMenu set deletedFlag = :deleted_flag where id = :id");
            query.setParameter("deleted_flag", (short) 1);
            query.setParameter("id", id);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to delete to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<JenisMenu> getAllActive() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List resultList = new ArrayList();
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(JenisMenu.class);
            criteria.add(Restrictions.eq("deletedFlag", (short) 0));
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
    
}