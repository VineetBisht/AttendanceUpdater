/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.UI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.bg.ControlledScreen;
import main.bg.ScreensController;


/**
 * FXML Controller class
 *
 * @author Vineet
 */
public class DetailsController implements Initializable,ControlledScreen {

   static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DetailsController.class);
   ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
   @FXML
    private TextField name;

    @FXML
    private TextField username;

    @FXML
    private Button update;

    @FXML
    private Label error;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField repassword;

    @FXML
    private ImageView back;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;

    @FXML
    private TextField reg;

    @FXML
    private TextArea submission;

    @FXML
    void letsGoBack(MouseEvent event) {

    }

    @FXML
    void registerUser(ActionEvent event) {

    }

      @FXML
    void onLogout(MouseEvent event) {

    }
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
         myController = screenParent;
   }
    
     


}
