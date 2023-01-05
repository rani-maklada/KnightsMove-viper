package controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.SysData;

/**
 * Main is the main class for the Knight Move game.
 * It extends the javafx.application.Application class
 * and overrides the start method to load the game's home page
 * and display it to the user.
 */
public class Main extends Application {
    // Create a singleton instance of the SysData class.
    private static SysData sys = SysData.getInstance();
    // The main method is the entry point for the application. It calls the launch method from the
    // Application class to start the JavaFX application.
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method is called by the JavaFX runtime to initialize
     * and start the application.
     * @param stage
     */
    @Override
    @SuppressWarnings("exports")
    public void start(Stage stage) {
        // Load the FXML file for the home page and set it as the root of the scene.
        // Create a new scene with the home page as the root node and set it as the scene for the stage.
        // Set the stage to be non-resizable and show it to the user.
        try {
            FXMLLoader root = new FXMLLoader(Main.class.getResource("/view/HomePage.fxml"));
            Scene scene = new Scene(root.load(), 800, 600);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            // Print the stack trace of any exceptions that occur.
            e.printStackTrace();
        }
    }
}
