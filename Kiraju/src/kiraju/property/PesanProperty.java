/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.property;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author arvita
 */
public class PesanProperty {
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final IntegerProperty jumlah = new SimpleIntegerProperty();
    private final StringProperty harga = new SimpleStringProperty();
    private final IntegerProperty transaksiId = new SimpleIntegerProperty();
    private final IntegerProperty jenisId = new SimpleIntegerProperty();
    private final IntegerProperty menuId = new SimpleIntegerProperty();
    private final IntegerProperty totalHarga = new SimpleIntegerProperty();
    private final IntegerProperty mejaId = new SimpleIntegerProperty();
    private final StringProperty namaPemesan = new SimpleStringProperty();
    private final StringProperty mejaNama = new SimpleStringProperty();
    private String hargaNumberFormat;
    
    //added by Arvit@20170831 - retail version
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty menuNama = new SimpleStringProperty();
//    private final IntegerProperty modal = new SimpleIntegerProperty();
//    private final IntegerProperty untungCode = new SimpleIntegerProperty();
//    private final IntegerProperty untung = new SimpleIntegerProperty();
//    private final IntegerProperty tambahanCode = new SimpleIntegerProperty();
//    private final IntegerProperty tambahan = new SimpleIntegerProperty();
    private final StringProperty menuItemNama = new SimpleStringProperty();
    private final StringProperty diskonNama = new SimpleStringProperty();
    private final StringProperty pajakNama = new SimpleStringProperty();
    private final IntegerProperty diskonId = new SimpleIntegerProperty();
    private final IntegerProperty pajakId = new SimpleIntegerProperty();
//    private final IntegerProperty totalModal = new SimpleIntegerProperty();
    private String jumlahNumberFormat;
    
    //20180417 - kiraju4
    private final StringProperty produk = new SimpleStringProperty();
    private final StringProperty banyak = new SimpleStringProperty();
    private final StringProperty hargaJual = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty(){
        return id;
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }
    
    public StringProperty namaProperty() {
        return nama;
    }

    public Integer getJumlah() {
        return jumlah.get();
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah.set(jumlah);
    }
    
    public IntegerProperty jumlahProperty() {
        return jumlah;
    }
    
    public Integer getHarga() {
        return Integer.valueOf(harga.get().replace(".", ""));
    }

    public void setHarga(Integer harga) {
        this.harga.set(numberFormat.format(harga));
        setHargaNumberFormat(this.harga.get());
    }
    
    public StringProperty hargaProperty() {
        return harga;
    }
    
    public int getTransaksiId() {
        return transaksiId.get();
    }

    public void setTransaksiId(int transaksiId) {
        this.transaksiId.set(transaksiId);
    }
    
    public IntegerProperty transaksiIdProperty() {
        return transaksiId;
    }
    
    public short getJenisId() {
        return (short) jenisId.get();
    }

    public void setJenisId(short jenisId) {
        this.jenisId.set(jenisId);
    }
    
    public IntegerProperty jenisIdProperty() {
        return jenisId;
    }
    
    public int getMenuId() {
        return menuId.get();
    }

    public void setMenuId(int menuId) {
        this.menuId.set(menuId);
    }
    
    public IntegerProperty menuIdProperty() {
        return menuId;
    }
    
    public Integer getTotalHarga() {
        return totalHarga.get();
    }

    public void setTotalHarga(Integer totalHarga) {
        this.totalHarga.set(totalHarga);
    }
    
    public IntegerProperty totalHargaProperty() {
        return totalHarga;
    }

    public short getMejaId() {
        return (short) mejaId.get();
    }

    public void setMejaId(short mejaId) {
        this.mejaId.set(mejaId);
    }
    
    public IntegerProperty mejaIdProperty() {
        return mejaId;
    }

    public String getNamaPemesan() {
        return namaPemesan.get();
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan.set(namaPemesan);
    }
    
    public StringProperty namaPemesanProperty() {
        return namaPemesan;
    }

    public String getMejaNama() {
        return mejaNama.get();
    }

    public void setMejaNama(String mejaNama) {
        this.mejaNama.set(mejaNama);
    }
    
    public StringProperty mejaNama() {
        return mejaNama;
    }

    public String getHargaNumberFormat() {
        return hargaNumberFormat;
    }

    public void setHargaNumberFormat(String hargaNumberFormat) {
        this.hargaNumberFormat = hargaNumberFormat;
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }
    
    public StringProperty codeProperty() {
        return code;
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
//    public Integer getUntungCode() {
//        return untungCode.get();
//    }
//
//    public void setUntungCode(Integer untungCode) {
//        this.untungCode.set(untungCode);
//    }
//    
//    public IntegerProperty untungCodeProperty() {
//        return untungCode;
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
//
//    public Integer getTambahanCode() {
//        return tambahanCode.get();
//    }
//
//    public void setTambahanCode(Integer tambahanCode) {
//        this.tambahanCode.set(tambahanCode);
//    }
//    
//    public IntegerProperty tambahanCodeProperty() {
//        return tambahanCode;
//    }
//
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

    public String getMenuItemNama() {
        return menuItemNama.get();
    }

    public void setMenuItemNama(String menuItemNama) {
        this.menuItemNama.set(menuItemNama);
    }
    
    public StringProperty menuItemNamaProperty() {
        return menuItemNama;
    }

    public String getDiskonNama() {
        return diskonNama.get();
    }

    public void setDiskonNama(String diskonNama) {
        this.diskonNama.set(diskonNama);
    }
    
    public StringProperty diskonNamaProperty() {
        return diskonNama;
    }

    public String getPajakNama() {
        return pajakNama.get();
    }

    public void setPajakNama(String pajakNama) {
        this.pajakNama.set(pajakNama);
    }
    
    public StringProperty pajakNamaProperty() {
        return pajakNama;
    }

    public Integer getDiskonId() {
        return diskonId.get();
    }

    public void setDiskonId(Integer diskonId) {
        this.diskonId.set(diskonId);
    }
    
    public IntegerProperty diskonIdProperty() {
        return diskonId;
    }

    public Integer getPajakId() {
        return pajakId.get();
    }

    public void setPajakId(Integer pajakId) {
        this.pajakId.set(pajakId);
    }
    
    public IntegerProperty pajakIdProperty() {
        return pajakId;
    }

    public String getJumlahNumberFormat() {
        return numberFormat.format(jumlah.get());
    }

    public void setJumlahNumberFormat(String jumlahNumberFormat) {
        this.jumlahNumberFormat = jumlahNumberFormat;
    }

//    public Integer getTotalModal() {
//        return totalModal.get();
//    }
//
//    public void setTotalModal(Integer totalModal) {
//        this.totalModal.set(totalModal);
//    }
//    
//    public IntegerProperty totalModalProperty() {
//        return totalModal;
//    }

    public String getProduk() {
        return produk.get();
    }

    public void setProduk(String produk) {
        this.produk.set(produk);
    }
    
    public StringProperty produkProperty() {
        return produk;
    }

    public String getBanyak() {
        return banyak.get();
    }

    public void setBanyak(String banyak) {
        this.banyak.set(banyak);
    }
    
    public StringProperty banyakProperty() {
        return banyak;
    }

    public String getHargaJual() {
        return hargaJual.get();
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual.set(hargaJual);
    }
    
    public StringProperty hargaJualProperty() {
        return hargaJual;
    }
    
}
