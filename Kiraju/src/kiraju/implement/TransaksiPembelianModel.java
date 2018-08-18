/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.interfaces.ITransaksiPembelian;
import kiraju.model.Pemasok;
import kiraju.model.TransaksiPembelian;
import kiraju.property.TransaksiPembelianProperty;
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
public class TransaksiPembelianModel implements ITransaksiPembelian{
    
    private final static Logger LOGGER = Logger.getLogger(TransaksiPembelianModel.class);

    @Override
    public ObservableList<TransaksiPembelianProperty> getAllProp(int bulan) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<TransaksiPembelianProperty> dataProperty = FXCollections.observableArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from TransaksiPembelian tp join tp.pemasokId p where month(tp.tanggal) = :bulan order by tp.id desc");
            query.setParameter("bulan", bulan);
            List<Object[]> obj = query.list();
            if(null != obj){
                for(Object[] row : obj){
                    TransaksiPembelian pembelian = (TransaksiPembelian) row[0];
                    Pemasok pemasok = (Pemasok) row[1];
                    TransaksiPembelianProperty property = new TransaksiPembelianProperty();
                    property.setId(pembelian.getId());
                    property.setTanggal(pembelian.getTanggal());
                    property.setPemasokNama(pemasok.getNama());
                    property.setTotal(pembelian.getTotal());
                    property.setPemasokId(pemasok.getId());
                    property.setStatus(pembelian.getStatus());
                    property.setIsLunas(pembelian.getIsLunas());
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
    public int insertOrUpdate(TransaksiPembelian pembelian) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(pembelian);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        } finally {
            session.close();
        }
        return pembelian.getId();
    }

    @Override
    public void remove(TransaksiPembelian pembelian) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.delete(pembelian);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        } finally {
            session.close();
        }
    }
    
}
