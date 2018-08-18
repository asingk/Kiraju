/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.property;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class TransaksiPembelianProperty {
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty total = new SimpleStringProperty();
    private final StringProperty pemasokNama = new SimpleStringProperty();
    private final StringProperty produk = new SimpleStringProperty();
    private final StringProperty harga = new SimpleStringProperty();
    private final StringProperty jumlah = new SimpleStringProperty();
    private final IntegerProperty pemasokId = new SimpleIntegerProperty();
    private final BooleanProperty status = new SimpleBooleanProperty();
    private final BooleanProperty isLunas = new SimpleBooleanProperty();

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty() {
        return id;
    }

    public String getTanggal() {
        return tanggal.get();
    }

    public void setTanggal(Date tanggal) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String out = dateFormat.format(tanggal);
        this.tanggal.set(out);
    }
    
    public StringProperty tanggalProperty() {
        return tanggal;
    }

    public String getTotal() {
        return total.get();
    }

    public void setTotal(int total) {
        this.total.set(numberFormat.format(total));
    }
    
    public StringProperty totalProperty() {
        return total;
    }

    public String getPemasokNama() {
        return pemasokNama.get();
    }

    public void setPemasokNama(String pemasokNama) {
        this.pemasokNama.set(pemasokNama);
    }
    
    public StringProperty pemasokNamaProperty() {
        return pemasokNama;
    }

    public String getProduk() {
        return produk.get();
    }

    public void setProduk(String produk) {
        this.produk.set(produk);
    }
    
    public StringProperty produkProperty() {
        return produk;
    }

    public String getHarga() {
        return harga.get();
    }

    public void setHarga(int harga) {
        this.harga.set(numberFormat.format(harga));
    }
    
    public StringProperty hargaProperty() {
        return harga;
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

    public Integer getPemasokId() {
        return pemasokId.get();
    }

    public void setPemasokId(Integer pemasokId) {
        this.pemasokId.set(pemasokId);
    }
    
    public IntegerProperty pemasokIdProperty() {
        return pemasokId;
    }

    public Boolean getStatus() {
        return status.get();
    }

    public void setStatus(Boolean status) {
        this.status.set(status);
    }
    
    public BooleanProperty statusProperty() {
        return status;
    }

    public Boolean getIsLunas() {
        return isLunas.get();
    }

    public void setIsLunas(Boolean isLunas) {
        this.isLunas.set(isLunas);
    }
    
    public BooleanProperty isLunasProperty() {
        return isLunas;
    }
}
