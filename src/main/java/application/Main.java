package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    @SuppressWarnings("exports")
    public void start(Stage stage) {
        try {
            FXMLLoader root = new FXMLLoader(application.Main.class.getResource("/view/HomePage.fxml"));
            System.out.println(root.getLocation());
            Scene scene = new Scene(root.load(), 800, 600);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
