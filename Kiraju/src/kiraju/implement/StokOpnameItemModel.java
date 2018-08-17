/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.interfaces.IStokOpnameItem;
import kiraju.model.Menu;
import kiraju.model.MenuItem;
import kiraju.model.StokOpname;
import kiraju.model.StokOpnameItem;
import kiraju.property.StokOpnameItemProperty;
import kiraju.property.StokOpnameProperty;
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
public class StokOpnameItemModel implements IStokOpnameItem{
    
    private final static Logger LOGGER = Logger.getLogger(StokOpnameItemModel.class);

    @Override
    public ObservableList<StokOpnameItemProperty> getByStokOpnameId(StokOpnameProperty stokOpnameProperty) {
        ObservableList<StokOpnameItemProperty> resultObsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from StokOpnameItem soi join soi.menuItemCode mi join mi.menuId m where soi.stokOpnameId = :stokOpnameId");
            query.setParameter("stokOpnameId", new StokOpname(stokOpnameProperty.getId()));
            List<Object[]> resultList = query.list();
            if(null != resultList && !resultList.isEmpty()) {
                for(Object[] obj : resultList){
                    StokOpnameItem stokOpnameItem = (StokOpnameItem) obj[0];
                    MenuItem menuItem = (MenuItem) obj[1];
                    Menu menu = (Menu) obj[2];
                    
                    StokOpnameItemProperty itemProperty = new StokOpnameItemProperty();
                    itemProperty.setId(stokOpnameItem.getId());
                    itemProperty.setKode(menuItem.getCode());
                    itemProperty.setMenuNama(menu.getNama());
//                    itemProperty.setMenuItemId(menuItem.getId());
                    itemProperty.setMenuItemNama(menuItem.getNama());
                    itemProperty.setStok(stokOpnameItem.getStokAwal());
                    itemProperty.setStokTersedia(stokOpnameItem.getStokTersedia());
                    itemProperty.setSelisih(stokOpnameItem.getStokSelisih());
                    itemProperty.setKet(stokOpnameItem.getKet());
                    
                    resultObsList.add(itemProperty);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        }
        session.close();
        return resultObsList;
    }

//    @Override
//    public int insert(StokOpnameItemProperty itemProperty) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        StokOpnameItem item = new StokOpnameItem();
//        item.setId(itemProperty.getId());
//        item.setMenuItemId(new MenuItem(itemProperty.getMenuItemId()));
//        item.setStokOpnameId(new StokOpname(itemProperty.getStokOpNameId()));
//        item.setStokTersedia(itemProperty.getStokTersedia());
//        item.setStokSelisih(itemProperty.getStok() - itemProperty.getStokTersedia());
//        item.setStokAwal(itemProperty.getStok());
//        try {
//            Transaction tx = session.beginTransaction();
//            session.save(item);
//            tx.commit();
//        } catch (HibernateException e) {
//            LOGGER.error("failed to insert to database", e);
//        }
//        session.close();
//        return itemProperty.getId();
//    }

//    @Override
//    public void update(StokOpnameItemProperty itemProperty) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction tx;
//        try {
//            tx = session.beginTransaction();
//            Query query =  session.createQuery("update StokOpnameItem set stokTersedia = :stokTersedia, stokSelisih = :stokSelisih where id = :id");
//            query.setParameter("stokTersedia", itemProperty.getStokTersedia());
//            query.setParameter("stokSelisih", itemProperty.getStok() - itemProperty.getStokTersedia());
//            query.setParameter("id", itemProperty.getId());
//            query.executeUpdate();
//            tx.commit();
//        } catch (HibernateException e) {
//            LOGGER.error("failed to update to database", e);
//        }
//        session.close();
//    }

    @Override
    public void insert(StokOpnameItem item) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(item);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        }
        session.close();
    }

    @Override
    public void update(StokOpnameItem item) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Query query =  session.createQuery("update StokOpnameItem set stokTersedia = :stokTersedia, stokSelisih = :stokSelisih, ket = :ket where id = :id");
            query.setParameter("stokTersedia", item.getStokTersedia());
            query.setParameter("stokSelisih", item.getStokSelisih());
            query.setParameter("ket", item.getKet());
            query.setParameter("id", item.getId());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        }
        session.close();
    }

    @Override
    public void delete(StokOpnameItem item) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.delete(item);
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to insert to database", e);
        }
        session.close();
    }
    
}
