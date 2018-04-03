package main.UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import main.bg.ControlledScreen;
import main.bg.ScreensController;

/**
 *
 * @author Vineet
 */
public class MAController implements Initializable, ControlledScreen {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(MAController.class);
    ScreensController myController;
    static String NAME;

    @FXML
    private Button attendance;

    @FXML
    private MenuItem save;

    @FXML
    private MenuItem generate;

    @FXML
    private MenuItem saveNexit;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem about;

    @FXML
    private TextField search;

    @FXML
    private ListView<String> results;

    @FXML
    private TabPane tabs;

    @FXML
    public void onPressed(KeyEvent ke) {
        try {
            results.getItems().clear();
            String loc = "jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(loc, "root", null);
            System.out.println("Database connection successful for Name Check.");
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select * from login where Name LIKE '" + search.getText().trim() + "%'");
            System.out.println(search.getText().trim());
            while (rset.next()) {
                results.getItems().add(rset.getString("Name"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.error("NameCheck", ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        results.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String selected = results.getSelectionModel().getSelectedItem();
            NAME = selected;
            try {
                Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/main/resources/fxml/Tab.fxml"));
                Tab tb = new Tab(selected, node);
                tabs.getTabs().add(tb);

            } catch (IOException ex) {
                LOGGER.error("Results Initialize: " + ex);
            }
        });

        EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/main/resources/fxml/Manual.fxml"));
                    Tab tb = new Tab("Manual Attendance", node);
                    tabs.getTabs().add(tb);
                    event.consume();
                } catch (IOException ex) {
                    Logger.getLogger(MAController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        attendance.setOnAction(buttonHandler);

        onPressed(null);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

}
