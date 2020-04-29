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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ahmed Abuwarda, Date: Apr 29,2020 , 2:31 AM.
 */
public class BookmarksController implements Initializable {

    @FXML
    private Button addbtn;
    @FXML
    private Button browsebtn;
    @FXML
    private Button deletebtn;
    @FXML
    private TextField urltext;
    @FXML
    TableView<Table> tableview;
    @FXML
    TableColumn<Table, String> urlcolumn;
    @FXML
    TableColumn<Table, Integer> idcolumn;

    public static ObservableList<Table> bookmarks = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url url
     * @param rb  ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO code application logic here.

        addbtn.setOnMouseClicked(e -> addItem());
        deletebtn.setOnMouseClicked(e -> deleteSelectedItem());
        browsebtn.setOnMouseClicked(e -> BrowserController.webEngine.load(tableview.getSelectionModel().getSelectedItems().get(0).getUrl()
        ));

        // set cells to the table
        urlcolumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableview.setItems(bookmarks);

    }

    // add item to the table method
    public void addItem() {

        if (!urltext.getText().isEmpty()) {
            bookmarks.add(new Table((int) (Math.random() * (500 - 100 + 1) + 100), urltext.getText()));
            urltext.clear();
        }

    }

    // delete bookmark method
    private void deleteSelectedItem() {

        ObservableList<Table> selectedBookmark, allbookmarks;
        allbookmarks = tableview.getItems();
        selectedBookmark = tableview.getSelectionModel().getSelectedItems();
        selectedBookmark.forEach(allbookmarks::remove);

    }

    @FXML
    private void handle() {
        // handel action events
    }

}