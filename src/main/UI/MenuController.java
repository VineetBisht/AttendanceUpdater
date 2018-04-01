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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.AttendanceUpdater;
import main.bg.ControlledScreen;
import main.bg.Percentage;
import main.bg.ScreensController;
import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Vineet
 */
public class MenuController implements Initializable, ControlledScreen {

    ScreensController myController;
    String name;
    private static final Logger LOGGER = Logger.getLogger(MenuController.class);        
    
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name = "";
        System.out.println("Initializing");
        try {
            punchIn.setDisable(false);
            table.getColumns().clear();
            TableColumn attendance = new TableColumn("Attendance");
            attendance.setCellValueFactory(
                    new PropertyValueFactory<>("att"));
            TableColumn tot = new TableColumn("Total");
            tot.setCellValueFactory(
                    new PropertyValueFactory<>("total"));
            TableColumn percent = new TableColumn("%");
            percent.setCellValueFactory(
                    new PropertyValueFactory<>("percentage"));

            table.getColumns().addAll(attendance, tot, percent);

            String u = "jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            try (Connection conn = DriverManager.getConnection(u, "root", null)) {
                System.out.println("Database connection successful for Name Check.");
                Statement stmt = conn.createStatement();
                ResultSet rset = stmt.executeQuery("select * from login where LoggedIn=true");
                if (rset.next()) {
                    name = rset.getString("Name");
                    System.out.println("Name- "+name);
                    LOGGER.info(name + " Logged In");
                } else {
                    LOGGER.info("No logged in user");
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            LOGGER.error("NameCheck", ex);
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    public void onGenerate(ActionEvent e) {                      //Method to generate the report
    /* Document doc =new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("details.pdf"));
        doc.add(new Paragraph());
        */
    }

    @FXML
    public void onPunch(ActionEvent e) {                         //Method called whenever the punch buttons are pressed
        if (e.getSource() == punchIn) {
            punchOut.setDisable(false);
            punchIn.setDisable(true);
        } else if (e.getSource() == punchOut) {
            try {
                String url = "jdbc:mysql://localhost:3306/satyam";
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                Connection conn = DriverManager.getConnection(url, "root", null);
                System.out.println("Database connection successful for punchIn.");
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("update login set Attendance=Attendance+1 where Name='" + name + "'");
                System.out.println("Name: " + name);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
                LOGGER.error("punchOut", ex);
            }
            punchOut.setDisable(true);
        }
    }

    @FXML                                                            // logout method 
    public void onLogout(MouseEvent me) {
        try {
            String url = "jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(url, "root", null);
            System.out.println("Database connection successful for logout.");
            Statement stmt2 = conn.createStatement();
            int rset2 = stmt2.executeUpdate("update login set LoggedIn=FALSE where Username='" + name + "'");
            System.out.println("Logged Out successfully");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            LOGGER.error("On Logging out:", ex);
        }
        myController.setScreen(AttendanceUpdater.loginID);
    }

    @FXML                                                            // method called after check percentage button is pressed
    public void onCheck(ActionEvent a) {
        try {
            String url = "jdbc:mysql://localhost:3306/satyam";
            int att, total;
            Double per;
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(url, "root", null);
            System.out.println("Database connection successful.");
            Statement stmt3 = conn.createStatement();
            ResultSet rset3 = stmt3.executeQuery("select * from login where LoggedIn=true");
            if (rset3.next()) {
                att = rset3.getInt("Attendance");
                total = rset3.getInt("Total");
                per = (((double)att / (double)total) * 100.0);
            } else {
                LOGGER.info("ResultSet empty for percentage check");
                return;
            }

            final ObservableList<Percentage> data
                    = FXCollections.observableArrayList(
                            new Percentage(att, total, per));
            table.setItems(data);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            LOGGER.error("Percentage Check", e);
        }
        table.setDisable(false);
    }

}
