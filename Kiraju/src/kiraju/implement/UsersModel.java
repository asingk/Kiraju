/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import kiraju.interfaces.IUsers;
import kiraju.model.Posisi;
import kiraju.model.Users;
import kiraju.property.UsersProperty;
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
public class UsersModel implements IUsers {
    
    private final static Logger logger = Logger.getLogger(UsersModel.class);

    @Override
    public ObservableList<UsersProperty> getAll() {
        ObservableList<UsersProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            List<Object[]> obj = session.createQuery("from Users u join u.posisiId p where u.id not in(1,3) order by u.deletedFlag, p.id, u.id").list();
            if(obj != null){
                obj.stream().map((result) -> (Users) result[0]).map((users) -> {
                    UsersProperty usersProperty = new UsersProperty();
                    usersProperty.setId(users.getId());
                    usersProperty.setNama(users.getNama());
                    usersProperty.setUserName(users.getUsername());
                    usersProperty.setPassword(users.getPassword());
                    usersProperty.setPosisiId(users.getPosisiId().getId());
                    usersProperty.setPosisiNama(users.getPosisiId().getNama());
                    usersProperty.setDeletedFlag(users.getDeletedFlag());
                    return usersProperty;
                }).forEachOrdered((usersProperty) -> {
                    dataProperty.add(usersProperty);
                });
            }
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return dataProperty;
    }

    @Override
    public short insert(Users users, Stage stage) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.save(users);
            tx.commit();
        } catch (ConstraintViolationException cve) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Username Sudah Terpakai");
            alert.setContentText("Silahkan masukkan Username yang lain");
            alert.showAndWait();
        } catch (HibernateException e) {
            logger.error("failed to insert to database", e);
        } finally {
            session.close();
        }
        return users.getId();
    }

    @Override
    @SuppressWarnings("empty-statement")
    public boolean update(Users users, Stage stage) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update Users set nama = :nama, username = :username, password = :password, posisiId = :posisiId, deletedFlag = :deletedFlag where id = :id");
            query.setParameter("nama", users.getNama());
            query.setParameter("username", users.getUsername());
            query.setParameter("password", users.getPassword());
            if(users.getPosisiId().getId() > 0){
                query.setParameter("posisiId", users.getPosisiId());
            }else{
                query.setParameter("posisiId", new Posisi((short) 2));
            }
            query.setParameter("id", users.getId());
            query.setParameter("deletedFlag", users.getDeletedFlag());
            query.executeUpdate();
            tx.commit();
        } catch (ConstraintViolationException cve) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Username Sudah Terpakai");
            alert.setContentText("Silahkan masukkan Username yang lain");
            alert.showAndWait();
            result = false;
        } catch (HibernateException e) {
            logger.error("failed to update to database", e);
            result = false;
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
            Query query =  session.createQuery("update Users set deletedFlag = :deleted_flag where id = :id");
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
    public Users selectByUsername(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Users users = new Users();
        try{
           tx = session.beginTransaction();
           Criteria cr = session.createCriteria(Users.class);
           cr.add(Restrictions.eq("username", userName));
           List<Users> usersList = cr.list(); 

           for (Iterator iterator = usersList.iterator(); iterator.hasNext();){
              users = (Users) iterator.next(); 
           }
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           logger.error("failed to select to database", e);
        }finally {
           session.close(); 
        }
        return users;
    }

    @Override
    public List<Users> getAllWithin99() {
        List<Users> usersList = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Users.class);
            criteria.add(Restrictions.gt("id", (short) 1));
            criteria.addOrder(Order.desc("posisiId"));
            usersList = criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return usersList;
    }
    
}
