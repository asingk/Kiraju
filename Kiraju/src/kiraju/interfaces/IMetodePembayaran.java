/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.util.List;
import javafx.collections.ObservableList;
import kiraju.model.MetodePembayaran;
import kiraju.property.MetodePembayaranProperty;

/**
 *
 * @author arvita
 */
public interface IMetodePembayaran {
    List<MetodePembayaran> getAll();
    ObservableList<String> getAllNama();
    int getIdByName(String nama);
    ObservableList<MetodePembayaranProperty> getAllProperty();
    void insertOrUpdate(MetodePembayaran metodePembayaran);
}
