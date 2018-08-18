/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import kiraju.implement.MetodePembayaranModel;
import kiraju.implement.PemasokModel;
import kiraju.interfaces.IMetodePembayaran;
import kiraju.interfaces.IPemasok;
import kiraju.property.TransaksiPembelianProperty;
import kiraju.util.Choice;

/**
 * FXML Controller class
 *
 * @author arvita
 */
public class SubmitPembelianController implements Initializable {
    
    @FXML
    private ChoiceBox<Choice> pemasokCB;
    @FXML
    private ChoiceBox<String> jualMetodeBayarCB;
    @FXML
    private CheckBox status;
    
    private Stage dialogStage;
    private boolean okClicked;
    private final IPemasok iPemasok = new PemasokModel();
    private final IMetodePembayaran iMetodePembayaran = new MetodePembayaranModel();
    
//    private TransaksiPembelianProperty pembelianProperty;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pemasokCB.getItems().addAll(iPemasok.getAllOrderByName());
        jualMetodeBayarCB.getItems().addAll(iMetodePembayaran.getAllNama());
        jualMetodeBayarCB.getSelectionModel().selectFirst();
    }    

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    boolean isOkClicked() {
        return okClicked;
    }
    
    int getPemasokId() {
        return pemasokCB.getSelectionModel().getSelectedItem().getId();
    }
    
    int getMetodePembayaranId() {
        return jualMetodeBayarCB.getSelectionModel().getSelectedIndex()+1;
    }
    
    boolean getStatus() {
        return status.isSelected();
    }
    
    @FXML
    private void okAction(ActionEvent event) {
        okClicked = true;
        dialogStage.close();
    }
    
    @FXML
    private void batalAction(ActionEvent event) {
        dialogStage.close();
    }

    void initValue(TransaksiPembelianProperty pembelianProperty) {
//        this.pembelianProperty = pembelianProperty;
        pemasokCB.setValue(new Choice(pembelianProperty.getPemasokId(), pembelianProperty.getPemasokNama()));
        jualMetodeBayarCB.getSelectionModel().selectLast();
    }
    
    
    
}
