/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.util.List;
import javafx.collections.ObservableList;
import kiraju.model.Menu;
import kiraju.property.MenuProperty;
import kiraju.property.PesanProperty;

/**
 *
 * @author arvita
 */
public interface IMenu {
    ObservableList<MenuProperty> getAllProperty(short jenisId);
    short insert(Menu menu);
    void update(Menu menu);
    void delete(short id);
    ObservableList<PesanProperty> getAllAndJumlah(short jenisId, int transaksiId);
    List getChartByMonthAndJenisMenu(int bulan, Short jenisMenu);
}
