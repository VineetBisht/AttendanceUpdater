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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Vineet
 */
public class ManualController implements Initializable {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DetailsController.class);
    ArrayList<String> student;

    @FXML
    private Button update;

    @FXML
    private Label done;

    @FXML
    private ListView<String> list;

    @FXML
    private CheckBox all;

    @FXML
    void onUpdate(ActionEvent event) {
        try {
            String con = "jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(con, "root", null);
            System.out.println("Database connection successful.");
            if (all.isSelected()) {
                Statement prep = conn.createStatement();
                prep.executeUpdate("update login "
                        + "set Attendance=Attendance+1,Total=Total+1");
                student.clear();
            } else {
                for (String a : student) {
                    Statement prep = conn.createStatement();
                    prep.executeUpdate("update login "
                            + "set Attendance=Attendance+1,Total=Total+1 WHERE Name='" + a + "'");
                }
                student.clear();
            }
        } catch (Exception ex) {
            LOGGER.error("Updating All: ", ex);
        }
        update.setDisable(true);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            student = new ArrayList<>();
            String con = "jdbc:mysql://localhost:3306/satyam";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(con, "root", null);
            System.out.println("Database connection successful.");
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select * from login");
            while (rset.next()) {
                list.getItems().add(rset.getString("Name"));
            }

            list.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(String item) {
                    if (update.isDisabled()) {
                        update.setDisable(false);
                    }
                    BooleanProperty observable = new SimpleBooleanProperty();
                    observable.addListener((obs, wasSelected, isNowSelected) -> {
                        if (isNowSelected) {
                            student.add(item);
                        } else {
                            student.remove(item);
                        }
                    });
                    return observable;
                }
            }));

            all.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (update.isDisable()) {
                        update.setDisable(false);
                    }
                }
            }
            );

        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("List of All: ", e);
        }
    }

}
