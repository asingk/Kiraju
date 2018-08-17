/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.interfaces.IStokOpname;
import kiraju.model.StokOpname;
import kiraju.model.Users;
import kiraju.property.StokOpnameProperty;
import kiraju.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author arvita
 */
public class StokOpnameModel implements IStokOpname{
    
    private final static Logger LOGGER = Logger.getLogger(StokOpnameModel.class);

    @Override
    public ObservableList<Integer> getYear() {
        ObservableList<Integer> resultObsList = FXCollections.observableArrayList(LocalDate.now().getYear());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("select distinct YEAR(tanggal) from StokOpname where YEAR(tanggal) is not :thisYear order by YEAR(tanggal) desc");
            query.setParameter("thisYear", LocalDate.now().getYear());
            List<Integer> resultList = query.list();
            resultObsList.addAll(resultList);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return resultObsList;
    }

    @Override
    public ObservableList<StokOpnameProperty> getByBulan(int tahun, int bulan) {
        ObservableList<StokOpnameProperty> resultObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from StokOpname s join s.userId u where YEAR(tanggal) = :thisYear and MONTH(tanggal) = :thisMonth order by s.id");
            query.setParameter("thisYear", tahun);
            query.setParameter("thisMonth", bulan);
            List<Object[]> resultList = query.list();
            if(null != resultList && !resultList.isEmpty()) {
                for(Object[] obj : resultList){
                    StokOpname stokOpname = (StokOpname) obj[0];
                    Users users = (Users) obj[1];
                    StokOpnameProperty stokOpnameProperty = new StokOpnameProperty();
                    stokOpnameProperty.setId(stokOpname.getId());
                    stokOpnameProperty.setTanggal(stokOpname.getTanggal());
                    stokOpnameProperty.setNama(stokOpname.getNama());
                    stokOpnameProperty.setStatus(stokOpname.getStatus());
                    stokOpnameProperty.setUserNama(users.getNama());
                    stokOpnameProperty.setUserId(users.getId());
                    resultObsList.add(stokOpnameProperty);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return resultObsList;
    }

    @Override
    public int insert(StokOpname stokOpname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(stokOpname);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        }
        session.close();
        return stokOpname.getId();
    }

    @Override
    public void update(StokOpname stokOpname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("update StokOpname set nama = :nama where id = :id");
//            query.setParameter("tanggal", stokOpname.getTanggal());
//            query.setParameter("userId", stokOpname.getUserId());
            query.setParameter("nama", stokOpname.getNama());
//            query.setParameter("status", stokOpname.getStatus());
            query.setParameter("id", stokOpname.getId());
            query.executeUpdate();
//            session.update(stokOpname);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }

    @Override
    public void delete(StokOpname stokOpname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.delete(stokOpname);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }

    @Override
    public void updateStatus(StokOpname stokOpname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("update StokOpname set status = :status where id = :id");
            query.setParameter("status", stokOpname.getStatus());
            query.setParameter("id", stokOpname.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }
    
}
