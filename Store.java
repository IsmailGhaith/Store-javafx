package store;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Store extends Application {

    public static String title = "E-Store Shopping";

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("enter.fxml"))));
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setMaximized(false);
        stage.show();

    }

}
