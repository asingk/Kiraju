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
import kiraju.util.Choice;
import kiraju.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
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
    private final static Logger LOGGER = Logger.getLogger(JenisMenuModel.class);

    @Override
    public List<JenisMenu> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List resultList = new ArrayList();
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(JenisMenu.class);
            criteria.addOrder(Order.desc("status"));
            criteria.addOrder(Order.asc("id"));
            resultList = criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
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
//                    jenisMenuProp.setKode(jenisMenu.getCode());
                    jenisMenuProp.setNama(jenisMenu.getNama());
//                    jenisMenuProp.setDeletedFlag(jenisMenu.getDeletedFlag());
                    jenisMenuProp.setStatus(jenisMenu.getStatus());
                    dataProperty.add(jenisMenuProp);
                }
            }
        return dataProperty;
    }

    @Override
    public boolean insert(JenisMenu jenisMenu, Stage stage) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(jenisMenu);
            tx.commit();
        } 
        catch (ConstraintViolationException cve) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Nama Sudah Terpakai");
            alert.setContentText("Silahkan masukkan nama yang lain");
            alert.showAndWait();
            result = false;
        } 
        catch (JDBCException jdbce) {
            String sqlSate = jdbce.getSQLException().getNextException().getSQLState();
            if(sqlSate.equalsIgnoreCase("23505")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Salah!");
                alert.setHeaderText("nama Sudah Terpakai");
                alert.setContentText("Silahkan masukkan yang berbeda");
                alert.showAndWait();
            }
            result = false;
        }
        catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
            result = false;
        }
        finally{
            session.close();
        }
        return result;
    }

    @Override
    public boolean update(JenisMenu jenisMenu, Stage stage) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.update(jenisMenu);
            tx.commit();
        } 
        catch (ConstraintViolationException cve) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Nama Sudah Terpakai");
            alert.setContentText("Silahkan masukkan nama yang lain");
            alert.showAndWait();
            result = false;
        } 
        catch (JDBCException jdbce) {
            String sqlSate = jdbce.getSQLException().getNextException().getSQLState();
            if(sqlSate.equalsIgnoreCase("23505")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Salah!");
                alert.setHeaderText("nama Sudah Terpakai");
                alert.setContentText("Silahkan masukkan yang berbeda");
                alert.showAndWait();
            }
            result = false;
        }
        catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void delete(short id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update JenisMenu set status = :status where id = :id");
//            query.setParameter("deleted_flag", (short) 1);
            query.setParameter("status", Boolean.FALSE);
            query.setParameter("id", id);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to delete to database", e);
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
//            criteria.add(Restrictions.eq("deletedFlag", (short) 0));
            criteria.add(Restrictions.eq("status", Boolean.TRUE));
            criteria.addOrder(Order.asc("id"));
            resultList = criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public ObservableList<Choice> getAllActiveChoice() {
        ObservableList<Choice> data = FXCollections.observableArrayList();
//        data.add(new Choice(0, "Semua"));
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(JenisMenu.class);
            criteria.add(Restrictions.eq("status", Boolean.TRUE));
            criteria.addOrder(Order.asc("id"));
            List<JenisMenu> resultList = criteria.list();
//            if(null != resultList && !resultList.isEmpty()) {
                for(JenisMenu jenisMenu : resultList) {
                    data.add(new Choice(jenisMenu.getId(), jenisMenu.getNama()));
                }
//            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return data;
    }
    
}