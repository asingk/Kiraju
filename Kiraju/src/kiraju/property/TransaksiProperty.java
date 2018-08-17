/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.property;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kiraju.util.CommonConstant;

/**
 *
 * @author arvita
 */
public class TransaksiProperty {
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty namaPemesan = new SimpleStringProperty();
    private final IntegerProperty jumlah = new SimpleIntegerProperty();
    private final StringProperty namaMenu = new SimpleStringProperty();
    private final ObjectProperty<LocalTime> waktu = new SimpleObjectProperty<>();
    private final IntegerProperty meja = new SimpleIntegerProperty();
    private final StringProperty statusTransaksi = new SimpleStringProperty();
    private final StringProperty total = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty() {
        return id;
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
    
    public Short getJumlah() {
        return (short) jumlah.get();
    }

    public void setJumlah(Short harga) {
        this.jumlah.set(harga);
    }
    
    public IntegerProperty hargaProperty() {
        return jumlah;
    }

    public String getNamaMenu() {
        return namaMenu.get();
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu.set(namaMenu);
    }
    
    public StringProperty namaMenuProperty() {
        return namaMenu;
    }

    public LocalTime getWaktu() {
        return waktu.get();
    }

    public void setWaktu(Date waktu) {
//        System.out.println("Date waktu = "+ waktu);
//        Date test = new Date();
        LocalTime time = LocalDateTime.ofInstant(waktu.toInstant(), ZoneId.systemDefault()).toLocalTime();
//        System.out.println("LocalTime time = "+ time);
        this.waktu.set(time);
    }
    
    public ObjectProperty<LocalTime> waktuProperty() {
        return waktu;
    }

    public short getMeja() {
        return (short) meja.get();
    }

    public void setMeja(short meja) {
        this.meja.set(meja);
    }
    
    public IntegerProperty mejaproperty() {
        return meja;
    }

    public Short getStatusTransaksi() {
        Short status = 0;
        if(statusTransaksi.get().equals("BAYAR")){
            status = CommonConstant.TRANSAKSI_BAYAR;
        }else if(statusTransaksi.get().equals("BATAL")){
            status = CommonConstant.TRANSAKSI_BATAL;
        }
        return status;
    }

    public void setStatusTransaksi(Short statusTransaksi) {
        String status = "";
        if(statusTransaksi == CommonConstant.TRANSAKSI_BAYAR){
            status = "BAYAR";
        }else if(statusTransaksi == CommonConstant.TRANSAKSI_BATAL){
            status = "BATAL";
        }
        this.statusTransaksi.set(status);
    }
    
    public StringProperty statusTransaksiproperty() {
        return statusTransaksi;
    }

    public String getTotal() {
        return total.get().replace(".", "");
    }

    public void setTotal(Integer total) {
        this.total.set(numberFormat.format(total));
    }
    
    public StringProperty totalProperty() {
        return total;
    }
}
