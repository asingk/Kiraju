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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kiraju.implement.AbsensiModel;
import kiraju.implement.UsersModel;
import kiraju.interfaces.IAbsensi;
import kiraju.interfaces.IUsers;
import kiraju.model.Users;

/**
 * FXML Controller class
 *
 * @author arvita
 */
public class AbsensiController implements Initializable {

    @FXML
    private AnchorPane anchorPane1;
    @FXML
    private AnchorPane anchorPane2;
    @FXML
    private Button nextBtn;
    @FXML
    private Button masukBtn;
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private Text status;
    @FXML
    private Text nama;
    
    private Stage dialogStage;
    private Users users;
    private final IUsers iUsers = new UsersModel();
    private final IAbsensi iAbsensi = new AbsensiModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameTF.textProperty().addListener((observable, oldValue, newValue) -> {
            nextBtn.setDisable(newValue.trim().isEmpty());
        });
        passwordTF.textProperty().addListener((observable, oldValue, newValue) -> {
            masukBtn.setDisable(newValue.trim().isEmpty());
        });
    }    

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
    private void tutupBtn(ActionEvent event) {
        dialogStage.close();
    }
    
    @FXML
    private void nextBtn(ActionEvent event) {
        users = iUsers.selectByUsernameIncludeStaff(usernameTF.getText());
        if(null != users && users.getUsername() != null) {
            usernameTF.setText("");
            anchorPane1.setVisible(false);
            anchorPane2.setVisible(true);
            status.setText(users.getStatusAbsensi() ? "Masuk" : "Keluar");
            masukBtn.setText(users.getStatusAbsensi() ? "Keluar" : "Masuk");
            nama.setText(users.getNama());
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!");
            alert.setHeaderText("Username salah");
            alert.setContentText("Silahkan masukkan lagi");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void masukBtn(ActionEvent event) {
        if(users.getPassword().equals(passwordTF.getText())){
            if(!users.getStatusAbsensi()) {
                iAbsensi.insert(users);
            }else{
                iAbsensi.update(users);
            }
            dialogStage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!");
            alert.setHeaderText("Password salah");
            alert.setContentText("Silahkan masukkan lagi");
            alert.showAndWait();
        }
    }
    
}
