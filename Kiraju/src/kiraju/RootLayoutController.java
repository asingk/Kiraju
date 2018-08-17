/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent; 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import kiraju.implement.UsersModel;
import kiraju.interfaces.IUsers;
import kiraju.model.Posisi;
import kiraju.model.Users;
import kiraju.util.CommonConstant;
import kiraju.util.JDBCConnection;
import org.apache.log4j.Logger;

/**
 *
 * @author arvita
 */
public class RootLayoutController extends AdminController implements Initializable {
    
    private final static Logger LOGGER = Logger.getLogger(RootLayoutController.class);
    
    @FXML
    private Text namaUser;
    @FXML
    private Button masuk;
    
    private Kiraju mainApp;
    private Stage primaryStage;
    private final IUsers iUsers = new UsersModel();
    private boolean login;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!JDBCConnection.checkInstallDate() && !CommonConstant.ISPREMIUM){
            masuk.setDisable(true);
        }else{
            masuk.setDisable(false);
        }
    }
    
    @FXML
    private void masukBtn(ActionEvent actionEvent) {
        if(!login){
            loginDialog();
        }else{
            logoutDialog();
        }
    }

    public void setMainApp(Kiraju mainApp) {
        this.mainApp = mainApp;
        primaryStage = mainApp.getStage();
    }

    private void loginDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Masuk");
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(primaryStage);

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("img/login.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Masuk", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!!!");
            alert.setHeaderText("Username dan/atau password salah");
            alert.setContentText("Silahkan masukkan lagi");
            Users users = iUsers.selectByUsername(usernamePassword.getKey());
            if(users.getUsername() != null) {
                
                if(usernamePassword.getValue().equals(users.getPassword())){
                    masuk.setText("Keluar");
                    namaUser.setText(users.getNama());
                    login = true;
                    mainApp.setLoginUser(users);
                }else{
                    alert.showAndWait();
                }
            }else{
                alert.showAndWait();
            }
        });
    }

    private void logoutDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Anda Yakin?");
//        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            masuk.setText("Masuk");
            namaUser.setText("");
            login = false;
//            Users users = new Users();
//            users.setId(String.valueOf(CommonConstant.USER_NONE));
//            users.setNama("Tanpa User");
//            users.setPosisiId(new Posisi(CommonConstant.USER_NONE));
            mainApp.setLoginUser(new Users(String.valueOf(CommonConstant.USER_NONE), "Tanpa User", new Posisi(CommonConstant.USER_NONE)));
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    
    @FXML
    private void absensiBtn(ActionEvent actionEvent) {
        try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(AdminController.class.getResource("Absensi.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Absensi");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                
                AbsensiController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                
                dialogStage.showAndWait();

            } catch (IOException ex) {
                LOGGER.error("failed to load Bayar.fxml", ex);
            }
    }
    
}
