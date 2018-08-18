/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import javafx.collections.ObservableList;
import kiraju.model.TransaksiPembelian;
import kiraju.property.TransaksiPembelianProperty;

/**
 *
 * @author arvita
 */
public interface ITransaksiPembelian {
    ObservableList<TransaksiPembelianProperty> getAllProp(int bulan);
    int insertOrUpdate(TransaksiPembelian pembelian);
    void remove(TransaksiPembelian pembelian);
}
