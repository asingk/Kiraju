/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import kiraju.interfaces.IMenuItem;
import kiraju.model.Menu;
import kiraju.model.MenuItem;
import kiraju.property.MenuItemProperty;
import kiraju.property.MenuProperty;
import kiraju.property.PesanProperty;
import kiraju.property.StokOpnameItemProperty;
import kiraju.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author arvita
 */
public class MenuItemModel implements IMenuItem{
    
    private final static Logger LOGGER = Logger.getLogger(MenuItemModel.class);

//    @Override
//    public ObservableList<PesanProperty> getByMenuIdAndJumlah(int menuId, int transaksiId) {
//        ObservableList<PesanProperty> dataProperty = FXCollections.observableArrayList();
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction tx;
//        try {
//            tx = session.beginTransaction();
//            String sql = "select mi.nama, p.jumlah, p.id, mi.code, mi.modal, mi.untung_code, mi.untung, mi.tambahan_code, mi.tambahan from app.MENU_ITEM mi " +
//                    "left join (select * from app.pesan where transaksi_id = :transaksiId) p on mi.code = p.menu_item_code " +
//                    "join app.menu m on mi.MENU_ID = m.ID " +
//                    "join app.JENIS_MENU j on m.JENIS = j.ID " +
//                    "where m.DELETED_FLAG = 0 and j.DELETED_FLAG = 0 and mi.MENU_ID = :menuId";
//
//            SQLQuery query = session.createSQLQuery(sql);
//            query.setParameter("transaksiId", transaksiId);
//            query.setParameter("menuId", menuId);
//            List<Object[]> rows = query.list();
//            if(null != rows){
//                rows.stream().map((row) -> {
//                    PesanProperty pesanProperty = new PesanProperty();
//                    pesanProperty.setTransaksiId(transaksiId);
//                    pesanProperty.setNama(row[0].toString());
//                    String jumlah = null != row[1] ? row[1].toString() : "0";
//                    pesanProperty.setJumlah(Integer.valueOf(jumlah));
////                    pesanProperty.setJenisId(Short.parseShort(row[2].toString()));
//                    if(null != row[2]) pesanProperty.setId(Integer.parseInt(row[2].toString()));
////                    pesanProperty.setMenuId(Short.parseShort(row[4].toString()));
////                    pesanProperty.setMenuItemId(Integer.parseInt(row[3].toString()));
//                    pesanProperty.setCode(row[3].toString());
//                    pesanProperty.setModal(Integer.valueOf(row[4].toString()));
//                    pesanProperty.setUntungCode(Integer.valueOf(row[5].toString()));
//                    pesanProperty.setUntung(Integer.valueOf(row[6].toString()));
//                    pesanProperty.setTambahanCode(Integer.valueOf(row[7].toString()));
//                    pesanProperty.setTambahan(Integer.valueOf(row[8].toString()));
//                    
//                    return pesanProperty;                    
//                }).forEachOrdered((pesanProperty) -> {
//                    dataProperty.add(pesanProperty);
//                });
//            }
//            tx.commit();
//        } catch (HibernateException e) {
//            LOGGER.error("failed to select to database", e);
//        }
//        session.close();
//        return dataProperty;
//    }

    @Override
    public ObservableList<MenuItemProperty> getPropertyByMenuId(int menuId) {
        ObservableList<MenuItemProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
//            MenuItem menuItemBefore = new MenuItem();
//            Menu menuBefore = new Menu();
//            menuBefore.setId(menuId);
//            menuItemBefore.setMenuId(menuBefore);
            Query query = session.createQuery("from MenuItem mi join mi.menuId m where mi.menuId = :menuId");
            query.setParameter("menuId", new Menu(menuId));
            List<Object[]> obj = query.list();
            if(null != obj){
                for(Object[] row : obj){
                    MenuItem menuItem = (MenuItem) row[0];
                    Menu menu = (Menu) row[1];
                    MenuItemProperty menuItemProp = new MenuItemProperty();
                    MenuProperty menuProp = new MenuProperty();
                    menuProp.setId(menu.getId());
                    menuProp.setNama(menu.getNama());
//                    menuProp.setCode(menu.getCode());
//                    menuItemProp.setId(menuItem.getId());
                    menuItemProp.setNama(menuItem.getNama());
                    menuItemProp.setCode(menuItem.getCode());
                    menuItemProp.setHargaJual(menuItem.getHargaTotal());
                    menuItemProp.setStokFlag(menuItem.getStokFlag());
                    if(menuItem.getStokFlag()){
                        menuItemProp.setStok(menuItem.getStok().toString());
                    }
                    menuItemProp.setModal(menuItem.getModal());
                    menuItemProp.setUntung(menuItem.getUntung());
                    menuItemProp.setUntungCode(menuItem.getUntungCode());
                    menuItemProp.setTambahan(menuItem.getTambahan());
                    menuItemProp.setTambahanCode(menuItem.getTambahanCode());
                    dataProperty.add(menuItemProp);
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
    public MenuItem getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        MenuItem menuItem = new MenuItem();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(MenuItem.class);
            criteria.add(Restrictions.eq("id", id));
            List resultList = criteria.list();
            for(Object o : resultList){
                menuItem = (MenuItem) o;
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return menuItem;
    }

    @Override
    public void insertOrupdate(MenuItem menuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(menuItem);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        }
        session.close();
    }

    @Override
    public List<String> searchMenuItemByCode(String code) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<String> data = new ArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from MenuItem where code like :code");
            query.setParameter("code", code.toUpperCase()+"%");
            query.setMaxResults(10);
            List resultList = query.list();
            if(null != resultList) {
                for(Object o : resultList) {
                    MenuItem menuItem = (MenuItem) o;
                    Menu menu = menuItem.getMenuId();
                    data.add(menuItem.getCode() + " - " + menu.getNama() + " " + menuItem.getNama());
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return data;
    }

    @Override
    public MenuItem getByCode(String code) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        MenuItem menuItem = new MenuItem();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(MenuItem.class);
            criteria.add(Restrictions.eq("code", code));
            List resultList = criteria.list();
            for(Object o : resultList){
                menuItem = (MenuItem) o;
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return menuItem;
    }

//    @Override
//    public ObservableList<StokOpnameItemProperty> getMenuAndStok(int stokOpnameId, int menuId, int jenisMenuId) {
//        ObservableList<StokOpnameItemProperty> dataProperty = FXCollections.observableArrayList();
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            Transaction tx = session.beginTransaction();
//            String sql = "SELECT s.stok_opname_item_id, s.STOK_TERSEDIA, mi.code, mi.nama menu_item_nama, mi.stok, m.nama menu_nama  FROM APP.MENU_ITEM mi " +
//                    "left join (select soi.id stok_opname_item_id, soi.MENU_ITEM_CODE, soi.STOK_TERSEDIA from app.STOK_OPNAME_ITEM soi join app.STOK_OPNAME so on so.ID = soi.STOK_OPNAME_ID where so.ID = :stokOpnameId) " +
//                    "s on s.menu_item_code = mi.code " +
//                    "join APP.MENU m on m.ID = mi.MENU_ID " +
//                    "where mi.STOK_FLAG = true " +
//                    "and m.status = true ";
//            if(menuId > 0){
//                sql = sql.concat("and m.ID = :menuId ");
//            }
//            if(jenisMenuId > 0) {
//                sql = sql.concat("and m.jenis = :jenisMenuId ");
//            }
//            
//            SQLQuery query = session.createSQLQuery(sql);
//            query.setParameter("stokOpnameId", stokOpnameId);
//            if(menuId > 0) {
//                query.setParameter("menuId", menuId);
//            }
//            if(jenisMenuId > 0 ) {
//                query.setParameter("jenisMenuId", jenisMenuId);
//            }
//            List<Object[]> rows = query.list();
//            if(null != rows && !rows.isEmpty()){
//                for(Object[] row : rows) {
//                    
////                    MenuItem menuItem = (MenuItem) row[0];
////                    StokOpnameItem stokOpnameItem = (StokOpnameItem) row[1];
//                    StokOpnameItemProperty itemProperty = new StokOpnameItemProperty();
//                    if(null!=row[0]){
//                        itemProperty.setId(Integer.valueOf(row[0].toString()));
//                    }
//                    if(null != row[1]) {
//                        itemProperty.setStokTersedia(Integer.valueOf(row[1].toString()));
//                    }
//                    itemProperty.setMenuItemId(Integer.valueOf(row[2].toString()));
//                    itemProperty.setKode(row[3].toString());
//                    itemProperty.setMenuItemNama(row[4].toString());
//                    itemProperty.setStok(Integer.valueOf(row[5].toString()));
//                    itemProperty.setMenuNama(row[6].toString());
//                    itemProperty.setStokOpNameId(stokOpnameId);
//                    dataProperty.add(itemProperty);
//                }
//            }
//            tx.commit();
//        } catch (HibernateException e) {
//            LOGGER.error("failed to select to database", e);
//        }
//        session.close();
//        return dataProperty;
//    }

    @Override
    public void updateStok(ObservableList<StokOpnameItemProperty> itemObsList) {
        itemObsList.sort(Comparator.comparing(StokOpnameItemProperty::getKode));
        Session session = HibernateUtil.getSessionFactory().openSession();
//        List<Integer> menuItemIdList = new ArrayList<>();
//        for(int i = 0; i < itemObsList.size(); i++) {
//            menuItemIdList.add(itemObsList.get(i).getMenuItemId());
//        }
        List<String> menuItemCodeList = new ArrayList<>();
        for(int i = 0; i < itemObsList.size(); i++) {
            menuItemCodeList.add(itemObsList.get(i).getKode());
        }
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from MenuItem where code in (:list) order by code");
            query.setParameterList("list", menuItemCodeList);
            ScrollableResults resultsCursor = query.scroll();
            int count = 0;
            while (resultsCursor.next()) {               
                MenuItem menuItem = (MenuItem) resultsCursor.get(0);
                menuItem.setStok(itemObsList.get(count).getStokTersedia());
                session.update(menuItem);
                if ( ++count % 20 == 0 ) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }

    @Override
    public void updateStokBayar(ObservableList<PesanProperty> itemObsList) {
        itemObsList.sort(Comparator.comparing(PesanProperty::getCode));
        Session session = HibernateUtil.getSessionFactory().openSession();
//        List<Integer> menuItemIdList = new ArrayList<>();
//        for(int i = 0; i < itemObsList.size(); i++) {
//            menuItemIdList.add(itemObsList.get(i).getMenuItemId());
//        }
        List<String> menuItemCodeList = new ArrayList<>();
        for(int i = 0; i < itemObsList.size(); i++) {
            menuItemCodeList.add(itemObsList.get(i).getCode());
        }
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from MenuItem where code in (:list) order by code");
            query.setParameterList("list", menuItemCodeList);
            ScrollableResults resultsCursor = query.scroll();
            int count = 0;
            while (resultsCursor.next()) {               
                MenuItem menuItem = (MenuItem) resultsCursor.get(0);
                if(menuItem.getStokFlag()) {
                    menuItem.setStok(menuItem.getStok() - itemObsList.get(count).getJumlah());
                    session.update(menuItem);
                }
                if ( ++count % 20 == 0 ) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }

    @Override
    public List<String> searchMenuItemByCodeOnStokOpname(String code, ObservableList<StokOpnameItemProperty> obsList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<String> data = new ArrayList();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from MenuItem where code like :code and stokFlag = true and code not in (:list)");
            List<String> codeList = new ArrayList<>();
//            obsList.forEach((StokOpnameItemProperty itemProperty) -> {
//                codeList.add(itemProperty.getKode());
//            });
            if(obsList.size() > 0) {
                for(StokOpnameItemProperty itemProperty : obsList){
                    codeList.add(itemProperty.getKode());
                }
            }else{
                codeList.add("");
            }
            
            query.setParameter("code", code.toUpperCase()+"%");
            query.setParameterList("list", codeList);
            query.setMaxResults(10);
            List resultList = query.list();
            if(null != resultList) {
                for(Object o : resultList) {
                    MenuItem menuItem = (MenuItem) o;
                    Menu menu = menuItem.getMenuId();
                    data.add(menuItem.getCode() + " - " + menu.getNama() + " " + menuItem.getNama());
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return data;
    }

    @Override
    public boolean insert(MenuItem menuItem, Stage primaryStage) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.save(menuItem);
            tx.commit();
        }
        catch (ConstraintViolationException cve) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Nomor Sudah Terpakai");
            alert.setContentText("Silahkan masukkan nomor yang lain");
            alert.showAndWait();
            result = false;
            LOGGER.error("ConstraintViolationException: failed to insert to database", cve);
        } 
        catch (JDBCException jdbce) {
            String sqlSate = jdbce.getSQLException().getNextException().getSQLState();
            if(sqlSate.equalsIgnoreCase("23505")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(primaryStage);
                alert.setTitle("Salah!");
                alert.setHeaderText("Kode Sudah Terpakai");
                alert.setContentText("Silahkan masukkan kode yang lain");
                alert.showAndWait();
                LOGGER.debug("JDBCException ALERT");
            }
            LOGGER.error("JDBCException: failed to insert to database", jdbce);
            result = false;
        }
        catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
            result = false;
        } 
        finally{
            session.close();
        }
        return result;
    }

    @Override
    public void update(MenuItem menuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            session.update(menuItem);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        }
        session.close();
    }

    @Override
    public boolean cekStokBayar(ObservableList<PesanProperty> itemObsList, Stage primaryStage) {
        boolean result = true;
        String errorMessage = "";
        itemObsList.sort(Comparator.comparing(PesanProperty::getCode));
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<String> menuItemCodeList = new ArrayList<>();
        for(int i = 0; i < itemObsList.size(); i++) {
            menuItemCodeList.add(itemObsList.get(i).getCode());
        }
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from MenuItem where code in (:list) order by code");
            query.setParameterList("list", menuItemCodeList);
            List<MenuItem> resultList = query.list();
            if(null != resultList){
                for(int i = 0; i < resultList.size(); i++) {
                    MenuItem menuItem = (MenuItem) resultList.get(i);
                    int sisaStok = menuItem.getStok() - itemObsList.get(i).getJumlah();
                    if(menuItem.getStokFlag() && sisaStok < 0) {
                        errorMessage += "Stok " + itemObsList.get(i).getMenuNama() + " " + itemObsList.get(i).getMenuItemNama() + " = " + menuItem.getStok() + "\n";
                    }
                }
                if(errorMessage.length() > 0){
                    // Show the error message.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(primaryStage);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Stok Tidak Mencukupi");
                    alert.setContentText(errorMessage);

                    alert.showAndWait();
                    
                    result = false;
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return result;
    }
    
}
