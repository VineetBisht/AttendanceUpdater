/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.bg.ScreensController;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 *
 * @author Vineet
 */
public class AttendanceUpdater extends Application {
    
    static Logger logger=Logger.getLogger(AttendanceUpdater.class);
    public static String login="/main/resources/fxml/Login.fxml";
    public static String loginID="Login";
    public static String register="/main/resources/fxml/Register.fxml";
    public static String registerID="Register";
    public static String menu="/main/resources/fxml/Menu.fxml";
    public static String menuID="Menu";
    private static Stage primaryStage;
    
    
    @Override    
    public void start(Stage primaryStage) {
    ScreensController mainController=new ScreensController();
    mainController.loadScreen(loginID, login);
    mainController.loadScreen(registerID,register);
    mainController.loadScreen(menuID, menu);
    mainController.setScreen(loginID);
    Group root=new Group();
    root.getChildren().addAll(mainController);
    Scene scene=new Scene(root);
   
   primaryStage.setScene(scene);
   primaryStage.setResizable(false);
   primaryStage.initStyle(StageStyle.UTILITY);
   primaryStage.show();
   this.primaryStage=primaryStage;
     }

   public static void resizeScreen(){
     primaryStage.sizeToScene();
     primaryStage.centerOnScreen();
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");
        launch(args);
    }
    
}
