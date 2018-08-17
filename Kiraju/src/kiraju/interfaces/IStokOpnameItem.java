/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import javafx.collections.ObservableList;
import kiraju.model.StokOpnameItem;
import kiraju.property.StokOpnameItemProperty;
import kiraju.property.StokOpnameProperty;

/**
 *
 * @author arvita
 */
public interface IStokOpnameItem {
    ObservableList<StokOpnameItemProperty> getByStokOpnameId(StokOpnameProperty stokOpnameProperty);
//    int insert(StokOpnameItemProperty itemProperty);
//    void update(StokOpnameItemProperty itemProperty);
    void insert(StokOpnameItem item);
    void update(StokOpnameItem item);
    void delete(StokOpnameItem item);
}
