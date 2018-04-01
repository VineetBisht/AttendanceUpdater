package main.UI;

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

public class LoginController implements Initializable, ControlledScreen {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
    ScreensController myController;

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
    public void loginAction(ActionEvent event) {
        int temp=4;
        if (password.getText().isEmpty() || username.getText().isEmpty()) {
            error.setText("Fields empty!");
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), error);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.setAutoReverse(true);
            if (!error.isVisible()) {
                error.setVisible(true);
                fadeIn.playFromStart();
            }
        } else if ((temp=loginCheck())==0 || temp==1 ){
           
            error.setText("Successful");
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), error);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.setAutoReverse(true);
            if (!error.isVisible()) {
                error.setVisible(true);
                fadeIn.playFromStart();
            }
           if(temp==0)
            myController.setScreen(AttendanceUpdater.menuID);
           else 
            myController.setScreen(AttendanceUpdater.admin);
           
        } else {
            error.setText("Invalid Entry!");
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), error);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.setAutoReverse(true);
            if (!error.isVisible()) {
                error.setVisible(true);
                fadeIn.playFromStart();
            }
        }
    }

    @FXML
    public void registerAction(ActionEvent event) throws FileNotFoundException, IOException {
        myController.setScreen(AttendanceUpdater.registerID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public int loginCheck() {           //checking database for the user account
        try {
            String userName = username.getText().trim();
            String pass = password.getText().trim();
            String url = "jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(url, "root", null);
            System.out.println("Database connection successful.");
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select * from login where Username='" + userName + "' and Password='" + pass + "'");
            if (rset.next()) {
                Statement stmt2 = conn.createStatement();
                int rset2 = stmt2.executeUpdate("update login set LoggedIn=true where Username='" + userName + "'");
                System.out.println("Logged In successfully" + rset2);
                
                boolean admin=rset.getBoolean("Admin");
                if(admin)
                    return 1;
                else 
                    return 0;
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            LOGGER.error("loginCheck", e);
        }
        return 2;
    }
}
