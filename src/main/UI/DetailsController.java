/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.UI;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import main.bg.ControlledScreen;
import main.bg.ScreensController;

/**
 * FXML Controller class
 *
 * @author Vineet
 */
public class DetailsController implements Initializable, ControlledScreen{

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DetailsController.class);
    ScreensController myController;
    String NAME;
    boolean alt1, alt2, unsaved;

    @FXML
    private Button edit;

    @FXML
    private Button save;

    @FXML
    private Label done;
    
    @FXML
    private CheckBox firstFee;

    @FXML
    private CheckBox secondFee;

    @FXML
    private TextField name;

    @FXML
    private TextField add;

    @FXML
    private TextField ph;

    @FXML
    private TextField reg;

    @FXML
    private TextField att;

    @FXML
    private TextField tot;

    @FXML
    private TextField fee1;

    @FXML
    private TextField fee2;

    @FXML
    private Button update;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            NAME = MAController.NAME;
            alt1 = false;
            alt2 = false;
            unsaved=false;

            firstFee.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                alt1=true;
            });
            secondFee.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)->{
                alt2=true;
            });
            
            firstFee.setDisable(true);
            secondFee.setDisable(true);
            name.setEditable(false);
            add.setEditable(false);
            ph.setEditable(false);
            save.setDisable(true);
            edit.setDisable(false);
            update.setDisable(true);
    
                
            String con = "jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(con, "root", null);
            Connection conn2 = DriverManager.getConnection(con, "root", null);
            System.out.println("Database connection successful.");
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select * from login WHERE Name='" + NAME + "'");
            Statement stmt2 = conn2.createStatement();
            ResultSet rset2 = stmt2.executeQuery("select * from dates WHERE Name='" + NAME + "'");
            System.out.println("name :"+NAME);
            rset.next();
            rset2.next();
            name.setText(rset.getString("Name")); 
            add.setText(rset.getString("Address"));
            ph.setText(rset.getString("Phone"));
            att.setText(String.valueOf(rset.getInt("Attendance")));
            tot.setText(String.valueOf(rset.getInt("Total")));

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            reg.setText(dateFormat.format(rset2.getTimestamp("Registration")));

            if (rset2.getBoolean("FirstFee") && !(rset2.getBoolean(("SecondFee")))) {
            System.out.println("First");
                firstFee.setSelected(true);
                fee1.setText(dateFormat.format(rset2.getTimestamp("FirstDate")));
             
            } else if (rset2.getBoolean("FirstFee") && rset2.getBoolean(("SecondFee"))) {
                System.out.println("Second");
                firstFee.setSelected(true);
                secondFee.setSelected(true);
                fee1.setText(dateFormat.format(rset2.getTimestamp("FirstDate")));
                fee2.setText(dateFormat.format(rset2.getTimestamp("SecondDate")));
             } else {
               System.out.println("Third");
               fee1.setDisable(true);
               fee2.setDisable(true);
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
          LOGGER.error("Details Init: ",ex);
        }
    }

    @FXML
    public void onEdit(ActionEvent ae){
        try{
            unsaved=true;
            String con = "jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn3 = DriverManager.getConnection(con, "root", null);
            System.out.println("Edit successful.");
            Statement s = conn3.createStatement();
            ResultSet r = s.executeQuery("select * from dates WHERE Name='" + NAME + "'");
            r.next();
            
            if (r.getBoolean("FirstFee") && !(r.getBoolean(("SecondFee")))) {
                secondFee.setDisable(false);
                firstFee.setDisable(true);
                fee1.setDisable(true);
            } else if (r.getBoolean("FirstFee") && r.getBoolean(("SecondFee"))) {
                firstFee.setDisable(true);
                secondFee.setDisable(true);
            } else {
                firstFee.setDisable(false);
                secondFee.setDisable(false);
                fee1.setDisable(true);
                fee2.setDisable(true);
                
            }
            
            name.setEditable(true);
            add.setEditable(true);
            ph.setEditable(true);
            save.setDisable(false);
            edit.setDisable(true);
            update.setDisable(false);
        }catch(ClassNotFoundException | SQLException e){
            LOGGER.error("Edit: "+e);
        }
    }
    
    @FXML
    public void onUpdate(ActionEvent ae) {
        try {
            String con = "jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn4 = DriverManager.getConnection(con, "root", null);
            Connection conn5 = DriverManager.getConnection(con, "root", null);
            System.out.println("Database connection successful.");
            PreparedStatement prep = conn4.prepareStatement("update dates "
                    + "set "
                    + "Name=?,"
                    + "FirstFee=?,"
                    + "SecondFee=? "
                    + "where Name=?"
            );
            prep.setString(1, name.getText());
            prep.setBoolean(2, firstFee.isSelected());
            prep.setBoolean(3, secondFee.isSelected());
            prep.setString(4, NAME);

            PreparedStatement prep2 = conn5.prepareStatement(""
                    + "update login "
                    + "set "
                    + "Name=?,"
                    + "Address=?,"
                    + "Phone=? "
                    + "where Name=?");
            prep2.setString(1, name.getText());
            prep2.setString(2, add.getText());
            prep2.setString(3, ph.getText());
            prep2.setString(4, NAME);
            
            Connection conn6 = DriverManager.getConnection(con, "root", null);
                
            if (alt1 && alt2) {
                PreparedStatement prep3 = conn6.prepareStatement(""
                        + "update dates "
                        + "set "
                        + "FirstDate=?,"
                        + "SecondDate=? "
                        + "where Name=?");
                long date=new java.util.Date().getTime();
                Timestamp ts = new Timestamp(date);
            
                prep3.setTimestamp(1, ts);
                prep3.setTimestamp(2, ts);
                prep3.setString(3, NAME);
                prep3.executeUpdate();
            
            
            } else if (alt1 && !alt2) {
                PreparedStatement prep3 = conn6.prepareStatement(
                         "update dates "
                        + "set "
                        + "FirstDate=? "
                        + "where Name=?");
                long date=new java.util.Date().getTime();
                Timestamp ts = new Timestamp(date);
            
                prep3.setTimestamp(1, ts);
                prep3.setString(2, NAME);
                prep3.executeUpdate();
            } else if (!alt1 && alt2) {
                PreparedStatement prep3 = conn6.prepareStatement(""
                        + "update dates "
                        + "set "
                        + "SecondDate=? "
                        + "where Name=?");
                long date=new java.util.Date().getTime();
                Timestamp ts = new Timestamp(date);
                prep3.setTimestamp(1, ts);
                prep3.setString(2, NAME);
                prep3.executeUpdate();
            }
            prep.executeUpdate();
            prep2.executeUpdate();
            unsaved=false;
            done.setText("Success!");
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), done);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.setAutoReverse(true);
            if (!done.isVisible()) {
                done.setVisible(true);
                fadeIn.playFromStart();
            }
        } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
        }
    initialize(null,null);
    }

}

