/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import javafx.collections.ObservableList;
import kiraju.model.TransaksiPembelian;
import kiraju.property.DaftarPembelianProperty;

/**
 *
 * @author arvita
 */
public interface IDaftarPembelian {
    void insertList(ObservableList<DaftarPembelianProperty> data, int trxPembelianId);
    ObservableList<DaftarPembelianProperty> getListBeli(TransaksiPembelian pembelian);
    void deleteByTrxPembelianId(TransaksiPembelian pembelian);
}
