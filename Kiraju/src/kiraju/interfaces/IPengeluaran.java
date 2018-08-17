/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.time.LocalDate;
import java.util.List;
import javafx.collections.ObservableList;
import kiraju.model.Pengeluaran;
import kiraju.property.PengeluaranProperty;

/**
 *
 * @author arvita
 */
public interface IPengeluaran {
    ObservableList<PengeluaranProperty> getByDate(LocalDate date);
    int insert(Pengeluaran pengeluaran);
    void update(Pengeluaran pengeluaran);
    void delete(int id);
    List getChartByBulan(int bulan);
    List getChartByTahun(int tahun);
}
