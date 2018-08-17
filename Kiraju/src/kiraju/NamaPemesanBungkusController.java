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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kiraju.interfaces.ITransaksi;
import kiraju.implement.TransaksiModel;
import kiraju.model.Users;

/**
 *
 * @author arvita
 */
public class NamaPemesanBungkusController implements Initializable {
    @FXML
    private TextField namaPemesanTF;
    
    private Stage dialogStage;
    private int id;
    private boolean okClicked;
    private final ITransaksi iTransaksi = new TransaksiModel();
    private Users loginUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @FXML
    private void simpanBtn(ActionEvent actionEvent) {
        id = iTransaksi.insertByNama(namaPemesanTF.getText(), loginUser.getId());
        okClicked = true;
        dialogStage.close();
    }
    
    @FXML
    private void batalBtn(ActionEvent actionEvent) {
        dialogStage.close();
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public int getId() {
        return id;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setLoginUser(Users loginUser) {
        this.loginUser = loginUser;
    }
    
}