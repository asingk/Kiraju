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
    private final StringProperty jumlah = new SimpleStringProperty();
    private final StringProperty harga = new SimpleStringProperty();
    private final IntegerProperty transaksiId = new SimpleIntegerProperty();
    private final IntegerProperty jenisId = new SimpleIntegerProperty();
    private final IntegerProperty menuId = new SimpleIntegerProperty();
    private final IntegerProperty totalHarga = new SimpleIntegerProperty();
    private final IntegerProperty mejaId = new SimpleIntegerProperty();
    private final StringProperty namaPemesan = new SimpleStringProperty();
    private final StringProperty mejaNama = new SimpleStringProperty();
    private String hargaNumberFormat;

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

    public String getJumlah() {
        return jumlah.get();
    }

    public void setJumlah(String jumlah) {
        this.jumlah.set(jumlah);
    }
    
    public StringProperty jumlahProperty() {
        return jumlah;
    }
    
    public String getHarga() {
        return harga.get().replace(".", "");
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
    
    public short getMenuId() {
        return (short) menuId.get();
    }

    public void setMenuId(short menuId) {
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
    
}
