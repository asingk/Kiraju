/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import kiraju.model.MenuItem;
import kiraju.property.MenuItemProperty;
import kiraju.property.PesanProperty;
import kiraju.property.StokOpnameItemProperty;

/**
 *
 * @author arvita
 */
public interface IMenuItem {
//    ObservableList<PesanProperty> getByMenuIdAndJumlah(int menuId, int transaksiId);
//    ObservableList<MenuItemProperty> getPropertyByMenuId(int menuId);
    MenuItem getById(int id);
    void insertOrupdate(MenuItem menuItem);
//    List<String> searchMenuItemByCode(String code);
    MenuItem getByCode(String code);
//    ObservableList<StokOpnameItemProperty> getMenuAndStok(int stokOpnameId, int menuId, int jenisMenuId);
    void updateStok(ObservableList<StokOpnameItemProperty> itemObsList);
    void updateStokBayar(ObservableList<PesanProperty> itemObsList);
    List<String> searchMenuItemByCodeOnStokOpname(String code, ObservableList<StokOpnameItemProperty> obsList);
    boolean insert(MenuItem menuItem, Stage primaryStage);
    void update(MenuItem menuItem);
    boolean cekStokBayar(ObservableList<PesanProperty> itemObsList, Stage primaryStage);
    
    ObservableList<MenuItemProperty> getAllProperty(int jenisId);
    ObservableList<MenuItemProperty> getActiveProperty(int jenisId);
}
