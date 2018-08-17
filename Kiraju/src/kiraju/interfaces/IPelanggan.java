/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.util.List;
import javafx.collections.ObservableList;
import kiraju.model.Pelanggan;
import kiraju.property.PelangganProperty;

/**
 *
 * @author arvita
 */
public interface IPelanggan {
    ObservableList<PelangganProperty> getAllProp();
    void insertOrUpdate(Pelanggan pelanggan);
    List<String> searchPelangganById(String id);
}
