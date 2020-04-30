/*
 * To change this template file, choose Settings | Editor | File and Code Templates
 * and change the template in the editor.
 */

package sample;

import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML BrowserController class
 *
 * @author Ahmed Abuwarda, Date: Apr 27,2020 , 6:19 PM.
 */
public class BrowserController implements Initializable {

    @FXML
    private TextField txt;
    @FXML
    private Button forbtn;
    @FXML
    private Button backbtn;
    @FXML
    private Button reloadbtn;
    @FXML
    private Button bookbtn;
    @FXML
    private Button cookieStoragebtn;
    @FXML
    private WebView webView;
    private WebHistory history;
    public static WebEngine webEngine;

    private ObservableList<WebHistory.Entry> historyEntryList;

    private static boolean clicked = true;

    /**
     * Initializes the controller class.
     *
     * @param url url
     * @param rb  ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO code application logic here.

        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load("https://google.com"); // load the homepage.

        txt.setOnAction(e -> webEngine.load(txt.getText().startsWith("http://") ? txt.getText() : "http://" + txt.getText()));

        setHistory(webEngine.getHistory());
        historyEntryList = getHistory().getEntries();
        SimpleListProperty<WebHistory.Entry> list = new SimpleListProperty<>(historyEntryList);

        // backward button
        backbtn.setOnAction(e -> goBack());
        backbtn.disableProperty().bind(getHistory().currentIndexProperty().isEqualTo(0));

        // forward button
        forbtn.setOnAction(e -> goForward());
        forbtn.disableProperty().bind(getHistory().currentIndexProperty().greaterThanOrEqualTo(list.sizeProperty().subtract(1)));

        // reload the site
        reloadbtn.setOnAction(e -> reloadWebSite());

        // Search text
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            txt.setText(newValue);
            HistoryController.history.add(new Table((int)(Math.random() * (500 - 100 + 1) + 100),newValue));

        });

        // bookmark button
        bookbtn.setOnMouseClicked(e -> {
            BookmarksController.bookmarks.add(new Table((int)(Math.random() * (500 - 100 + 1) + 100), txt.getText()));
            Alert addBookmarkAlert = new Alert(Alert.AlertType.INFORMATION);
            addBookmarkAlert.initStyle(StageStyle.UTILITY);
            addBookmarkAlert.setTitle("BOOKMARK STATUS!");
            addBookmarkAlert.setHeaderText(null);
            addBookmarkAlert.setContentText("Bookmark added to your bookmark manager successfully");
            addBookmarkAlert.showAndWait();
        });

        // cookie storage management
        cookieStoragebtn.setOnMouseClicked(e -> {

            if (clicked) {

                cookieStoragebtn.setDefaultButton(true);
                CookieManager manager = new CookieManager();
                manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
                CookieHandler.setDefault(manager);

                //it can save cookie on disk upon exit of application for next startup you can retrieve
                CookieStore store = manager.getCookieStore();
                try {
                    URI uriadd = new URI(getHistory().getEntries().get(getHistory().getCurrentIndex()).getUrl());
                    store.add(uriadd, new HttpCookie("name", "value"));

                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }

                // get cookie implementation
                try {

                    URI getcookie = new URI(getHistory().getEntries().get(getHistory().getCurrentIndex()).getUrl());
                    store.get(getcookie);

                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
                clicked = false;

            } else {

                cookieStoragebtn.setDefaultButton(false);
                CookieManager manager = new CookieManager();
                manager.setCookiePolicy(CookiePolicy.ACCEPT_NONE);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("COOKIES STATUS!");
                alert.setHeaderText(null);
                alert.setContentText("You DISABLED browser cookies -NO TRACKING-");
                alert.showAndWait();
                clicked = true;

            }
        });

    }

    @FXML
    private void handle() {

    }

    @FXML
    private void showBookmarkScene() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Bookmarks.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Bookmark manager");
        primaryStage.getIcons().add(new Image("bookmarkSettings.png"));
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();

    }

    @FXML
    private void showHistoryScene() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("History.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("History manager");
        primaryStage.getIcons().add(new Image("history.png"));
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();

    }

    public void reloadWebSite() {
        if (!getHistory().getEntries().isEmpty())
            webEngine.reload();
        else
            webEngine.load("https://google.com");
    }

    /**
     * @param history history
     */
    public void setHistory(WebHistory history) {
        this.history = history;
    }

    public WebHistory getHistory() {
        return history;
    }

    /**
     * goes back one page
     */
    public void goBack() {
        getHistory().go(historyEntryList.size() > 1 && getHistory().getCurrentIndex() > 0 ? -1 : 0);
    }

    /**
     * goes Forward one Page
     */
    public void goForward() {
        getHistory().go(historyEntryList.size() > 1 && getHistory().getCurrentIndex() < historyEntryList.size() - 1 ? 1 : 0);
    }
}