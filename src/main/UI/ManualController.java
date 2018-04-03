/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.UI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

    @FXML
    private Button update;

    @FXML
    private Label done;

    @FXML
    private ListView<?> list;

    @FXML
    private CheckBox all;

    @FXML
    void onUpdate(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       list = new ListView<>();
        list.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
          @Override
            public ObservableValue<Boolean> call(String item) {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener((obs, wasSelected, isNowSelected) -> 
                    System.out.println("Check box for "+item+" changed from "+wasSelected+" to "+isNowSelected)
                );
                return observable ;
            }}));
  // TODO
    }    
    
}
