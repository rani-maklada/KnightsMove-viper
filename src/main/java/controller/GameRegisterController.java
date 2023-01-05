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

/**
 * Before entering the game you need to Register
 * and choose theme for the board
 */
public class GameRegisterController {
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML private ComboBox<String> cbTheme;
    @FXML private TextField myNickName;
    @FXML private GridPane gpBoard;
    private ChessBoard board;
    /**
     * Initializes the HomePage.
     * Initializes the GridPane and creates a new chessboard with the default "Coral" theme.
     * Initializes the ChoiceBox with the list of themes and sets the default value to "Coral".
     * Adds a listener to the ChoiceBox so that when the user selects a different theme,
     * the chessboard's theme is updated.
     */
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

    /**
     * Handle the "cancel" button press and send you to the HomePage
     * @param event cancelButton event
     */
    @FXML
    void cancelButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handle the "OK" button press and send you to the GamePage
     * @param event okButton event
     */
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
    /**
     * Check if the user has entered a nickname.
     * If the nickname is empty, show an alert with a message to the user to enter a nickname.
     * @return a boolean value indicating whether the nickname is empty or not
     */
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
