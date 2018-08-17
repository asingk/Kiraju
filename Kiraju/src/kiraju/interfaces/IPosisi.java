/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import javafx.collections.ObservableList;
import kiraju.property.PosisiProperty;

/**
 *
 * @author arvita
 */
public interface IPosisi {
    ObservableList<PosisiProperty> getAll();
    int getIdByName(String nama);
}
