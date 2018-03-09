/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author Vineet
 */
public class AttendanceUpdater extends Application {
    
    @Override    
    public void start(Stage primaryStage) {
       
   try {
       FXMLLoader obj=new FXMLLoader();
       Parent parent=obj.load((getClass().getResource("/main/resources/fxml/Login.fxml")));
       Scene scene =new Scene(parent);
       primaryStage.setScene(scene);
       } catch (IOException ex) {
       ex.printStackTrace();
       }
   primaryStage.setAlwaysOnTop(true);
   primaryStage.setResizable(false);
   primaryStage.initStyle(StageStyle.UTILITY);
   primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
