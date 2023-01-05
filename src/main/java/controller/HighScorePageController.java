package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.GameHistory;
import model.SysData;
import java.io.IOException;
import java.time.LocalDate;
/**
 * HighScorePageController to view HighScore of the game
 * is the controller class for
 * the high score page of the Knight Move game.
 * It displays a table view of the top 10 scores in the game's history
 * and allows the user to return to the home page.
 */
public class HighScorePageController {
    // FXML elements
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML private TableView<GameHistory> tableView;
    @FXML private TableColumn<GameHistory, String> colPlayerName;
    @FXML private TableColumn<GameHistory, Integer> colScore;
    @FXML private TableColumn<GameHistory, LocalDate> colDate;

    /**
     * Initialize the high score page
     */
    @FXML
    void initialize() throws IOException {
        // Change the font of the table view
        tableView.setStyle("-fx-font-family: Arial; -fx-font-size: 14pt; -fx-alignment: center;");

        // Change the font of the table columns
        for (TableColumn column : tableView.getColumns()) {
            column.setStyle("-fx-font-family: Arial; -fx-font-size: 14pt; -fx-alignment: center;");
        }
        // Set the cell value factories for the table columns.
        colPlayerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        // Get the game history from the SysData singleton and sort it by score in descending order.
        ObservableList<GameHistory> data = FXCollections.observableArrayList(SysData.getInstance().getHistory());
        data.sort((h1, h2) -> Integer.compare(h2.getScore(), h1.getScore()));
        // Set the sorted game history as the items for the table view.
        tableView.setItems(data);
    }

    /**
     * Handle the "Back" button press and send you to the HomePage
     * @param event BackButton event
     * @throws IOException
     */
    @FXML
    void BackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }
}
