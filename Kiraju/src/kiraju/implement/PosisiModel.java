/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.implement;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kiraju.interfaces.IPosisi;
import kiraju.model.Posisi;
import kiraju.property.PosisiProperty;
import kiraju.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author arvita
 */
public class PosisiModel implements IPosisi {
    
    private final static Logger logger = Logger.getLogger(PosisiModel.class);

    @Override
    public ObservableList<PosisiProperty> getAll() {
        ObservableList<PosisiProperty> dataProperty = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            List<Posisi> obj = session.createQuery("from Posisi where id > 2").list();
            if(obj != null){
                obj.stream().map((posisi) -> {
                    PosisiProperty posisiProperty = new PosisiProperty();
                    posisiProperty.setId(posisi.getId());
                    posisiProperty.setNama(posisi.getNama());
                    return posisiProperty;                    
                }).forEachOrdered((posisiProperty) -> {
                    dataProperty.add(posisiProperty);
                });
            }
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        } finally {
            session.close();
        }
        return dataProperty;

    }

    @Override
    public short getIdByName(String nama) {
        short id = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Posisi.class).add(Restrictions.eq("nama", nama));
            List<Posisi> obj = criteria.list();
            for(int i = 0; i < obj.size(); i++){
                id = obj.get(i).getId();
            }
            tx.commit();
        } catch (HibernateException e) {
            logger.error("failed to select to database", e);
        }finally {
            session.close();
        }
        return id;
    }
    
}
