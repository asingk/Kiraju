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
public class PosisiProperty {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();

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
    
}
