/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.property;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author arvita
 */
public class StokOpnameProperty {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty userNama = new SimpleStringProperty();
    private final StringProperty userId = new SimpleStringProperty();

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
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDate date = tanggal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String out = dateFormat.format(tanggal);
        this.tanggal.set(out);
    }
    
    public StringProperty tanggalProperty() {
        return tanggal;
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

    public String getStatus() {
        return status.get();
    }

    public void setStatus(Boolean status) {
        String out = status ? "Selesai" : "Simpan";
        this.status.set(out);
    }
    
    public StringProperty statusProperty() {
        return status;
    }

    public String getUserNama() {
        return userNama.get();
    }

    public void setUserNama(String userNama) {
        this.userNama.set(userNama);
    }
    
    public StringProperty userNamaProperty() {
        return userNama;
    }

    public String getUserId() {
        return userId.get();
    }

    public void setUserId(String userId) {
        this.userId.set(userId);
    }
    
    public StringProperty userIdProperty() {
        return userId;
    }
}
