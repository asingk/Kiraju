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
public class JenisMenuProperty {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
//    private final StringProperty deletedFlag = new SimpleStringProperty();
    
    //added by Arvit@20170907 - retail version
//    private final StringProperty kode = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    /**
     * @return the id
     */
    public int getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty(){
        return id;
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama.get();
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama.set(nama);
    }
    
    public StringProperty namaProperty(){
        return nama;
    }

//    public short getDeletedFlag() {
//        return deletedFlag.get().equalsIgnoreCase("Aktif") ? (short) 0 : (short) 1;
//    }
//
//    public void setDeletedFlag(short deletedFlag) {
//        String status = deletedFlag == 0 ? "Aktif" : "Tidak Aktif";
//        this.deletedFlag.set(status);
//    }
//    
//    public StringProperty deletedFlagProperty() {
//        return deletedFlag;
//    }
//
//    public String getKode() {
//        return kode.get();
//    }
//
//    public void setKode(String kode) {
//        this.kode.set(kode);
//    }
//    
//    public StringProperty kodeProperty() {
//        return kode;
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
