package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GamePageController {
    @FXML private AnchorPane GameBoard;
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML private GridPane gridPane;

    @FXML
    void initialize() throws IOException {
        initChessBoard();
    }
    void initChessBoard(){
        int count = 0;
        double s = 69; // side of rectangle
        for (int i = 0; i < 8; i++) {
            count++;
            for (int j = 0; j < 8; j++) {
                Rectangle r = new Rectangle(s, s, s, s);
                if (count % 2 == 0)
                    r.setFill(Color.WHITE);
                gridPane.add(r, j, i);
                count++;
            }
        }
    }
}
