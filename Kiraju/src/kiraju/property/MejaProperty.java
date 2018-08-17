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
public class MejaProperty {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty nomor = new SimpleStringProperty();

    public short getId() {
        return (short) id.get();
    }

    public void setId(short id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty() {
        return id;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
    
    public StringProperty statusProperty() {
        return status;
    }

    public String getNomor() {
        return nomor.get();
    }

    public void setNomor(String nomor) {
        this.nomor.set(nomor);
    }
    
    public StringProperty nomorProperty() {
        return nomor;
    }
}
