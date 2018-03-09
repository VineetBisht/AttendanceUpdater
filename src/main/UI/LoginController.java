package main.UI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import main.AttendanceUpdater;
import main.bg.ControlledScreen;
import main.bg.ScreensController;
import org.apache.log4j.Logger;


public class LoginController implements Initializable, ControlledScreen{
    private static final Logger logger=Logger.getLogger(LoginController.class);
    ScreensController myController;
    ResultSet rset;
    Connection conn;
   
    @FXML
    private Button login;
    
    @FXML
    private Button register;

    @FXML
    private TextArea username;

    @FXML
    private PasswordField password;
    
    @FXML
    private Label error;
   
    @FXML
    public void loginAction(ActionEvent event){
       if(password.getText().isEmpty() || username.getText().isEmpty()){
            error.setText("Fields empty!");
             FadeTransition fadeIn = new FadeTransition(Duration.millis(1500),error);
             fadeIn.setFromValue(0.0);
             fadeIn.setToValue(1.0);
             fadeIn.setAutoReverse(true);
         if(!error.isVisible()){
             error.setVisible(true);
             fadeIn.playFromStart(); 
            }
       }
       else if(loginCheck()){
             error.setText("Successful");
             FadeTransition fadeIn = new FadeTransition(Duration.millis(1500),error);
             fadeIn.setFromValue(0.0);
             fadeIn.setToValue(1.0);
             fadeIn.setAutoReverse(true);
         if(!error.isVisible()){
             error.setVisible(true);
             fadeIn.playFromStart(); 
            }
       }else{  error.setText("Invalid Entry!");
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

    @FXML
    public void registerAction(ActionEvent event) throws FileNotFoundException, IOException{
        myController.setScreen(AttendanceUpdater.registerID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    rset=null;
    conn=null;
     
   }

    @Override
    public void setScreenParent(ScreensController screenParent) {
    myController=screenParent;
    }
    
     public boolean loginCheck(){           //checking database for the user account
            try{
            String userName=username.getText();
            String pass=password.getText();
            String url="jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn=DriverManager.getConnection(url,"root",null);
            System.out.println("Database connection successful.");
            Statement stmt=conn.createStatement();
            rset=stmt.executeQuery("select * from login where Username='"+userName+"' and Password='"+pass+"'");     
            if(rset.next()){
             return true;   
            }
            }catch(Exception e){
            logger.error("loginCheck",e);
           }
        return false;
        }
}
