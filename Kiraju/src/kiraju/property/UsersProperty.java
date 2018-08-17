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
public class UsersProperty {
    
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty userName = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final IntegerProperty posisiId = new SimpleIntegerProperty();
    private final StringProperty posisiNama = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

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

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }
    
    public StringProperty userNameProperty() {
        return userName;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
    
    public StringProperty passwordProperty() {
        return password;
    }

    public int getPosisiId() {
        return posisiId.get();
    }

    public void setPosisiId(int posisiId) {
        this.posisiId.set(posisiId);
    }
    
    public IntegerProperty posisiIdProperty() {
        return posisiId;
    }

    public String getPosisiNama() {
        return posisiNama.get();
    }

    public void setPosisiNama(String posisiNama) {
        this.posisiNama.set(posisiNama);
    }
    
    public StringProperty posisiNamaProperty() {
        return posisiNama;
    }
    
//    public Boolean getDeletedFlag() {
//        Boolean statusBoolean = status.get().equalsIgnoreCase("Ya") ? Boolean.TRUE : Boolean.FALSE;
//        return statusBoolean;
//    }
//
//    public void setDeletedFlag(Boolean status) {
//        String statusString = status ? "Ya" : "Tidak";
//        this.status.set(statusString);
//    }
//    
//    public StringProperty deletedFlagProperty() {
//        return status;
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
    
}
