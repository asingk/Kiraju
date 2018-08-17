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
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    private final StringProperty waktu = new SimpleStringProperty();
    private final IntegerProperty meja = new SimpleIntegerProperty();
    private final StringProperty statusTransaksi = new SimpleStringProperty();
    private final StringProperty total = new SimpleStringProperty();
    
    //20170912 - retail version
    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty diskonNama = new SimpleStringProperty();
    private final StringProperty pajakNama = new SimpleStringProperty();
    private final IntegerProperty diskonTotal = new SimpleIntegerProperty();
    private final IntegerProperty pajakTotal = new SimpleIntegerProperty();
    private final StringProperty metodePembayaranNama = new SimpleStringProperty();

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

    public String getWaktu() {
        return waktu.get();
    }

    public void setWaktu(Date waktu) {
//        LocalTime time = LocalDateTime.ofInstant(waktu.toInstant(), ZoneId.systemDefault()).toLocalTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String out = dateFormat.format(waktu);
        this.waktu.set(out);
    }
    
    public StringProperty waktuProperty() {
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
        if(Objects.equals(statusTransaksi, CommonConstant.TRANSAKSI_BAYAR)){
            status = "BAYAR";
        }else if(Objects.equals(statusTransaksi, CommonConstant.TRANSAKSI_BATAL)){
            status = "BATAL";
        }
        this.statusTransaksi.set(status);
    }
    
    public StringProperty statusTransaksiproperty() {
        return statusTransaksi;
    }

    public Integer getTotal() {
        return Integer.valueOf(total.get().replace(".", ""));
    }

    public void setTotal(Integer total) {
        this.total.set(numberFormat.format(total));
    }
    
    public StringProperty totalProperty() {
        return total;
    }

    public String getTanggal() {
        return tanggal.get();
    }

    public void setTanggal(Date tanggal) {
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDate date = tanggal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        String out = date.format(format);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String out = dateFormat.format(tanggal);
        this.tanggal.set(out);
    }
    
    public StringProperty tanggalProperty() {
        return tanggal;
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

    public Integer getDiskonTotal() {
        return diskonTotal.get();
    }

    public void setDiskonTotal(Integer diskonTotal) {
        this.diskonTotal.set(diskonTotal);
    }
    
    public IntegerProperty diskonTotalProperty() {
        return diskonTotal;
    }

    public Integer getPajakTotal() {
        return pajakTotal.get();
    }

    public void setPajakTotal(Integer pajakTotal) {
        this.pajakTotal.set(pajakTotal);
    }
    
    public IntegerProperty pajakTotalProperty() {
        return pajakTotal;
    }

    public String getMetodePembayaranNama() {
        return metodePembayaranNama.get();
    }

    public void setMetodePembayaranNama(String metodePembayaranNama) {
        this.metodePembayaranNama.set(metodePembayaranNama);
    }
    
    public StringProperty metodePembayaranNamaProperty() {
        return metodePembayaranNama;
    }
}
