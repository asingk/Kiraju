/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.util.List;
import javafx.collections.ObservableList;
import kiraju.model.Pemasok;
import kiraju.property.PemasokProperty;
import kiraju.util.Choice;

/**
 *
 * @author arvita
 */
public interface IPemasok {
    ObservableList<PemasokProperty> getAllProp();
    void saveOrUpdate(Pemasok pemasok);
    List<Choice> searchPemasokByNama(String nama);
    List<Choice> getAllOrderByName();
}
