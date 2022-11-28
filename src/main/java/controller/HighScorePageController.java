package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class HighScorePageController {
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML private TableView<String> tableView;
    @FXML private Button Back;
    void initialize() throws IOException {
        System.out.println(tableView);
    }
    @FXML
    void BackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = (Scene)((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }
}
