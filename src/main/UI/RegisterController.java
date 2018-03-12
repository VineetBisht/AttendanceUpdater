/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.UI;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import main.AttendanceUpdater;
import main.bg.ControlledScreen;
import main.bg.ScreensController;
import org.apache.log4j.Logger;

/**
 *
 * @author Vineet
 */

public class RegisterController implements Initializable, ControlledScreen{
   
    private static final Logger logger=Logger.getLogger(RegisterController.class);
    ScreensController myController;
    
    @FXML
    private TextField name;

    @FXML
    private TextField username;
    
    @FXML
    private TextField address;
    
    @FXML
    private TextField phone;

    @FXML
    private PasswordField password;

    @FXML
    private ImageView back;
    
    @FXML
    private PasswordField repassword;

    @FXML
    private Button register;
    
    @FXML
    private Label error;

    @FXML
    void letsGoBack(MouseEvent event){
        myController.setScreen(AttendanceUpdater.loginID);
    }
            
    @FXML
    void registerUser(ActionEvent event) {
        if(password.getText().isEmpty() || name.getText().isEmpty() || username.getText().isEmpty() || repassword.getText().isEmpty()){
        error.setText("Fields Empty!");
             FadeTransition fadeIn = new FadeTransition(Duration.millis(1500),error);
             fadeIn.setFromValue(0.0);
             fadeIn.setToValue(1.0);
             fadeIn.setAutoReverse(true);
         if(!error.isVisible()){
             error.setVisible(true);
             fadeIn.playFromStart(); 
            }
            
        }
        else if(!(password.getText()).equals(repassword.getText())){               //if - to check if passwords match
        error.setText("Password Mismatch!");
             FadeTransition fadeIn = new FadeTransition(Duration.millis(1500),error);
             fadeIn.setFromValue(0.0);
             fadeIn.setToValue(1.0);
             fadeIn.setAutoReverse(true);
         if(!error.isVisible()){
             error.setVisible(true);
             fadeIn.playFromStart(); 
            }
        }else if(nameCheck()){                                               // to check whether username exists
            error.setText("Username exists!");
             FadeTransition fadeIn = new FadeTransition(Duration.millis(1500),error);
             fadeIn.setFromValue(0.0);
             fadeIn.setToValue(1.0);
             fadeIn.setAutoReverse(true);
         if(!error.isVisible()){
             error.setVisible(true);
             fadeIn.playFromStart(); 
            }
        }else{                                                               // Success in registration
             this.insert();
             error.setText("Successful");
             FadeTransition fadeIn = new FadeTransition(Duration.millis(1500),error);
             fadeIn.setFromValue(0.0);
             fadeIn.setToValue(1.0);
             fadeIn.setAutoReverse(true);
         if(!error.isVisible()){
             error.setVisible(true);
             fadeIn.playFromStart(); 
            }
        }
  
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController=screenParent;
     }

    public void insert(){                                   // To insert a new account into the database
       try{   
            String userName=username.getText();
            String pass=password.getText();
            String url="jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn=DriverManager.getConnection(url,"root",null);
            System.out.println("Database connection successful.");
            Statement stmt=conn.createStatement();
            stmt.executeUpdate("INSERT INTO login values("
                    + "'"
                    +name.getText()
                    +"','"+userName
                    +"','"+pass
                    +"','"+address.getText()
                    +"','"+phone.getText()
                    +"',0,0,false)");
            
            }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
            logger.error("insert",e);
           }
        
    }
    public boolean nameCheck(){                             // To check whether the name already exists or not
       try{
            String userName=username.getText();
            String url="jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn=DriverManager.getConnection(url,"root",null);
            System.out.println("Database connection successful.");
            Statement stmt=conn.createStatement();
            ResultSet rset=stmt.executeQuery("select * from login WHERE Username='"+userName+"'");     
            if(rset.next()){
             return true;   
            }
            }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
            logger.error("nameCheck",e);
            }
        return false;
        }
}   //class ends
