/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import javafx.collections.ObservableList;
import kiraju.model.StokOpname;
import kiraju.property.StokOpnameProperty;

/**
 *
 * @author arvita
 */
public interface IStokOpname {
    ObservableList<Integer> getYear();
    ObservableList<StokOpnameProperty> getByBulan(int tahun, int bulan);
    int insert(StokOpname stokOpname);
    void update(StokOpname stokOpname);
    void delete(StokOpname stokOpname);
    void updateStatus(StokOpname stokOpname);
}
