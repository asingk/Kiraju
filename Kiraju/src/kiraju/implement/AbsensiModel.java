/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.interfaces.IAbsensi;
import kiraju.model.Absensi;
import kiraju.model.Users;
import kiraju.property.AbsensiProperty;
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
public class AbsensiModel implements IAbsensi{
    
    private final static Logger LOGGER = Logger.getLogger(AbsensiModel.class);

    @Override
    public ObservableList<AbsensiProperty> getByDate(LocalDate localDate) {
        ObservableList<AbsensiProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Absensi a join a.userId u where date(masuk) = :masuk order by a.masuk");
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            query.setParameter("masuk", date);
            List<Object[]> rows = query.list();
            if(rows != null){
                for(Object[] row : rows){
                    Absensi absensi = (Absensi) row[0];
                    Users users = (Users) row[1];
                    AbsensiProperty property = new AbsensiProperty();
                    property.setNama(users.getNama());
                    property.setMasuk(absensi.getMasuk());
                    if(null != absensi.getKeluar()) {
                        property.setKeluar(absensi.getKeluar());
                    }
                    if(null != absensi.getTotal()) {
                        property.setTotal(absensi.getTotal());
                    }
                    
                    dataProperty.add(property);
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
    public void insert(Users users) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            
            //insert absensi
            Absensi absensi = new Absensi();
            absensi.setUserId(users);
            absensi.setMasuk(new Date());
            session.save(absensi);
            
            //update user absensi status
            users.setStatusAbsensi(Boolean.TRUE);
            session.update(users);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        }
        session.close();
    }

    @Override
    public void update(Users users) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            //select absensi
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Absensi.class);
            criteria.add(Restrictions.eq("userId", users));
            criteria.addOrder(Order.desc("masuk"));
            criteria.setMaxResults(1);
            Absensi absensi = (Absensi) criteria.list().get(0);
            
            //update absensi
            absensi.setKeluar(new Date());
            long diff = absensi.getKeluar().getTime() - absensi.getMasuk().getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            String total = diffHours + ":" + diffMinutes + ":" + diffSeconds;
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            absensi.setTotal(format.parse(total));
            session.update(absensi);
            
            //update user absensi status
            users.setStatusAbsensi(Boolean.FALSE);
            session.update(users);
            
            //commit
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        } catch (ParseException ex) {
            LOGGER.error("failed to format date", ex);
        }
        session.close();
    }

    @Override
    public List<AbsensiProperty> getLaporan(LocalDate masuk, LocalDate keluar) {
        List<AbsensiProperty> resultList = new ArrayList();
        Date dateDari = Date.from(masuk.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateSampai = Date.from(keluar.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Absensi.class);
            criteria.add(Restrictions.ge("masuk", dateDari));
            criteria.add(Restrictions.lt("keluar", dateSampai));
            criteria.addOrder(Order.asc("masuk"));
            List<Absensi> list = criteria.list();
            for(Absensi absensi : list) {
                AbsensiProperty property = new AbsensiProperty();
                property.setNama(absensi.getUserId().getNama());
                property.setMasuk(absensi.getMasuk());
                property.setKeluar(absensi.getKeluar());
                property.setTotal(absensi.getTotal());
                resultList.add(property);
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return resultList;
    }
    
}
