

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.bg.ControlledScreen;
import main.bg.ScreensController;

/**
 *
 * @author Vineet
 */
public class MAC implements Initializable, ControlledScreen {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(MAC.class);
    ScreensController myController;

    @FXML
    private MenuItem save;

    @FXML
    private MenuItem saveNlog;

    @FXML
    private MenuItem about;

    @FXML
    private TextField search;

    @FXML
    private ListView<String> results;

    @FXML
    private TabPane tabs;

    private void onSelect() {
        results.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                int i = results.getSelectionModel().getSelectedIndex();
                try {

                    if (i == 0) {
                        Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/main/resources/fxml/Tab.fxml"));
                        Tab tb = new Tab("temp1", node);
                        tabs.getTabs().add(tb);

                    } else if (i == 1) {
                        Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/main/resources/fxml/Tab.fxml"));
                        Tab tb = new Tab("temp2", node);
                        tabs.getTabs().add(tb);

                    }

                } catch (IOException ex) {
                    LOGGER.error("Admin Tab: " + ex);
                }
            }

        });

    }

    @FXML
    public void onSearch(MouseEvent me) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        results.getItems().addAll("temp1", "temp2");
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

}
