
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vineet
 */
public class DefaultTest extends Application{

    public static void main(String ar[]){
    launch(ar);    
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    Parent root=FXMLLoader.load(getClass().getResource("MainAdmin.fxml"));
    Scene scene=new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    }
    
    
}
