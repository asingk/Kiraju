/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kiraju.model.Users;
import kiraju.util.HibernateUtil;
import kiraju.util.JDBCConnection;
import org.apache.log4j.Logger;

/**
 *
 * @author arvita
 */
public class Kiraju extends Application {
    private final static Logger LOGGER = Logger.getLogger(Kiraju.class);
    private Stage stage;
    private BorderPane root;
    private AdminController adminController;
    
    @Override
    public void init() throws Exception {
        try {
            String currentDir = System.getProperty("user.dir");
            System.setProperty("derby.system.home", currentDir);
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("derby.system.home = " + System.getProperty("derby.system.home"));
                LOGGER.debug("user.home = "+ System.getProperty("user.home"));
                LOGGER.debug("user.dir = "+ System.getProperty("user.dir"));
            }
        } catch (Exception e) {
            LOGGER.error("failed to set derby.system.home", e);
        }
        JDBCConnection.createDatabase();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.getIcons().add(new Image(this.getClass().getResource("img/kiraju_logo_thumb.png").toString()));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RootLayout.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        RootLayoutController controller = loader.getController();
        controller.setMainApp(this);
        stage.show();
        showInti();
    }
    
    @Override
    public void stop() throws Exception {
        HibernateUtil.getSessionFactory().close();
        JDBCConnection.databaseShutdown();
        super.stop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public Stage getStage() {
        return stage;
    }

    private void showInti() {
        try {
            // Load person overview
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Admin.fxml"));
            TabPane inti = (TabPane) loader.load();
            
            //Set person overview into the center of root layout
            root.setCenter(inti);
            
            //give the controller access to the main page
            adminController = loader.getController();
            adminController.setPrimaryStage(stage);
        } catch (IOException e) {
            LOGGER.error("failed to load Admin.fxml", e);
        }
    }

    public void setLoginUser(Users loginUser) {
        adminController.setLoginUser(loginUser);
    }
}