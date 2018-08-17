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
public class DiskonPajakProperty {
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty tipe = new SimpleStringProperty();
    private final StringProperty bilangan = new SimpleStringProperty();
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

    public String getTipe() {
        return tipe.get();
    }

    public void setTipe(Integer tipe) {
        String typeString = tipe == 1 ? "Rp." : "%";
        this.tipe.set(typeString);
    }
    
    public StringProperty tipeProperty() {
        return tipe;
    }

    public String getBilangan() {
        return bilangan.get().replace(".", "");
    }

    public void setBilangan(Integer bilangan) {
        this.bilangan.set(numberFormat.format(bilangan));
    }
    
    public StringProperty bilanganProperty() {
        return bilangan;
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
