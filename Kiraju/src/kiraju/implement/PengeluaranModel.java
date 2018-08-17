/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.interfaces.IPengeluaran;
import kiraju.model.Pengeluaran;
import kiraju.property.PengeluaranProperty;
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
public class PengeluaranModel implements IPengeluaran {
    
    private final static Logger logger = Logger.getLogger(PengeluaranModel.class);

    @Override
    public ObservableList<PengeluaranProperty> getByDate(LocalDate localDate) {
        ObservableList<PengeluaranProperty> resultObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Pengeluaran where dtOnly = :dtOnly");
            query.setDate("dtOnly", Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            List<Pengeluaran> obj = query.list();
            if(obj != null){
                for(Pengeluaran pengeluaran : obj){
                    PengeluaranProperty pengeluaranProperty = new PengeluaranProperty();
                    pengeluaranProperty.setId(pengeluaran.getId());
                    pengeluaranProperty.setNama(pengeluaran.getNama());
                    pengeluaranProperty.setHarga(pengeluaran.getHarga());
                    resultObsList.add(pengeluaranProperty);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return resultObsList;
    }

    @Override
    public int insert(Pengeluaran pengeluaran) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.save(pengeluaran);
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to insert to database", e);
        } finally {
            session.close();
        }
        return pengeluaran.getId();
    }

    @Override
    public void update(Pengeluaran pengeluaran) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("update Pengeluaran set nama = :nama, harga = :harga where id = :id");
            query.setParameter("nama", pengeluaran.getNama());
            query.setParameter("harga", pengeluaran.getHarga());
            query.setParameter("id", pengeluaran.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to update to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        Pengeluaran pengeluaran = new Pengeluaran(id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.delete(pengeluaran);
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to delete to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List getChartByBulan(int bulan) {
        List resultList = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("select DAY(dtOnly), sum(harga) from Pengeluaran where MONTH(dtOnly) = :month and YEAR(dtOnly) = :year group by dtOnly");
            query.setInteger("month", bulan);
            query.setInteger("year", LocalDate.now().getYear());
            resultList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public List getChartByTahun(int tahun) {
        List resultList = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("select MONTH(dtOnly), sum(harga) from Pengeluaran where YEAR(dtOnly) = :year group by MONTH(dtOnly)");
            query.setInteger("year", tahun);
            resultList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return resultList;
    }
    
}
