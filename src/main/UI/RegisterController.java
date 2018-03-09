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
import javafx.util.Duration;
import main.bg.ControlledScreen;
import main.bg.ScreensController;

/**
 *
 * @author Vineet
 */

public class RegisterController implements Initializable, ControlledScreen{

    ResultSet rset;
    Connection conn;
    
    @FXML
    private TextField name;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField repassword;

    @FXML
    private Button register;
    
    @FXML
    private Label error;

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
    rset=null;
    conn=null;
     }

    @Override
    public void setScreenParent(ScreensController screenPage) {
     }

    public void insert(){                                   // To insert a new account into the database
       try{   
            Statement stmt=conn.createStatement();
            String userName=username.getText();
            String pass=password.getText();
            String url="jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn=DriverManager.getConnection(url,"root",null);
            System.out.println("Database connection successful.");
            rset=stmt.executeQuery("INSERT INTO login values('"+name.getText()+"','"+userName+"','"+pass+"',0,0)");
            
            }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
            System.out.println("Cannot connect to database. "+e);
           }
        
    }
    public boolean nameCheck(){                             // To check whether the name already exists or not
       try{
            Statement stmt=conn.createStatement();
            String userName=username.getText();
            String url="jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn=DriverManager.getConnection(url,"root",null);
            System.out.println("Database connection successful.");
            rset=stmt.executeQuery("select * from login WHERE Username="+userName);     
            if(rset.next()){
             return true;   
            }
            }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
            System.out.println("Cannot connect to database. "+e);
           }
        return false;
        }
}   //class ends