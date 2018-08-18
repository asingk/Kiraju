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
import kiraju.model.JenisMenu;
import kiraju.model.MenuItem;
import kiraju.property.MenuItemProperty;
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

    @Override
    public void updateStok(ObservableList<StokOpnameItemProperty> itemObsList) {
        itemObsList.sort(Comparator.comparing(StokOpnameItemProperty::getKode));
        Session session = HibernateUtil.getSessionFactory().openSession();
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
//                    Menu menu = menuItem.getMenuId();
                    data.add(menuItem.getCode() + " - " + menuItem.getNama());
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
            alert.setHeaderText("Kode Sudah Terpakai");
            alert.setContentText("Silahkan masukkan kode yang lain");
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
    
    @Override
    public ObservableList<MenuItemProperty> getAllProperty(int jenisId) {
        ObservableList<MenuItemProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            String query = jenisId == 0 ? "from MenuItem m join m.jenisMenuId j where j.status = true order by m.status desc, j.id, m.id" : "from MenuItem m join m.jenisMenuId j where j.status = true and j.id = " + jenisId + " order by m.status desc, j.id, m.id";
            List<Object[]> obj = session.createQuery(query).list();
            if(null != obj){
                for(Object[] row : obj){
                    MenuItemProperty menuItemProp = new MenuItemProperty();
                    MenuItem menuItem = (MenuItem) row[0];
                    JenisMenu jenisMenu = (JenisMenu) row[1];
//                    menuProp.setId(menu.getId());
                    menuItemProp.setNama(menuItem.getNama());
                    menuItemProp.setCode(menuItem.getCode());
                    menuItemProp.setHargaJual(menuItem.getHargaTotal());
//                    menuProp.setCreatedDate(menu.getCreatedDt());
//                    menuProp.setUpdatedDate(menu.getUpdatedDt());
//                    menuProp.setDeletedFlag(menu.getDeletedFlag());
                    menuItemProp.setStatus(menuItem.getStatus());
                    menuItemProp.setJenisId(jenisMenu.getId());
                    menuItemProp.setJenisNama(jenisMenu.getNama());
//                    menuProp.setJenisCode(jenisMenu.getCode());
                    menuItemProp.setStokFlag(menuItem.getStokFlag());
                    if(menuItem.getStokFlag()){
                        menuItemProp.setStok(menuItem.getStok().toString());
                    }
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
    public ObservableList<MenuItemProperty> getActiveProperty(int jenisId) {
        ObservableList<MenuItemProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            String query = jenisId == 0 ? "from MenuItem m join m.jenisMenuId j where j.status = true and m.status = true order by j.id, m.id" : "from MenuItem m join m.jenisMenuId j where j.status = true and j.id = " + jenisId + " and m.status = true order by j.id, m.id";
            List<Object[]> obj = session.createQuery(query).list();
            if(null != obj){
                for(Object[] row : obj){
                    MenuItemProperty menuItemProp = new MenuItemProperty();
                    MenuItem menuItem = (MenuItem) row[0];
//                    JenisMenu jenisMenu = (JenisMenu) row[1];
//                    menuItemProp.setId(menu.getId());
                    menuItemProp.setNama(menuItem.getNama());
                    menuItemProp.setCode(menuItem.getCode());
//                    menuProp.setJenisId(jenisMenu.getId());
//                    menuProp.setJenisNama(jenisMenu.getNama());
                    dataProperty.add(menuItemProp);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return dataProperty;
    }
    
}
