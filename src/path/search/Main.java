package path.search;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static path.search.Constants.*;

/**
 * Class start application.
 *
 * @version 1.0 6/24/2019.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(MAIN_FRAME));
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root, WIDTH, HIGHT));
        primaryStage.getScene().getStylesheets().add(CSS);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
