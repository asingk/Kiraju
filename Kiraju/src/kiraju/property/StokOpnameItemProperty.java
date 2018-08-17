/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.property;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author arvita
 */
public class StokOpnameItemProperty {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty stokOpNameId = new SimpleIntegerProperty();
//    private final IntegerProperty menuItemId = new SimpleIntegerProperty();
    private final IntegerProperty stok = new SimpleIntegerProperty();
    private final IntegerProperty stokTersedia = new SimpleIntegerProperty();
    private final StringProperty kode = new SimpleStringProperty();
    private final StringProperty menuNama = new SimpleStringProperty();
    private final StringProperty menuItemNama = new SimpleStringProperty();
    private final IntegerProperty selisih = new SimpleIntegerProperty();
//    private final StringProperty menuItemCode = new SimpleStringProperty();
    private final StringProperty ket = new SimpleStringProperty();

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }
    
    public IntegerProperty idproperty() {
        return id;
    }

    public Integer getStokOpNameId() {
        return stokOpNameId.get();
    }

    public void setStokOpNameId(Integer stokOpNameId) {
        this.stokOpNameId.set(stokOpNameId);
    }
    
    public IntegerProperty stokOpnameIdProperty() {
        return stokOpNameId;
    }

//    public Integer getMenuItemId() {
//        return menuItemId.get();
//    }
//
//    public void setMenuItemId(Integer menuItemId) {
//        this.menuItemId.set(menuItemId);
//    }
//    
//    public IntegerProperty menuItemIdProperty() {
//        return menuItemId;
//    }

    public Integer getStok() {
        return stok.get();
    }

    public void setStok(Integer stok) {
        this.stok.set(stok);
    }
    
    public IntegerProperty stokProperty() {
        return stok;
    }

    public Integer getStokTersedia() {
        return stokTersedia.get();
    }

    public void setStokTersedia(Integer stokTersedia) {
        this.stokTersedia.set(stokTersedia);
    }
    
    public IntegerProperty stokTersediaProperty() {
        return stokTersedia;
    }

    public String getKode() {
        return kode.get();
    }

    public void setKode(String kode) {
        this.kode.set(kode);
    }
    
    public StringProperty kodeProperty() {
        return kode;
    }

    public String getMenuNama() {
        return menuNama.get();
    }

    public void setMenuNama(String menuNama) {
        this.menuNama.set(menuNama);
    }
    
    public StringProperty menuNamaProperty() {
        return menuNama;
    }

    public String getMenuItemNama() {
        return menuItemNama.get();
    }

    public void setMenuItemNama(String menuItemNama) {
        this.menuItemNama.set(menuItemNama);
    }
    
    public StringProperty menuItemNamaProperty() {
        return menuItemNama;
    }

    public Integer getSelisih() {
        return selisih.get();
    }

    public void setSelisih(Integer selisih) {
        this.selisih.set(selisih);
    }
    
    public IntegerProperty selisihProperty() {
        return selisih;
    }
    
//    public String getMenuItemCode() {
//        return menuItemCode.get();
//    }
//
//    public void setMenuItemCode(String menuItemCode) {
//        this.menuItemCode.set(menuItemCode);
//    }
//    
//    public StringProperty menuItemCodeProperty() {
//        return menuItemCode;
//    }

    public String getKet() {
        return ket.get();
    }

    public void setKet(String ket) {
        this.ket.set(ket);
    }
    
    public StringProperty ketProperty() {
        return ket;
    }
}
