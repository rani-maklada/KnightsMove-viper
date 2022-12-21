package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.ChessBoard;

import java.io.IOException;

public class GameRegisterController {
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML
    private ComboBox<String> cbTheme;
    @FXML
    private TextField myNickName;
    @FXML
    private GridPane gpBoard;
    private ChessBoard board;
    @FXML
    void initialize() throws IOException {
        board = new ChessBoard(gpBoard, "Coral",2);
        String themes[] = {"Coral", "Dusk", "Wheat", "Marine", "Emerald", "Sandcastle"};
        cbTheme.getItems().addAll(themes);
        cbTheme.setPromptText("Coral");
        cbTheme.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Code to execute when the user selects a different item from the ChoiceBox
            System.out.println("You selected: " + newValue);
            board.setThemeBoard(cbTheme.getValue());
        });
    }
    @FXML
    void cancelButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void okButton(ActionEvent event) throws IOException {
        if(!checkName()){return;}
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GamePage.fxml"));
        loader.setControllerFactory((Class<?> type) -> {
            if (type == GamePageController.class) {
                return new GamePageController(myNickName.getText(),board.getTheme());
            } else {
                try {
                    return type.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    private boolean checkName(){
        if(myNickName.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Let's Go");
            alert.setHeaderText("Fill in your nickname please");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
