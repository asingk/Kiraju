/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.util.List;
import javafx.collections.ObservableList;
import kiraju.model.MenuItem;
import kiraju.model.Pelanggan;
import kiraju.model.Pesan;
import kiraju.model.Transaksi;
import kiraju.property.PesanProperty;

/**
 *
 * @author arvita
 */
public interface IPesan {
    List<Pesan> getAll();
    int insert(Pesan pesan);
    void update(Pesan pesan);
    ObservableList<PesanProperty> getDetailByTransaksiId(int transaksiId);
    void deleteByTransaksiId(int transaksiId);
//    void insertAll(List<PesanProperty> pesanPropList, int transaksiId);
//    void deleteByMenuIdAndTransaksiId(int transaksiId, int menuId);
    void deleteById(Pesan pesan);
    List<Pesan> getPieChartByPelanggan(Pelanggan pelanggan);
    List<Object[]> getByMenuItemAndTransaksi(MenuItem menuItem, Transaksi transaksi);
}
