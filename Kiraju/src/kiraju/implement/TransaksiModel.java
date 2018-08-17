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
import kiraju.interfaces.ITransaksi;
import kiraju.model.Laporan;
import kiraju.model.Meja;
import kiraju.model.Menu;
import kiraju.model.Pesan;
import kiraju.model.Transaksi;
import kiraju.model.Users;
import kiraju.property.PesanProperty;
import kiraju.property.TransaksiProperty;
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
public class TransaksiModel implements ITransaksi{
    
    private final static Logger LOGGER = Logger.getLogger(TransaksiModel.class);

    @Override
    public ObservableList<PesanProperty> getbyMeja(short meja_id) {
        ObservableList<PesanProperty> menuMejaObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Transaksi t join t.mejaId mj join t.pesan p join p.menuId m join m.jenis j where mj.id = :mejaId and t.status = 0 and p.jumlah is not 0 order by j.id, p.menuId");
            query.setParameter("mejaId", meja_id);
            List<Object[]> obj = query.list();
            if(obj != null){
                for(Object[] object : obj){
                    Transaksi transaksi = (Transaksi) object[0];
                    Meja meja = (Meja) object[1];
                    Pesan pesan = (Pesan) object[2];
                    Menu menu = (Menu) object[3];
                    PesanProperty pesanProperty = new PesanProperty();
                    pesanProperty.setNama(menu.getNama());
                    pesanProperty.setHarga(menu.getHarga());
                    pesanProperty.setJumlah(pesan.getJumlah().toString());
                    pesanProperty.setTransaksiId(transaksi.getId());
                    pesanProperty.setMejaId(meja.getId());
                    pesanProperty.setNamaPemesan(transaksi.getNamaPemesan());
                    pesanProperty.setTotalHarga(menu.getHarga() * pesan.getJumlah());
                    pesanProperty.setMejaNama(meja.getNomor());
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
    public ObservableList<TransaksiProperty> getBungkus() {
        ObservableList<TransaksiProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            List<Transaksi> obj = session.createQuery("from Transaksi where mejaId = 0 and status = 0 order by dtStart").list();
            if(obj != null){
                for(Transaksi transaksi : obj){
                    TransaksiProperty transaksiProperty = new TransaksiProperty();
                    transaksiProperty.setId(transaksi.getId());
                    transaksiProperty.setNamaPemesan(transaksi.getNamaPemesan());
                    dataProperty.add(transaksiProperty);
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

    @Override
    public int insertByNama(String nama, short userId) {
        Transaksi transaksi = new Transaksi();
        transaksi.setNamaPemesan(nama);
        transaksi.setStatus((short) 0);
        transaksi.setDtStart(new Date());
        transaksi.setMejaId(new Meja((short) 0));
        Users users = new Users();
        users.setId(userId != 0 ? userId : 99);
        transaksi.setUserStart(users);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.save(transaksi);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        } finally {
            session.close();
        }
        return transaksi.getId();
    }

    @Override
    public void updateStatus(Transaksi transaksi) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            transaksi.getUserEnd().setId(transaksi.getUserEnd().getId() != 0 ? transaksi.getUserEnd().getId() : 99);
            Query query =  session.createQuery("update Transaksi set status = :status, userEnd = :userEnd, dtEnd = :dtEnd, total = :total, endDtOnly = :endDtOnly, endTimeOnly = :endTimeOnly where id = :id");
            query.setParameter("status", transaksi.getStatus());
            query.setParameter("userEnd", transaksi.getUserEnd());
            query.setParameter("dtEnd", new Date());
            query.setParameter("total", transaksi.getTotal());
            query.setParameter("endDtOnly", new Date());
            query.setParameter("endTimeOnly", new Date());
            query.setParameter("id", transaksi.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public int insertByMeja(short mejaActive, short userId) {
        Transaksi transaksi = new Transaksi();
        transaksi.setStatus(CommonConstant.TRANSAKSI_PESAN);
        transaksi.setDtStart(new Date());
        transaksi.setMejaId(new Meja(mejaActive));
        Users users = new Users();
        users.setId(userId != 0 ? userId : 99);
        transaksi.setUserStart(users);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.save(transaksi);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        } finally {
            session.close();
        }
        return transaksi.getId();
    }

    @Override
    public void updateMejaNo(Transaksi transaksi) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update Transaksi set mejaId = :mejaId where id = :id");
            query.setParameter("mejaId", transaksi.getMejaId());
            query.setParameter("id", transaksi.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void updateBayar(Transaksi transaksi) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            transaksi.getUserEnd().setId(transaksi.getUserEnd().getId() != 0 ? transaksi.getUserEnd().getId() : 99);
            Query query =  session.createQuery("update Transaksi set status = :status, dtEnd = :dtEnd, total = :total, userEnd = :userEnd, endDtOnly = :endDtOnly, endTimeOnly = :endTimeOnly where id = :id");
            query.setParameter("status", transaksi.getStatus());
            query.setParameter("dtEnd", new Date());
            query.setParameter("total", transaksi.getTotal());
            query.setParameter("userEnd", transaksi.getUserEnd());
            query.setParameter("endDtOnly", new Date());
            query.setParameter("endTimeOnly", new Date());
            query.setParameter("id", transaksi.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteById(Transaksi transaksi) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.delete(transaksi);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to delete to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public ObservableList<TransaksiProperty> getPemasukanByTglAndUser(LocalDate localDate, short usersId) {
        ObservableList<TransaksiProperty> dataObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query;
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(0 == usersId){
                query = session.createQuery("from Transaksi t join t.mejaId m join t.userEnd u where t.endDtOnly = :endDtOnly order by t.endTimeOnly");
                query.setParameter("endDtOnly", date);
            }else{
                query = session.createQuery("from Transaksi t join t.mejaId m join t.userEnd u where t.endDtOnly = :endDtOnly and u.id = :userId order by t.endTimeOnly");
                query.setParameter("endDtOnly", date);
                query.setParameter("userId", usersId);
            }
            List<Object[]> obj = query.list();
            if(obj != null){
                for(Object[] object : obj){
                    TransaksiProperty transaksiProperty = new TransaksiProperty();
                    Transaksi transaksi = (Transaksi) object[0];
                    transaksiProperty.setWaktu(transaksi.getDtEnd());
                    transaksiProperty.setNamaPemesan(transaksi.getNamaPemesan());
                    transaksiProperty.setStatusTransaksi(transaksi.getStatus());
                    transaksiProperty.setTotal(transaksi.getTotal());
                    Meja meja = (Meja) object[1];
                    transaksiProperty.setMeja(meja.getId());
                    dataObsList.add(transaksiProperty);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return dataObsList;
    }

    @Override
    public List getChartByBulan(int bulan) {
        List resultList = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("select DAY(endDtOnly), sum(total) from Transaksi where status = :status and MONTH(endDtOnly) = :month and YEAR(endDtOnly) = :year group by endDtOnly");
            query.setInteger("month", bulan);
            query.setInteger("year", LocalDate.now().getYear());
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            resultList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public ObservableList<Integer> getYear() {
        ObservableList<Integer> resultObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("select distinct YEAR(endDtOnly) from Transaksi where status = :status and endDtOnly is not null");
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            List<Integer> resultList = query.list();
            resultObsList.addAll(resultList);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return resultObsList;
    }

    @Override
    public List getChartByTahun(int tahun) {
        List resultList = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("select MONTH(endDtOnly), sum(total) from Transaksi where status = :status and YEAR(endDtOnly) = :year group by MONTH(endDtOnly)");
            query.setInteger("year", tahun);
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            resultList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public List<Laporan> getLaporan(LocalDate tglDari, LocalDate tglSampai) {
        List<Laporan> resultList = new ArrayList();
        Date dateDari = Date.from(tglDari.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateSampai = Date.from(tglSampai.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            String sql = "select tgl, sum(income) pemasukan, sum(outcome) pengeluaran from " +
                    "(select END_DT_ONLY tgl, sum(total) income, 0 outcome from APP.TRANSAKSI where status = :status GROUP by END_DT_ONLY " +
                    "union all "+
                    "SELECT DT_ONLY tgl, 0 income, sum(harga) outcome from APP.PENGELUARAN GROUP by DT_ONLY) x " +
                    "group by tgl having tgl between :tglDari AND :tglSampai order by tgl";
            Query query = session.createSQLQuery(sql);
            query.setDate("tglDari", dateDari);
            query.setDate("tglSampai", dateSampai);
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            List<Object[]> rows = query.list();
            for(Object[] row : rows){
                Laporan laporan = new Laporan();
                laporan.setTgl((Date) row[0]);
                laporan.setPemasukan(Integer.parseInt(row[1].toString()));
                laporan.setPengeluaran(Integer.parseInt(row[2].toString()));
                resultList.add(laporan);
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return resultList;
    }

    //added by Arvit@20170820-v1.2 bug laporan
    @Override
    public List<Laporan> getLaporanPenjualan(LocalDate tglDari, LocalDate tglSampai) {
        List<Laporan> resultList = new ArrayList();
        Date dateDari = Date.from(tglDari.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateSampai = Date.from(tglSampai.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            String sql = "select m.NAMA daftar_menu, j.NAMA jenis_menu, x.total from " +
                    "(SELECT p.menu_id, sum(p.JUMLAH) total FROM APP.PESAN p " +
                    "join APP.TRANSAKSI t on t.ID = p.TRANSAKSI_ID where t.status = :status and t.END_DT_ONLY between :tglDari and :tglSampai group by p.MENU_ID  having sum(p.JUMLAH) > 0) x " +
                    "join app.MENU m on m.ID = x.MENU_ID " +
                    "join app.JENIS_MENU j on j.ID = m.JENIS " +
                    "order by j.ID, x.total desc";
            Query query = session.createSQLQuery(sql);
            query.setDate("tglDari", dateDari);
            query.setDate("tglSampai", dateSampai);
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            List<Object[]> rows = query.list();
            for(Object[] row : rows){
                Laporan laporan = new Laporan();
                laporan.setDaftarMenu(row[0].toString());
                laporan.setJenisMenu(row[1].toString());
                laporan.setJumlah(Integer.parseInt(row[2].toString()));
                resultList.add(laporan);
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return resultList;
    }
    
}
