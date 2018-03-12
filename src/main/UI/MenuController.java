/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.UI;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.AttendanceUpdater;
import main.bg.ControlledScreen;
import main.bg.ScreensController;
import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Vineet
 */
public class MenuController implements Initializable,ControlledScreen {

    ScreensController myController;
    private static final Logger logger=Logger.getLogger(MenuController.class);
    String name;
    
    @FXML
    private Button report;
   
    @FXML
    private HBox logout;
    
    @FXML
    private Button punchIn;
   
    @FXML
    private Button punchOut;
    
    @FXML
    private Button check;
    
    @FXML
    private TableView table;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           name="";
        try{ 
            String u="jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn=DriverManager.getConnection(u,"root",null);
            System.out.println("Database connection successful for Name Check.");
            Statement stmt=conn.createStatement();
            ResultSet rset=stmt.executeQuery("select * from login where LoggedIn=true"); 
            
            if(rset.next()){
            name=rset.getString("Name");
            logger.info(name+" Logged In");
            }else{
            logger.info("No logged in user");
            }
            }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex){
            logger.error("NameCheck",ex);
            }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
    
    @FXML                                                       
    public void onGenerate(ActionEvent e){                      //Method to generate the report
        //TODO
    }                   
    
    @FXML
    public void onPunch(ActionEvent e){                         //Method called whenever the punch buttons are pressed
        if(e.getSource()==punchIn){
            punchOut.setDisable(false);
            punchIn.setDisable(true);
        }
        else if(e.getSource()==punchOut){
            try{
            String url="jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn=DriverManager.getConnection(url,"root",null);
            System.out.println("Database connection successful for punchIn.");
            Statement stmt=conn.createStatement();
            int rset=stmt.executeUpdate("update login set Attendance=Attendance+1 where Name='"+name+"'");     
            }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex){
            logger.error("punchOut",ex);
            }
            punchOut.setDisable(true);
        }
    }
    
    @FXML
    public void onLogout(MouseEvent me){
           try{
            String url="jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn=DriverManager.getConnection(url,"root",null);
            System.out.println("Database connection successful for punchIn.");
            Statement stmt2=conn.createStatement();
            int rset2=stmt2.executeUpdate("update login set LoggedIn=false where Name='"+name+"'"); 
           }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex){
            logger.error("On Logging out:",ex);
            }
          myController.setScreen(AttendanceUpdater.loginID); 
    }
    
    @FXML
    public void onCheck(ActionEvent a){
       try{
            String url="jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn=DriverManager.getConnection(url,"root",null);
            System.out.println("Database connection successful.");
            Statement stmt3=conn.createStatement();
            ResultSet rset3=stmt3.executeQuery("select * from login where Username='"+name+"'");     
            int att=rset3.getInt("Attendance");
            int total=rset3.getInt("Total");
            double per=(att/total)*100;
       
            final ObservableList<Percentage> data=
                    FXCollections.observableArrayList(
                         new Percentage(att,total));
                
            table.setItems(data);
          }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
            logger.error("Percentage Check",e);
           }
        table.setDisable(false);
    }
    
}

class Percentage{
private final SimpleIntegerProperty att;
private final SimpleIntegerProperty total;

 Percentage(int att,int total){
    this.att=new SimpleIntegerProperty(att);
    this.total=new SimpleIntegerProperty(total);
}

public int getAtt(){
    return att.get();
}

public void setAtt(int att){
    this.att.set(att);
}

public int getTotal(){
    return total.get();
}

public void setTotal(int total){
    this.total.set(total);
}
} 