package controller;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

/**
 * HomePageController is the controller class
 * for the home page of the Knight Move game.
 * It handles button press events for the
 * "High Scores", "Questions", and "Start Game" buttons.
 */
public class HomePageController{
    // FXML elements
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;

    // Handle the "High Scores" button press.
    @FXML
    void HighScoreButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/HighScorePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    // Handle the "Questions" button press.
    @FXML
    void QuestionButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/QuestionsPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }
    // Handle the "Start Game" button press.
    @FXML
    void StartGameButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/GameRegister.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

}
