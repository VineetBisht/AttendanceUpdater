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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.bg.ControlledScreen;
import main.bg.ScreensController;
import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Vineet
 */
public class AdminController implements Initializable, ControlledScreen {

    static Logger LOGGER = Logger.getLogger(AdminController.class);
    ScreensController myController;

    @FXML
    private HBox logout;

    @FXML
    private Button search;

    @FXML
    private TextField name;

    @FXML
    private ListView<String> results;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    public void onSearch(ActionEvent e) {
        try {
            boolean found = false;
            String text = name.getText().trim();
            String url = "jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, "root", null);
            System.out.println("Database connection successful for search.");
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select * from login where Username='" + text + "'");
            while (rset.next()) {
                results.getItems().add(rset.getString("Name"));
                found = true;
            }
            if (!found) {
                results.getItems().add("No Result Found");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error("Searchy: " + ex);
        }
    }

    @FXML
    public void onSelect(MouseEvent me) {
        results.getSelectionModel().getSelectedItem();

    }

}
