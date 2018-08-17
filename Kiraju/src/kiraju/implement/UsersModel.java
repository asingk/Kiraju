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
public class UsersModel implements IUsers {
    
    private final static Logger LOGGER = Logger.getLogger(UsersModel.class);

    @Override
    public ObservableList<UsersProperty> getAll() {
        ObservableList<UsersProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            List<Object[]> obj = session.createQuery("from Users u join u.posisiId p where u.id not in('1','3') order by u.status desc, p.id, u.id").list();
            if(obj != null){
                obj.stream().map((result) -> (Users) result[0]).map((users) -> {
                    UsersProperty usersProperty = new UsersProperty();
                    usersProperty.setId(users.getId());
                    usersProperty.setNama(users.getNama());
                    usersProperty.setUserName(users.getUsername());
                    usersProperty.setPassword(users.getPassword());
                    usersProperty.setPosisiId(users.getPosisiId().getId());
                    usersProperty.setPosisiNama(users.getPosisiId().getNama());
                    usersProperty.setStatus(users.getStatus());
                    return usersProperty;
                }).forEachOrdered((usersProperty) -> {
                    dataProperty.add(usersProperty);
                });
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return dataProperty;
    }

    @Override
    public boolean insert(Users users, Stage stage) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.save(users);
            tx.commit();
        } 
        catch (ConstraintViolationException cve) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("ID dan/atau username Sudah Terpakai");
            alert.setContentText("Silahkan masukkan yang berbeda");
            alert.showAndWait();
            result = false;
        } 
        catch (JDBCException jdbce) {
            String sqlSate = jdbce.getSQLException().getNextException().getSQLState();
            if(sqlSate.equalsIgnoreCase("23505")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Salah!");
                alert.setHeaderText("ID dan/atau username Sudah Terpakai");
                alert.setContentText("Silahkan masukkan yang berbeda");
                alert.showAndWait();
            }
            result = false;
        }
        catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
            result = false;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public boolean update(Users users, Stage stage) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update Users set nama = :nama, username = :username, password = :password, posisiId = :posisiId, status = :status where id = :id");
            query.setParameter("nama", users.getNama());
            query.setParameter("username", users.getUsername());
            query.setParameter("password", users.getPassword());
            if(users.getPosisiId().getId() > 0){
                query.setParameter("posisiId", users.getPosisiId());
            }else{
                query.setParameter("posisiId", new Posisi((short) 2));
            }
            query.setParameter("id", users.getId());
            query.setParameter("status", users.getStatus());
            query.executeUpdate();
            tx.commit();
        } 
        catch (ConstraintViolationException cve) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Username Sudah Terpakai");
            alert.setContentText("Silahkan masukkan Username yang lain");
            alert.showAndWait();
            result = false;
        } 
        catch (JDBCException jdbce) {
            String sqlSate = jdbce.getSQLException().getNextException().getSQLState();
            if(sqlSate.equalsIgnoreCase("23505")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Salah!");
                alert.setHeaderText("Username Sudah Terpakai");
                alert.setContentText("Silahkan masukkan Username lain");
                alert.showAndWait();
            }
            result = false;
        }
        catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
            result = false;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void delete(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update Users set status = :status where id = :id");
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
    public Users selectByUsername(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Users users = new Users();
        try{
           tx = session.beginTransaction();
           Criteria cr = session.createCriteria(Users.class);
           cr.add(Restrictions.eq("username", userName));
           cr.add(Restrictions.eq("status", Boolean.TRUE));
           cr.add(Restrictions.ne("posisiId", new Posisi(6)));
           List<Users> usersList = cr.list(); 

           for (Iterator iterator = usersList.iterator(); iterator.hasNext();){
              users = (Users) iterator.next(); 
           }
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           LOGGER.error("failed to select to database", e);
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
            criteria.add(Restrictions.gt("id", "1"));
            criteria.add(Restrictions.eq("status", Boolean.TRUE));
            criteria.add(Restrictions.ne("posisiId", new Posisi(6)));
            criteria.addOrder(Order.desc("posisiId"));
            usersList = criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return usersList;
    }

    @Override
    public Users selectByUsernameIncludeStaff(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Users users = new Users();
        try{
           tx = session.beginTransaction();
           Criteria cr = session.createCriteria(Users.class);
           cr.add(Restrictions.eq("username", userName));
           cr.add(Restrictions.eq("status", Boolean.TRUE));
           List<Users> usersList = cr.list(); 

           for (Iterator iterator = usersList.iterator(); iterator.hasNext();){
              users = (Users) iterator.next(); 
           }
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           LOGGER.error("failed to select to database", e);
        }finally {
           session.close(); 
        }
        return users;
    }
    
}
