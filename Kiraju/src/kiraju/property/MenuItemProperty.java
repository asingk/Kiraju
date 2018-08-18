/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.property;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author arvita
 */
public class MenuItemProperty {
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    
//    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
//    private final StringProperty harga = new SimpleStringProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final BooleanProperty stokFlag = new SimpleBooleanProperty();
    private final StringProperty stok = new SimpleStringProperty();
//    private final IntegerProperty modal = new SimpleIntegerProperty();
//    private final IntegerProperty untung = new SimpleIntegerProperty();
//    private final IntegerProperty untungCode = new SimpleIntegerProperty();
//    private final IntegerProperty tambahan = new SimpleIntegerProperty();
//    private final IntegerProperty tambahanCode = new SimpleIntegerProperty();
    private final StringProperty hargaJual = new SimpleStringProperty();
    //20171229 - kirajulite
    private final StringProperty status = new SimpleStringProperty();
    private final IntegerProperty jenisId = new SimpleIntegerProperty();
    private final StringProperty jenisNama = new SimpleStringProperty();

//    public Integer getId() {
//        return id.get();
//    }
//
//    public void setId(Integer id) {
//        this.id.set(id);
//    }
//    
//    public IntegerProperty idProperty() {
//        return id;
//    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }
    
    public StringProperty namaProperty() {
        return nama;
    }

//    public String getHarga() {
//        return harga.get();
//    }
//
//    public void setHarga(int harga) {
//        this.harga.set(numberFormat.format(harga));
//    }
//    
//    public StringProperty hargaProperty() {
//        return harga;
//    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }
    
    public StringProperty codeProperty() {
        return code;
    }

    public String getStok() {
        return stok.get();
    }

    public void setStok(String stok) {
        this.stok.set(stok);
    }
    public StringProperty stokProperty() {
        return stok;
    }

//    public Integer getModal() {
//        return modal.get();
//    }
//
//    public void setModal(Integer modal) {
//        this.modal.set(modal);
//    }
//    
//    public IntegerProperty modalProperty() {
//        return modal;
//    }
//
//    public Integer getUntung() {
//        return untung.get();
//    }
//
//    public void setUntung(Integer untung) {
//        this.untung.set(untung);
//    }
//    
//    public IntegerProperty untungProperty() {
//        return untung;
//    }

//    public Integer getTambahan() {
//        return tambahan.get();
//    }
//
//    public void setTambahan(Integer tambahan) {
//        this.tambahan.set(tambahan);
//    }
//    
//    public IntegerProperty tambahanProperty() {
//        return tambahan;
//    }

    public String getHargaJual() {
        return hargaJual.get();
    }

    public void setHargaJual(int hargaJual) {
        this.hargaJual.set(numberFormat.format(hargaJual));
    }
    
    public StringProperty hargaJualProperty() {
        return hargaJual;
    }

//    public Integer getUntungCode() {
//        return untungCode.get();
//    }
//
//    public void setUntungCode(Integer untungCode) {
//        this.untungCode.set(untungCode);
//    }
//    
//    public IntegerProperty untungCodeProp() {
//        return untungCode;
//    }

//    public Integer getTambahanCode() {
//        return tambahanCode.get();
//    }
//
//    public void setTambahanCode(Integer tambahanCode) {
//        this.tambahanCode.set(tambahanCode);
//    }
//    
//    public IntegerProperty tambahanCode() {
//        return tambahanCode;
//    }

    public Boolean getStokFlag() {
        return stokFlag.get();
    }

    public void setStokFlag(Boolean stokFlag) {
        this.stokFlag.set(stokFlag);
    }
    
    public BooleanProperty stokFlagProperty() {
        return stokFlag;
    }
    
    public Boolean getStatus() {
        Boolean statusBoolean = status.get().equalsIgnoreCase("Ya") ? Boolean.TRUE : Boolean.FALSE;
        return statusBoolean;
    }

    public void setStatus(Boolean status) {
        String statusString = status ? "Ya" : "Tidak";
        this.status.set(statusString);
    }
    
    public StringProperty statusProperty() {
        return status;
    }
    
    /**
     * @return the jenisId
     */
    public int getJenisId() {
        return jenisId.get();
    }

    /**
     * @param jenisId the jenisId to set
     */
    public void setJenisId(int jenisId) {
        this.jenisId.set(jenisId);
    }
    
    public IntegerProperty jenisIdProperty() {
        return jenisId;
    }

    /**
     * @return the jenisNama
     */
    public String getJenisNama() {
        return jenisNama.get();
    }

    /**
     * @param jenisNama the jenisNama to set
     */
    public void setJenisNama(String jenisNama) {
        this.jenisNama.set(jenisNama);
    }

    public StringProperty jenisNamaProperty() {
        return jenisNama;
    }
}
