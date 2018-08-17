/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.property;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author arvita
 */
public class PengeluaranProperty {
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty harga = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
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

    public String getHarga() {
        return harga.get();
    }

    public void setHarga(Integer harga) {
        this.harga.set(numberFormat.format(harga));
    }
    
    public StringProperty hargaProperty() {
        return harga;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }
    
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
    
}
