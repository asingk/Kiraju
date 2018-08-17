/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import kiraju.model.Meja;
import kiraju.property.MejaProperty;

/**
 *
 * @author arvita
 */
public interface IMeja {
    List<Meja> getAll();
    void update(Meja meja);
    ObservableList<MejaProperty> getAllProperty();
    boolean updateAdminEdit(Meja meja, Stage stage);
    String getNomorById(short id);
    List<String> getAvailableForPindah(short id);
    short getIdByNomor(String nomor);
}
