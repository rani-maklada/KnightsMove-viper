package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.GameHistory;
import model.SysData;

import java.io.IOException;
import java.time.LocalDate;

public class HighScorePageController {
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML private TableView<GameHistory> tableView;
    @FXML
    private TableColumn<GameHistory, String> colPlayerName;

    @FXML
    private TableColumn<GameHistory, Integer> colScore;

    @FXML
    private TableColumn<GameHistory, LocalDate> colDate;
    @FXML
    void initialize() throws IOException {
        // Change the font of the table view
        tableView.setStyle("-fx-font-family: Arial; -fx-font-size: 14pt; -fx-alignment: center;");

        // Change the font of the table columns
        for (TableColumn column : tableView.getColumns()) {
            column.setStyle("-fx-font-family: Arial; -fx-font-size: 14pt; -fx-alignment: center;");
        }
//        for (TableColumn column : tableView.getColumns()) {
//            column.setResizable(false);
//        }
        // Set the cell value factories
        colPlayerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        ObservableList<GameHistory> data = FXCollections.observableArrayList(SysData.getInstance().getHistory());
        tableView.setItems(data);
        System.out.println(SysData.getInstance().getHistory());
    }
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
