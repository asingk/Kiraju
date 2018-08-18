/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.model.Laporan;
import kiraju.interfaces.ITransaksi;
import kiraju.model.Diskon;
import kiraju.model.Meja;
import kiraju.model.MenuItem;
import kiraju.model.MetodePembayaran;
import kiraju.model.Pajak;
import kiraju.model.Pelanggan;
import kiraju.model.Pemasok;
import kiraju.model.Pesan;
import kiraju.model.Transaksi;
import kiraju.model.TransaksiPembelian;
import kiraju.model.Users;
import kiraju.property.PesanProperty;
import kiraju.property.TransaksiPembelianProperty;
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
            Query query = session.createQuery("from Transaksi t left join t.diskonId d left join t.pajakId pj join t.mejaId mj join t.pesan p join p.menuItemCode mi where mj.id = :mejaId and t.status = 0 and p.jumlah is not 0 order by p.id");
            query.setParameter("mejaId", meja_id);
            List<Object[]> obj = query.list();
            if(obj != null){
                for(Object[] object : obj){
                    Transaksi transaksi = (Transaksi) object[0];
                    Diskon diskon = (Diskon) object[1];
                    Pajak pajak = (Pajak) object[2];
                    Meja meja = (Meja) object[3];
                    Pesan pesan = (Pesan) object[4];
                    MenuItem menuItem = (MenuItem) object[5];
//                    Menu menu = (Menu) object[6];
                    PesanProperty pesanProperty = new PesanProperty();
                    pesanProperty.setId(pesan.getId());
                    pesanProperty.setTransaksiId(transaksi.getId());
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
            List<Transaksi> obj = session.createQuery("from Transaksi where status = 3 and namaPemesan is not null order by dtStart").list();
            if(obj != null){
                for(Transaksi transaksi : obj){
                    TransaksiProperty transaksiProperty = new TransaksiProperty();
                    transaksiProperty.setId(transaksi.getId());
                    transaksiProperty.setNamaPemesan(transaksi.getNamaPemesan());
                    if(null != transaksi.getDiskonTotal()){
                        transaksiProperty.setDiskonTotal(transaksi.getDiskonTotal());
                    }
                    if(null != transaksi.getPajakTotal()){
                        transaksiProperty.setPajakTotal(transaksi.getPajakTotal());
                    }
                    transaksiProperty.setTotal(transaksi.getTotal());
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
    public void updateStatus(Transaksi transaksi) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
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
    public int insertByMeja(short mejaActive, String userId) {
        Transaksi transaksi = new Transaksi();
        transaksi.setStatus(CommonConstant.TRANSAKSI_PESAN);
        transaksi.setDtStart(new Date());
        transaksi.setMejaId(new Meja(mejaActive));
        transaksi.setUserStart(new Users(userId));
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            //Transaksi
            session.save(transaksi);
            
            //Meja
            Query query = session.createQuery("update Meja set status = :status where id = :id");
            query.setParameter("status", CommonConstant.MEJA_TERISI);
            query.setParameter("id", mejaActive);
            query.executeUpdate();
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
            Query query =  session.createQuery("update Transaksi set status = :status, dtEnd = :dtEnd, total = :total, userEnd = :userEnd, "
                    + "endDtOnly = :endDtOnly, endTimeOnly = :endTimeOnly, metodePembayaranId = :metodePembayaranId, "
                    + "diskonId = :diskonId, diskonTotal = :diskonTotal, pajakId = :pajakId, pajakTotal = :pajakTotal, pelangganId = :pelangganId, "
                    + "modalTotal = :modalTotal where id = :id");
            query.setParameter("status", transaksi.getStatus());
            query.setParameter("dtEnd", new Date());
            query.setParameter("total", transaksi.getTotal());
            query.setParameter("userEnd", transaksi.getUserEnd());
            query.setParameter("endDtOnly", new Date());
            query.setParameter("endTimeOnly", new Date());
            query.setParameter("metodePembayaranId", transaksi.getMetodePembayaranId());
//            query.setParameter("mejaId", transaksi.getMejaId());
//            if(transaksi.getDiskonId().getId() > 0) {
                query.setParameter("diskonId", transaksi.getDiskonId());
                query.setParameter("diskonTotal", transaksi.getDiskonTotal());
//            }else{
//                query.setParameter("diskonId", null);
//                query.setParameter("diskonTotal", null);
//            }
//            if(transaksi.getPajakId().getId() > 0) {
                query.setParameter("pajakId", transaksi.getPajakId());
                query.setParameter("pajakTotal", transaksi.getPajakTotal());
//            }else{
//                query.setParameter("pajakId", null);
//                query.setParameter("pajakTotal", null);
//            }
//            if(null != transaksi.getPelangganId()){
                query.setParameter("pelangganId", transaksi.getPelangganId());
//            }
            query.setParameter("modalTotal", transaksi.getModalTotal());
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
    public ObservableList<TransaksiProperty> getPemasukanByTglAndUser(LocalDate localDate, String usersId) {
        ObservableList<TransaksiProperty> dataObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query;
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(!usersId.equals("")){
                query = session.createQuery("from Transaksi t join t.mejaId m join t.userEnd u left join t.diskonId d left join t.pajakId p left join t.metodePembayaranId mb where t.endDtOnly = :endDtOnly and u.id = :userId order by t.endTimeOnly");
                query.setParameter("userId", usersId);
            }else{
                query = session.createQuery("from Transaksi t join t.mejaId m join t.userEnd u left join t.diskonId d left join t.pajakId p left join t.metodePembayaranId mb where t.endDtOnly = :endDtOnly order by t.endTimeOnly");
//                query.setParameter("userId", null);
            }
            query.setParameter("endDtOnly", date);
            List<Object[]> obj = query.list();
            if(obj != null){
                for(Object[] object : obj){
                    TransaksiProperty transaksiProperty = new TransaksiProperty();
                    Transaksi transaksi = (Transaksi) object[0];
                    Diskon diskon = (Diskon) object[3];
                    Pajak pajak = (Pajak) object[4];
                    MetodePembayaran metodePembayaran = (MetodePembayaran) object[5];
                    transaksiProperty.setId(transaksi.getId());
                    transaksiProperty.setWaktu(transaksi.getDtEnd());
//                    transaksiProperty.setNamaPemesan(transaksi.getNamaPemesan());
                    transaksiProperty.setStatusTransaksi(transaksi.getStatus());
                    transaksiProperty.setTotal(transaksi.getTotal());
//                    Meja meja = (Meja) object[1];
//                    transaksiProperty.setMeja(meja.getId());
//                    String pelanggan = meja.getId() > 0 ? "Meja " + meja.getNomor() : transaksi.getNamaPemesan();
//                    transaksiProperty.setNamaPemesan(pelanggan);
                    if(null != diskon){
                        transaksiProperty.setDiskonNama(diskon.getNama());
                    }
                    if(null != pajak) {
                        transaksiProperty.setPajakNama(pajak.getNama());
                    }
                    if(null != metodePembayaran) {
                        transaksiProperty.setMetodePembayaranNama(metodePembayaran.getNama());
                    }
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
            Query query = session.createQuery("select distinct YEAR(endDtOnly) from Transaksi where status = :status and endDtOnly is not null order by YEAR(endDtOnly) desc");
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            List<Integer> resultList = query.list();
//            if(null != resultList && resultList.size() > 0) {
                resultObsList.addAll(resultList);
//            }else{
//                resultObsList.add(LocalDate.now().getYear());
//            }
            
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
            String sql = "select tgl, sum(income) pemasukan, sum(outcome) pengeluaran, sum(diskon) diskon, sum(pajak) pajak, sum(modal) modal from ("
                    + "select END_DT_ONLY tgl, sum(t.total) income, 0 outcome, sum(diskon_total) diskon, sum(pajak_total) pajak, sum(modal_total) modal "
                    + "from APP.TRANSAKSI t "
                    + "where status = :status GROUP by END_DT_ONLY "
                    + "union all "
                    + "SELECT DT_ONLY tgl, 0 income, sum(harga) outcome, 0 diskon, 0 pajak, 0 modal from APP.PENGELUARAN GROUP by DT_ONLY) x "
                    + "group by tgl having tgl between :tglDari AND :tglSampai order by tgl";
            Query query = session.createSQLQuery(sql);
            query.setDate("tglDari", dateDari);
            query.setDate("tglSampai", dateSampai);
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            List<Object[]> rows = query.list();
            for(Object[] row : rows){
                Laporan laporan = new Laporan();
                laporan.setTgl((Date) row[0]);
                Integer pemasukan = Integer.valueOf(row[1].toString());
                Integer pengeluaran = Integer.valueOf(row[2].toString());
                Integer diskon = 0;
                if(null != row[3]){
                    diskon = Integer.valueOf(row[3].toString());
                }
                Integer pajak = 0;
                if(null != row[4]){
                    pajak = Integer.valueOf(row[4].toString());
                }
//                Integer modal = Integer.valueOf(row[5].toString());
                laporan.setPemasukan(pemasukan);
                laporan.setPengeluaran(pengeluaran);
                laporan.setDiskon(diskon);
                laporan.setPajak(pajak);
                
//                laporan.setModal(modal);
//                laporan.setUntung(pemasukan-modal-pajak-pengeluaran);
                laporan.setUntung(pemasukan-pajak-pengeluaran);   //remove modal @20171222 - kiraju3
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
            String sql = "select mi.NAMA menu_item, j.NAMA jenis_menu, x.total, x.modal, x.untung, x.tambahan, mi.HARGA_TOTAL harga, x.subtotal from "
                    + "(SELECT p.menu_item_code, sum(p.JUMLAH) total, sum(p.modal*p.JUMLAH) modal, sum(p.untung*p.JUMLAH) untung, sum(p.tambahan*p.JUMLAH) tambahan, sum(p.harga*p.jumlah) subtotal FROM APP.PESAN p "
                    + "join APP.TRANSAKSI t on t.ID = p.TRANSAKSI_ID "
                    + "where t.status = :status and t.END_DT_ONLY between :tglDari and :tglSampai group by p.menu_item_code having sum(p.JUMLAH) > 0) x "
                    + "join app.MENU_ITEM mi on mi.code = x.menu_item_code "
//                    + "join app.MENU m on m.ID = mi.MENU_ID "
                    + "join app.JENIS_MENU j on j.ID = mi.JENIS_MENU_ID "
                    + "order by j.ID, x.total desc";
            Query query = session.createSQLQuery(sql);
            query.setDate("tglDari", dateDari);
            query.setDate("tglSampai", dateSampai);
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            List<Object[]> rows = query.list();
            for(Object[] row : rows){
                Laporan laporan = new Laporan();
                laporan.setDaftarMenu(row[0].toString());
                laporan.setJenisMenu(row[1].toString());
                laporan.setJumlah(Integer.valueOf(row[2].toString()));
                laporan.setHarga(Integer.valueOf(row[6].toString()));
                laporan.setSubtotal(Integer.valueOf(row[7].toString()));
                resultList.add(laporan);
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return resultList;
    }

    @Override
    public int insert(String userId) {
        Transaksi transaksi = new Transaksi();
        transaksi.setStatus(CommonConstant.TRANSAKSI_PESAN);
        transaksi.setDtStart(new Date());
        transaksi.setUserStart(new Users(userId));
        transaksi.setMejaId(new Meja((short) 0));
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
    public void updateSimpan(Transaksi transaksi) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query =  session.createQuery("update Transaksi set status = :status, namaPemesan = :namaPemesan, diskonId = :diskonId, "
                    + "pajakId = :pajakId, total = :total, diskonTotal = :diskonTotal, pajakTotal = :pajakTotal, modalTotal = :modalTotal where id = :id");
            query.setParameter("status", transaksi.getStatus());
            query.setParameter("namaPemesan", transaksi.getNamaPemesan());
            query.setParameter("diskonId", null != transaksi.getDiskonId() ? transaksi.getDiskonId() : null);
            query.setParameter("pajakId", null != transaksi.getPajakId() ? transaksi.getPajakId() : null);
            query.setParameter("total", transaksi.getTotal());
            query.setParameter("diskonTotal", null != transaksi.getDiskonId() ? transaksi.getDiskonTotal() : null);
            query.setParameter("pajakTotal", null != transaksi.getPajakId() ? transaksi.getPajakTotal() : null);
            query.setParameter("modalTotal", transaksi.getModalTotal());
            query.setParameter("id", transaksi.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }

    @Override
    public void updateStatusOnly(Transaksi transaksi) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query =  session.createQuery("update Transaksi set status = :status where id = :id");
            query.setParameter("status", transaksi.getStatus());
            query.setParameter("id", transaksi.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }

    @Override
    public ObservableList<TransaksiProperty> getTransaksiByPelanggan(Pelanggan pelanggan) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<TransaksiProperty> dataProperty = FXCollections.observableArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Transaksi where pelangganId = :pelangganId order by id");
            query.setParameter("pelangganId", pelanggan);
            List<Transaksi> resultList = query.list();
//            Criteria criteria = session.createCriteria(Transaksi.class);
//            criteria.add(Restrictions.eq("pelangganId", pelanggan));
//            criteria.addOrder(Order.asc("id"));
//            List resultList = criteria.list();
            if(null != resultList){
                for(Object o : resultList){
                    Transaksi transaksi = (Transaksi) o;
                    TransaksiProperty transaksiProp = new TransaksiProperty();
                    transaksiProp.setId(transaksi.getId());
                    transaksiProp.setTanggal(transaksi.getDtEnd());
                    transaksiProp.setTotal(transaksi.getTotal());
                    dataProperty.add(transaksiProp);
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
    public void updateStatusBayar(Transaksi transaksi) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update Transaksi set status = :status, userEnd = :userEnd, dtEnd = :dtEnd, endDtOnly = :endDtOnly, "
                    + "endTimeOnly = :endTimeOnly, metodePembayaranId = :metodePembayaranId, pelangganId = :pelangganId where id = :id");
            query.setParameter("status", transaksi.getStatus());
            query.setParameter("userEnd", transaksi.getUserEnd());
            query.setParameter("dtEnd", new Date());
            query.setParameter("endDtOnly", new Date());
            query.setParameter("endTimeOnly", new Date());
            query.setParameter("metodePembayaranId", transaksi.getMetodePembayaranId());
            query.setParameter("pelangganId", transaksi.getPelangganId());
            query.setParameter("id", transaksi.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }

    @Override
    public ObservableList<TransaksiProperty> getMetodePembayaranByTglAndUser(LocalDate localDate, String userId) {
        ObservableList<TransaksiProperty> dataObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query;
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(!userId.equals("")){
                query = session.createQuery("select sum(t.total) as total, mb.nama, mb.id from Transaksi t join t.metodePembayaranId mb join t.userEnd u where t.endDtOnly = :endDtOnly and u.id = :userId group by mb.nama, mb.id order by mb.id");
                query.setParameter("userId", userId);
            }else{
                query = session.createQuery("select sum(t.total) as total, mb.nama, mb.id from Transaksi t join t.metodePembayaranId mb join t.userEnd u where t.endDtOnly = :endDtOnly group by mb.nama, mb.id order by mb.id");
//                query.setParameter("userId", null);
            }
            query.setParameter("endDtOnly", date);
            List<Object[]> rows = query.list();
            if(rows != null){
                for(Object[] row : rows){
                    TransaksiProperty transaksiProperty = new TransaksiProperty();
                    transaksiProperty.setTotal(Integer.valueOf(row[0].toString()));
                    transaksiProperty.setMetodePembayaranNama(row[1].toString());
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
    public Laporan getLaporanPenjualan2(LocalDate tglDari, LocalDate tglSampai) {
//        List<Laporan> resultList = new ArrayList();
        Laporan laporan = new Laporan();
        Date dateDari = Date.from(tglDari.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateSampai = Date.from(tglSampai.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            String sql = "select sum(t.total) income, sum(diskon_total) diskon, sum(pajak_total) pajak, sum(modal_total) modal "
                    + "from APP.TRANSAKSI t "
                    + "where status = :status "
                    + "and t.END_DT_ONLY between :tglDari AND :tglSampai";
            Query query = session.createSQLQuery(sql);
            query.setDate("tglDari", dateDari);
            query.setDate("tglSampai", dateSampai);
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            List<Object[]> rows = query.list();
            for(Object[] row : rows){
//                Laporan laporan = new Laporan();
//                laporan.setTgl((Date) row[0]);
                Integer pemasukan = Integer.valueOf(row[0].toString());
//                Integer pengeluaran = Integer.valueOf(row[2].toString());
                Integer diskon = 0;
                if(null != row[1]){
                    diskon = Integer.valueOf(row[1].toString());
                }
                Integer pajak = 0;
                if(null != row[2]){
                    pajak = Integer.valueOf(row[2].toString());
                }
//                Integer modal = Integer.valueOf(row[5].toString());
                laporan.setPemasukan(pemasukan);
//                laporan.setPengeluaran(pengeluaran);
                laporan.setDiskon(diskon);
                laporan.setPajak(pajak);
                
//                laporan.setModal(modal);
//                laporan.setUntung(pemasukan-modal-pajak-pengeluaran);
//                resultList.add(laporan);
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return laporan;
    }

    @Override
    public Laporan getlabaRugi(LocalDate tglDari, LocalDate tglSampai) {
        Laporan laporan = new Laporan();
        Date dateDari = Date.from(tglDari.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateSampai = Date.from(tglSampai.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            String sql = "select sum(income) pemasukan, sum(outcome) pengeluaran, sum(pajak) pajak, sum(modal) modal from ( " +
                    "select sum(t.total) income, 0 outcome, sum(pajak_total) pajak, sum(modal_total) modal " +
                    "from APP.TRANSAKSI t " +
                    "where status = :status and t.END_DT_ONLY between :tglDari AND :tglSampai " +
                    "union all " +
                    "SELECT 0 income, sum(harga) outcome, 0 pajak, 0 modal from APP.PENGELUARAN p " +
                    "where p.DT_ONLY between :tglDari AND :tglSampai) x";
            Query query = session.createSQLQuery(sql);
            query.setDate("tglDari", dateDari);
            query.setDate("tglSampai", dateSampai);
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            List<Object[]> rows = query.list();
            for(Object[] row : rows){
                Integer pemasukan = Integer.valueOf(row[0].toString());
                Integer pengeluaran = Integer.valueOf(row[1].toString());
                Integer pajak = 0;
                if(null != row[2]){
                    pajak = Integer.valueOf(row[2].toString());
                }
                Integer modal = Integer.valueOf(row[3].toString());
                laporan.setPemasukan(pemasukan);
                laporan.setPengeluaran(pengeluaran);
                laporan.setPajak(pajak);
                
//                laporan.setModal(modal);
                laporan.setUntung(pemasukan-modal-pajak-pengeluaran);
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return laporan;
    }

    @Override
    public Laporan getlabaRugi(YearMonth yearMonth) {
        Laporan laporan = new Laporan();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            String sql = "select sum(income) pemasukan, sum(outcome) pengeluaran, sum(pajak) pajak, sum(modal) modal from ( " +
                    "select sum(t.total) income, 0 outcome, sum(pajak_total) pajak, sum(modal_total) modal " +
                    "from APP.TRANSAKSI t " +
                    "where status = :status and month(t.END_DT_ONLY) = :bulan and year(t.END_DT_ONLY) = :tahun " +
                    "union all " +
                    "SELECT 0 income, sum(harga) outcome, 0 pajak, 0 modal from APP.PENGELUARAN p " +
                    "where month(p.DT_ONLY) = :bulan and year(p.DT_ONLY) = :tahun) x";
            Query query = session.createSQLQuery(sql);
            query.setParameter("bulan", yearMonth.getMonthValue());
            query.setParameter("tahun", yearMonth.getYear());
            query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
            List<Object[]> rows = query.list();
            for(Object[] row : rows){
                Integer pemasukan = Integer.valueOf(row[0].toString());
                Integer pengeluaran = Integer.valueOf(row[1].toString());
                Integer pajak = 0;
                if(null != row[2]){
                    pajak = Integer.valueOf(row[2].toString());
                }
                Integer modal = Integer.valueOf(row[3].toString());
                laporan.setPemasukan(pemasukan);
                laporan.setPengeluaran(pengeluaran);
                laporan.setPajak(pajak);
                
//                laporan.setModal(modal);
                laporan.setUntung(pemasukan-modal-pajak-pengeluaran);
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return laporan;
    }

    @Override
    public int insertJual(Transaksi transaksi) {
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
    public ObservableList<TransaksiProperty> getPemasukanByTgl(LocalDate localDate) {
        ObservableList<TransaksiProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Query query = session.createQuery("from Transaksi t join t.metodePembayaranId mb where date(t.tanggal) = :date order by t.tanggal");
            query.setParameter("date", date);

            List<Object[]> rows = query.list();
            if(null != rows){
                for(Object[] row : rows){
                    Transaksi transaksi = (Transaksi) row[0];
                    MetodePembayaran metodePembayaran = (MetodePembayaran) row[1];
                    TransaksiProperty transaksiProp = new TransaksiProperty();
                    transaksiProp.setId(transaksi.getId());
                    transaksiProp.setWaktu(transaksi.getTanggal());
                    transaksiProp.setTotal(transaksi.getTotal());
                    transaksiProp.setNamaPemesan(transaksi.getNamaPemesan());
                    transaksiProp.setMetodePembayaranNama(metodePembayaran.getNama());
                    dataProperty.add(transaksiProp);
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
    public ObservableList<TransaksiProperty> getMetodePembayaranByTgl(LocalDate localDate) {
        ObservableList<TransaksiProperty> dataObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Query query = session.createQuery("select sum(t.total) as total, mb.nama, mb.id from Transaksi t join t.metodePembayaranId mb where date(t.tanggal) = :date group by mb.nama, mb.id order by mb.id");

            query.setParameter("date", date);
            List<Object[]> rows = query.list();
            if(rows != null){
                for(Object[] row : rows){
                    TransaksiProperty transaksiProperty = new TransaksiProperty();
                    transaksiProperty.setTotal(Integer.valueOf(row[0].toString()));
                    transaksiProperty.setMetodePembayaranNama(row[1].toString());
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
    public ObservableList<TransaksiPembelianProperty> getHutangList() {
        ObservableList<TransaksiPembelianProperty> dataProp = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from TransaksiPembelian tp join tp.pemasokId p where tp.isLunas = false and tp.status = true order by tanggal");
//            query.setParameter("isLunas", false);
            List<Object[]> rows = query.list();
            if(null != rows && !rows.isEmpty()) {
                for(Object[] row : rows) {
                    TransaksiPembelianProperty property = new TransaksiPembelianProperty();
                    TransaksiPembelian pembelian = (TransaksiPembelian) row[0];
                    Pemasok pemasok = (Pemasok) row[1];
                    property.setId(pembelian.getId());
                    property.setTanggal(pembelian.getTanggal());
                    property.setPemasokNama(pemasok.getNama());
                    property.setTotal(pembelian.getTotal());
                    dataProp.add(property);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return dataProp;
    }

    @Override
    public ObservableList<TransaksiProperty> getPiutangList() {
        ObservableList<TransaksiProperty> dataProp = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Transaksi where isLunas = :isLunas order by tanggal");
            query.setParameter("isLunas", false);
            List<Transaksi> resultList = query.list();
            if(null != resultList && !resultList.isEmpty()) {
                for(Transaksi transaksi : resultList) {
                    TransaksiProperty property = new TransaksiProperty();
                    property.setId(transaksi.getId());
                    property.setTanggal(transaksi.getTanggal());
                    property.setNamaPemesan(transaksi.getNamaPemesan());
                    property.setTotal(transaksi.getTotal());
                    dataProp.add(property);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return dataProp;
    }

    @Override
    public void updateLunas(TransaksiProperty property) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update Transaksi set isLunas = true where id = :id");
            query.setParameter("id", property.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }
    
}
