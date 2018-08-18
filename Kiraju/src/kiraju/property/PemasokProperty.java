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
public class PemasokProperty {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty telp = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty() {
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

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public StringProperty emailProperty() {
        return email;
    }

    public String getTelp() {
        return telp.get();
    }

    public void setTelp(String telp) {
        this.telp.set(telp);
    }
    
    public StringProperty telponProperty() {
        return telp;
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
}
