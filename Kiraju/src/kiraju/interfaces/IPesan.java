/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.util.List;
import javafx.collections.ObservableList;
import kiraju.model.Pesan;
import kiraju.property.PesanProperty;

/**
 *
 * @author arvita
 */
public interface IPesan {
    List<Pesan> getAll();
    int insert(PesanProperty pesanProperty);
    void update(PesanProperty pesanProperty);
    ObservableList<PesanProperty> getDetailByTransaksiId(int transaksiId);
    void deleteByTransaksiId(int transaksiId);
}
