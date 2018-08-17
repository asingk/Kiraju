/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.time.LocalDate;
import java.util.List;
import javafx.collections.ObservableList;
import kiraju.model.Users;
import kiraju.property.AbsensiProperty;

/**
 *
 * @author arvita
 */
public interface IAbsensi {
    ObservableList<AbsensiProperty> getByDate(LocalDate localDate);
    void insert(Users users);
    void update(Users users);
    List<AbsensiProperty> getLaporan(LocalDate masuk, LocalDate keluar);
}
