/*
 * To change this template file, choose Settings | Editor | File and Code Templates
 * and change the template in the editor.
 */

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main class
 *
 * @author Ahmed Abuwarda, Date: Apr 28,2020 , 5:27 PM.
 */
public class Main extends Application {

    /**
     * @param primaryStage stage
     * @throws Exception exception errors
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Browser.fxml"));
        primaryStage.setTitle("AhmadUC Browser");
        primaryStage.getIcons().add(new Image("browser.png"));
        primaryStage.setScene(new Scene(root, 1220, 600));
        primaryStage.show();
    }

    /**
     * @param args the command line here.
     */
    public static void main(String[] args) {
        // TODO code application logic here.

        launch(args);
    }
}
