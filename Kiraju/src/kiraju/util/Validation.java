/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.util;

import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kiraju.interfaces.IValidation;

/**
 *
 * @author arvita
 */
public class Validation implements IValidation{
    @Override
    public boolean isTextFieldInputValid(TextField[] textFields, String[] namaTextFields, Stage stage) {
        String errorMessage = "";
        for(int i =0; i < textFields.length; i++){
            TextField textField = textFields[i];
            if(null == textField.getText() || textField.getText().length() == 0){
                errorMessage += namaTextFields[i] + " wajib diisi\n";
            }
        }
        if(errorMessage.length() == 0 ){
            return true;
        }else{
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Silahkan diisi bagian yang salah");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    @Override
    public boolean isTextFieldInputValid(TextField[] textFields, String[] namaTextFields, List<ChoiceBox<String>> choiceBoxs, String[] namaChoiceBoxs, Stage stage) {
        String errorMessage = "";
        for(int i = 0; i < textFields.length; i++){
            TextField textField = textFields[i];
            if(null == textField.getText() || textField.getText().length() == 0){
                errorMessage += namaTextFields[i] + " wajib diisi\n";
            }
        }
        for(int i = 0; i < choiceBoxs.size(); i++){
            ChoiceBox<String> choiceBox = choiceBoxs.get(i);
            if(null == choiceBox.getValue() || choiceBox.getValue().length() == 0){
                errorMessage += namaChoiceBoxs[i] + " wajib dipilih\n";
            }
        }
        
        if(errorMessage.length() == 0 ){
            return true;
        }else{
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Salah!");
            alert.setHeaderText("Silahkan diisi bagian yang salah");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
