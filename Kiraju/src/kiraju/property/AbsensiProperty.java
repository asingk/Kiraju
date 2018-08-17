/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.property;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author arvita
 */
public class AbsensiProperty {
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty masuk = new SimpleStringProperty();
    private final StringProperty keluar = new SimpleStringProperty();
    private final StringProperty total = new SimpleStringProperty();

    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }
    
    public StringProperty namaProperty() {
        return nama;
    }

    public String getMasuk() {
        return masuk.get();
    }

    public void setMasuk(Date masuk) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String out = dateFormat.format(masuk);
        this.masuk.set(out);
    }
    
    public StringProperty masukProperty() {
        return masuk;
    }

    public String getKeluar() {
        return keluar.get();
    }

    public void setKeluar(Date keluar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String out = dateFormat.format(keluar);
        this.keluar.set(out);
    }
    
    public StringProperty keluarProperty() {
        return keluar;
    }

    public String getTotal() {
        return total.get();
    }

    public void setTotal(Date total) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String out = dateFormat.format(total);
        this.total.set(out);
    }
    
    public StringProperty totalProperty() {
        return total;
    }
}
