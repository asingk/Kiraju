/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import javafx.collections.ObservableList;
import kiraju.model.Pajak;
import kiraju.property.DiskonPajakProperty;
import kiraju.util.Choice;

/**
 *
 * @author arvita
 */
public interface IPajak {
    ObservableList<DiskonPajakProperty> getAllProperty();
    void saveOrupdate(Pajak pajak);
    ObservableList<Choice> getAllActive();
    Pajak getById(int id);
}
