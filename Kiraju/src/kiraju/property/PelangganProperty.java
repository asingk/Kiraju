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
public class PelangganProperty {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty telp = new SimpleStringProperty();
//    private final StringProperty idCard = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tanggal = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }
    
    public StringProperty idProperty() {
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

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String alamat) {
        this.alamat.set(alamat);
    }
    
    public StringProperty alamatProperty() {
        return alamat;
    }

    public String getTelp() {
        return telp.get();
    }

    public void setTelp(String telp) {
        this.telp.set(telp);
    }
    
    public StringProperty telpProperty() {
        return telp;
    }

//    public String getIdCard() {
//        return idCard.get();
//    }
//
//    public void setIdCard(String idCard) {
//        this.idCard.set(idCard);
//    }
//    
//    public StringProperty idCardProperty() {
//        return idCard;
//    }

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
}
