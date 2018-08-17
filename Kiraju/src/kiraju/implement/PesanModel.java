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
import kiraju.interfaces.IPesan;
import kiraju.model.Menu;
import kiraju.model.Pesan;
import kiraju.model.Transaksi;
import kiraju.property.PesanProperty;
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
public class PesanModel implements IPesan{
    
    private final static Logger LOGGER = Logger.getLogger(PesanModel.class);

    @Override
    public List<Pesan> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        List resultList = new ArrayList();
        try {
            tx = session.beginTransaction();
            resultList = session.createQuery("from Pesan").list();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public int insert(PesanProperty pesanProperty) {
        Pesan pesan = new Pesan();
        pesan.setJumlah(Short.parseShort(pesanProperty.getJumlah()));
        Transaksi transaksi = new Transaksi();
        transaksi.setId(pesanProperty.getTransaksiId());
        pesan.setTransaksiId(transaksi);
        Menu menu = new Menu();
        menu.setId(pesanProperty.getMenuId());
        pesan.setMenuId(menu);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.save(pesan);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        } finally {
            session.close();
        }
        return pesan.getId();
    }

    @Override
    public void update(PesanProperty pesanProperty) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update Pesan set jumlah = :jumlah where id = :id");
            query.setParameter("jumlah", Short.parseShort(pesanProperty.getJumlah()) );
            query.setParameter("id", pesanProperty.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public ObservableList<PesanProperty> getDetailByTransaksiId(int transaksiId) {
        ObservableList<PesanProperty> menuMejaObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            List<Object[]> obj = session.createQuery("from Pesan p join p.transaksiId t join p.menuId m where p.transaksiId = "+transaksiId+" and p.jumlah is not 0 order by m.jenis, p.menuId").list();
            if(null != obj){
                for(Object[] row : obj){
                    PesanProperty pesanProperty = new PesanProperty();
                    Pesan pesan = (Pesan) row[0];
                    Transaksi transaksi = (Transaksi) row[1];
                    Menu menu = (Menu) row[2];
                    pesanProperty.setId(pesan.getId());
                    pesanProperty.setTransaksiId(transaksiId);
                    pesanProperty.setJumlah(pesan.getJumlah().toString());
                    pesanProperty.setNama(menu.getNama());
                    pesanProperty.setHarga(menu.getHarga());
                    pesanProperty.setTotalHarga(menu.getHarga() * pesan.getJumlah());
                    pesanProperty.setNamaPemesan(transaksi.getNamaPemesan());
                    pesanProperty.setMejaId(transaksi.getMejaId().getId());
                    menuMejaObsList.add(pesanProperty);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return menuMejaObsList;
    }

    @Override
    public void deleteByTransaksiId(int transaksiId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("delete from Pesan where transaksiId = :transaksiId");
            Transaksi transaksi = new Transaksi();
            transaksi.setId(transaksiId);
            query.setParameter("transaksiId", transaksi);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to delete to database", e);
        } finally {
            session.close();
        }
    }
    
}
