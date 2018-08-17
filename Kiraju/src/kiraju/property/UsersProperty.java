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
    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty userName = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final IntegerProperty posisiId = new SimpleIntegerProperty();
    private final StringProperty posisiNama = new SimpleStringProperty();
    private final StringProperty deletedFlag = new SimpleStringProperty();

    public short getId() {
        return (short) id.get();
    }

    public void setId(short id) {
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

    public short getPosisiId() {
        return (short) posisiId.get();
    }

    public void setPosisiId(short posisiId) {
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
    
    public short getDeletedFlag() {
        return deletedFlag.get().equalsIgnoreCase("Aktif") ? (short) 0 : (short) 1;
    }

    public void setDeletedFlag(short deletedFlag) {
        String status = deletedFlag == 0 ? "Aktif" : "Tidak Aktif";
        this.deletedFlag.set(status);
    }
    
    public StringProperty deletedFlagProperty() {
        return deletedFlag;
    }
    
}
