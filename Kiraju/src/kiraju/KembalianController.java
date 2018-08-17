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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author arvita
 */
public class KembalianController implements Initializable{
    
    private Stage dialogStage;
    
    @FXML
    private Text uangKembali;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    void initValue(String kembalian) {
        uangKembali.setText(kembalian);
    }
    
    @FXML
    private void okBtn(ActionEvent actionEvent) {
        dialogStage.close();
    }
    
}
