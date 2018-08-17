/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.interfaces.IGeneral;
import kiraju.model.Diskon;
import kiraju.model.General;
import kiraju.property.DiskonPajakProperty;
import kiraju.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 *
 * @author arvita
 */
public class GeneralModel implements IGeneral{
    
    private final static Logger LOGGER = Logger.getLogger(GeneralModel.class);

    @Override
    public General getGeneral() {
        General general = new General();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(General.class);
            List<General> resultList = criteria.list();
            if(null != resultList && !resultList.isEmpty()) {
                general = resultList.get(0);
            }
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return general;
    }

    @Override
    public void update(General general) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("update General set modeCafe = :modeCafe, printerCode = :printerCode");
            query.setParameter("modeCafe", general.getModeCafe());
            query.setParameter("printerCode", general.getPrinterCode());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            LOGGER.error("failed to update to database", e);
        } finally {
            session.close();
        }
    }
    
}
