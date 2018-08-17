/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.model.JenisMenu;
import kiraju.model.Menu;
import kiraju.property.MenuProperty;
import kiraju.property.PesanProperty;
import kiraju.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import kiraju.interfaces.IMenu;
import kiraju.util.CommonConstant;
import org.apache.log4j.Logger;

/**
 *
 * @author arvita
 */
public class MenuModel implements IMenu{
    
    private final static Logger LOGGER = Logger.getLogger(MenuModel.class);

    @Override
    public ObservableList<MenuProperty> getAllProperty(short jenisId) {
        ObservableList<MenuProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            String query = jenisId == 0 ? "from Menu m join m.jenis j where j.deletedFlag = 0 order by m.deletedFlag, j.id, m.id" : "from Menu m join m.jenis j where j.deletedFlag = 0 and j.id = " + jenisId + " order by m.deletedFlag, j.id, m.id";
            List<Object[]> obj = session.createQuery(query).list();
            if(null != obj){
                for(Object[] row : obj){
                    MenuProperty menuProp = new MenuProperty();
                    Menu menu = (Menu) row[0];
                    JenisMenu jenisMenu = (JenisMenu) row[1];
                    menuProp.setId(menu.getId());
                    menuProp.setNama(menu.getNama());
                    menuProp.setHarga(menu.getHarga());
                    menuProp.setCreatedDate(menu.getCreatedDt());
                    menuProp.setUpdatedDate(menu.getUpdatedDt());
                    menuProp.setDeletedFlag(menu.getDeletedFlag());
                    menuProp.setJenisId(jenisMenu.getId());
                    menuProp.setJenisNama(jenisMenu.getNama());
                    dataProperty.add(menuProp);
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
    public short insert(Menu menu) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            menu.setCreatedDt(new Date());
            session.save(menu);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        } finally {
            session.close();
        }
        return menu.getId();
    }

    @Override
    public void update(Menu menu) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update Menu set nama = :nama, harga = :harga, jenis = :jenis, updatedDt = :updated_dt, deletedFlag = :deletedFlag where id = :id");
            query.setParameter("nama", menu.getNama());
            query.setParameter("harga", menu.getHarga());
            query.setParameter("updated_dt", new Date());
            query.setParameter("id", menu.getId());
            query.setParameter("jenis", menu.getJenis());
            query.setParameter("deletedFlag", menu.getDeletedFlag());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(short id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update Menu set deletedFlag = :deleted_flag, updatedDt = :updated_dt where id = :id");
            query.setParameter("deleted_flag", (short) 1);
            query.setParameter("updated_dt", new Date());
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
    public ObservableList<PesanProperty> getAllAndJumlah(short jenisId, int transaksiId) {
        ObservableList<PesanProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            String sql;
            if(jenisId == 0){
                sql = "select m.nama, m.harga, p.jumlah, m.jenis, p.id, m.id menu_id from app.menu m left join (select * from app.pesan where transaksi_id = "+transaksiId+") p on m.ID = p.menu_id join app.jenis_menu j on m.JENIS = j.ID where m.DELETED_FLAG = 0 and j.DELETED_FLAG = 0";
            }else{
                sql = "select m.nama, m.harga, p.jumlah, m.jenis, p.id, m.id menu_id from app.menu m left join (select * from app.pesan where transaksi_id = "+transaksiId+") p on m.ID = p.menu_id join app.jenis_menu j on m.JENIS = j.ID where m.DELETED_FLAG = 0 and j.DELETED_FLAG = 0 and m.jenis = " + jenisId;
            }
            
            SQLQuery query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();
            if(null != rows){
                rows.stream().map((row) -> {
                    PesanProperty pesanProperty = new PesanProperty();
                    pesanProperty.setTransaksiId(transaksiId);
                    pesanProperty.setNama(row[0].toString());
                    pesanProperty.setHarga(Integer.parseInt(row[1].toString()));
                    String jumlah = null != row[2] ? row[2].toString() : "0";
                    pesanProperty.setJumlah(jumlah);
                    pesanProperty.setJenisId(Short.parseShort(row[3].toString()));
                    if(null != row[4]) pesanProperty.setId(Integer.parseInt(row[4].toString()));
                    pesanProperty.setMenuId(Short.parseShort(row[5].toString()));
                    return pesanProperty;                    
                }).forEachOrdered((pesanProperty) -> {
                    dataProperty.add(pesanProperty);
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
    public List getChartByMonthAndJenisMenu(int bulan, Short jenisMenu) {
        List<List> ls2d = new ArrayList<>();
        if(jenisMenu != null){
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx;
            try {
                tx = session.beginTransaction();
                String sql = "select x.hari, x.total, x.menu_id, m.NAMA from APP.MENU m join (SELECT p.menu_id, sum(p.JUMLAH) total, DAY(t.END_DT_ONLY) hari FROM APP.PESAN p join APP.TRANSAKSI t on t.ID = p.TRANSAKSI_ID where MONTH(t.END_DT_ONLY) = :bulan and t.status = :status" +
                        " group by p.MENU_ID, t.END_DT_ONLY) x on m.ID = x.MENU_ID where m.JENIS = :jenisMenu order by x.menu_id, x.hari";
                SQLQuery query = session.createSQLQuery(sql);
                query.setInteger("bulan", bulan);
                query.setShort("jenisMenu", jenisMenu);
                query.setShort("status", CommonConstant.TRANSAKSI_BAYAR);
                List resultList = query.list();
                tx.commit();
                if(null != resultList){
                    List list = new ArrayList();
                    Short prevMenuId = 0;
                    for(int i = 0; i < resultList.size(); i++){
                        Object[] row = (Object[]) resultList.get(i);
                        Short currMenuId = (Short) row[2];
                        if(prevMenuId > 0 && !prevMenuId.equals(currMenuId)){
                            ls2d.add(list);
                            list = new ArrayList();
                        }
                        list.add(row);
                        prevMenuId = (Short) row[2];
                    }
                    ls2d.add(list);
                }
            } catch (HibernateException e) {
                LOGGER.error("failed to select to database", e);
            } finally {
                session.close();
            }
        }
        return ls2d;
    }
    
}
