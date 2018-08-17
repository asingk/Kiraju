/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.util.List;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kiraju.util.Choice;

/**
 *
 * @author arvita
 */
public interface IValidation {
    boolean isTextFieldInputValid(TextField[] textFields, String[] namaTextFields, Stage stage);
    boolean isTextFieldInputValid(TextField[] textFields, String[] namaTextFields, List<ChoiceBox<Choice>> choiceBoxs, String[] namaChoiceBoxs, Stage stage);
    boolean isTextFieldInputValidv2(TextField[] textFields, String[] namaTextFields, List<ComboBox<String>> comboBoxs, String[] namaComboBoxs, Stage stage);
    boolean isTextFieldInputValid3(TextField[] textFields, String[] namaTextFields, List<ChoiceBox<String>> choiceBoxs, String[] namaChoiceBoxs, Stage stage);
}
