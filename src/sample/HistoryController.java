/*
 * To change this template file, choose Settings | Editor | File and Code Templates
 * and change the template in the editor.
 */

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ahmed Abuwarda, Date: Apr 30,2020 , 8:08 AM.
 */
public class HistoryController implements Initializable {

    @FXML
    private Button browsebtn;
    @FXML
    private Button deletebtn;
    @FXML
    TableView<Table> tableview;
    @FXML
    TableColumn<Table, String> urlcolumn;
    @FXML
    TableColumn<Table, Integer> idcolumn;

    public static ObservableList<Table> history = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url url
     * @param rb  ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO code application logic here.

        // delete button -to delete a url from the history table-
        deletebtn.setOnMouseClicked(e -> deleteSelectedItem());

        // browse button -to browse any url from the history table-
        browsebtn.setOnMouseClicked(e -> {
            // load the website if it selected and the history manager are not empty.
            if (!tableview.getItems().isEmpty() && !tableview.getSelectionModel().getSelectedItems().isEmpty()) {
                BrowserController.webEngine.load(tableview.getSelectionModel().getSelectedItems().get(0).getUrl());
            }
        });

        // set cells to the table
        urlcolumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        // add the url to history table
        tableview.setItems(history);

    }

    // delete bookmark method
    private void deleteSelectedItem() {

        ObservableList<Table> selectedBookmark, allbookmarks;
        allbookmarks = tableview.getItems();
        selectedBookmark = tableview.getSelectionModel().getSelectedItems();
        selectedBookmark.forEach(allbookmarks::remove);

    }

    /**
     * default handel method
     */
    @FXML
    private void handle() {
        // handel action events
    }

}