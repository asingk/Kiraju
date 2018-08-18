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
import kiraju.model.Diskon;
//import kiraju.model.Menu;
import kiraju.model.MenuItem;
import kiraju.model.Pajak;
import kiraju.model.Pelanggan;
import kiraju.model.Pesan;
import kiraju.model.Transaksi;
import kiraju.property.PesanProperty;
import kiraju.util.CommonConstant;
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
    public int insert(Pesan pesan) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.save(pesan);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        }
        session.close();
        return pesan.getId();
    }

    @Override
    public void update(Pesan pesan) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            //20171222 - kiraju3
//            String hql = "update Pesan set jumlah = :jumlah, modal = :modal, untung = :untung, tambahan = :tambahan where id = :id";
            String hql = "update Pesan set jumlah = :jumlah, harga = :harga where id = :id";
            Query query =  session.createQuery(hql);
            query.setParameter("jumlah", pesan.getJumlah());
            //20171222 - kiraju3
//            query.setParameter("modal", pesan.getModal());
//            query.setParameter("untung", pesan.getUntung());
//            query.setParameter("tambahan", pesan.getTambahan());
            query.setParameter("harga", pesan.getHarga());
            query.setParameter("id", pesan.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }

    @Override
    public ObservableList<PesanProperty> getDetailByTransaksiId(int transaksiId) {
        ObservableList<PesanProperty> menuMejaObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Pesan p join p.transaksiId t left join t.diskonId d left join t.pajakId pj join p.menuItemCode mi where p.transaksiId = :transaksiId order by p.id");
            Transaksi transaksiBefore = new Transaksi();
            transaksiBefore.setId(transaksiId);
            query.setParameter("transaksiId", transaksiBefore);
            List<Object[]> obj = query.list();
            if(null != obj){
                for(Object[] row : obj){
                    PesanProperty pesanProperty = new PesanProperty();
                    Pesan pesan = (Pesan) row[0];
                    Transaksi transaksi = (Transaksi) row[1];
                    MenuItem menuItem = (MenuItem) row[4];
//                    Menu menu = (Menu) row[5];
                    Diskon diskon = (Diskon) row[2];
                    Pajak pajak = (Pajak) row[3];
                    pesanProperty.setId(pesan.getId());
                    pesanProperty.setTransaksiId(transaksiId);
                    pesanProperty.setJumlah(pesan.getJumlah());
                    pesanProperty.setMenuItemNama(menuItem.getNama());
                    pesanProperty.setHarga(menuItem.getHargaTotal());
                    pesanProperty.setTotalHarga(menuItem.getHargaTotal() * pesan.getJumlah());
//                    pesanProperty.setNama(menu.getNama() + " " + menuItem.getNama());
                    pesanProperty.setNama(menuItem.getNama());
                    pesanProperty.setCode(menuItem.getCode());
//                    pesanProperty.setMenuId(menu.getId());
//                    pesanProperty.setMenuNama(menu.getNama());
                    pesanProperty.setNamaPemesan(transaksi.getNamaPemesan());
                    if(null != diskon){
                        pesanProperty.setDiskonId(diskon.getId());
                        pesanProperty.setDiskonNama(diskon.getNama());
                    }
                    if(null != pajak){
                        pesanProperty.setPajakId(pajak.getId());
                        pesanProperty.setPajakNama(pajak.getNama());
                    }
//                    pesanProperty.setTotalModal(pesan.getModal() * pesan.getJumlah());
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

    @Override
    public void deleteById(Pesan pesan) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.delete(pesan);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to delete to database", e);
        }
        session.close();
    }

    @Override
    public List<Pesan> getPieChartByPelanggan(Pelanggan pelanggan) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List resultList = new ArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("select m.nama, sum(p.jumlah) as total from Pesan p join p.transaksiId t join p.menuItemCode mi join mi.menuId m where t.pelangganId = :pelangganId and t.status = :status group by m.id, m.nama");
            query.setParameter("pelangganId", pelanggan);
            query.setParameter("status", CommonConstant.TRANSAKSI_BAYAR);
            resultList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return resultList;
    }

    @Override
    public List<Object[]> getByMenuItemAndTransaksi(MenuItem menuItem, Transaksi transaksi) {
        List<Object[]> resultList = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Pesan p join p.menuItemCode mi where p.transaksiId = :transaksi and mi.code = :menuItemCode");
            query.setParameter("transaksi", transaksi.getId());
            query.setParameter("menuItemCode", menuItem.getCode());
            resultList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return resultList;
    }

    @Override
    public void insertList(ObservableList<PesanProperty> data, int transaksiId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            for(int i=0; i<data.size(); i++) {
                Pesan pesan = new Pesan();
                pesan.setProduk(data.get(i).getProduk());
                pesan.setJumlah(Integer.valueOf(data.get(i).getBanyak()));
                pesan.setHarga(Integer.valueOf(data.get(i).getHargaJual()));
                pesan.setTransaksiId(transaksiId);
                session.save(pesan);
                if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert or update to database", e);
        }
        session.close();
    }

    @Override
    public ObservableList<PesanProperty> getDetailByTransaksiIdJual(int transaksiId) {
        ObservableList<PesanProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Pesan p where p.transaksiId = :transaksiId order by p.id");
            query.setParameter("transaksiId", transaksiId);
            List<Pesan> resultList = query.list();
            if(null != resultList){
                for(Pesan pesan : resultList){
                    PesanProperty pesanProperty = new PesanProperty();
                    pesanProperty.setId(pesan.getId());
                    pesanProperty.setMenuItemNama(pesan.getProduk());
                    pesanProperty.setJumlah(pesan.getJumlah());
                    pesanProperty.setHarga(pesan.getHarga());
                    
                    dataProperty.add(pesanProperty);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return dataProperty;
    }
    
}
