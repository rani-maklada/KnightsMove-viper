package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.King;
import model.Piece;
import model.Tile;

public class GamePageController {
    @FXML
    private ChoiceBox<String> choiceTheme;
    @FXML
    private AnchorPane GamePage;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private GridPane chessBoard;
    @FXML
    private Label lbTimer;
    @FXML
    private Button btPlay;

    @FXML
    private SplitPane splitPane;
    @FXML private Label myNickName;
    public static Piece myPiece;
    public static Piece computerPiece;
    public static String currentPlayer;
    private static final Integer STARTTIME = 0;
    private Timeline timeline;
    private Integer timeSeconds = (STARTTIME);
    String myNickHolder;
    public static ChessBoard cb;
    private String theme;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private boolean game;
    private ArrayList<Tile> visitedTiles = new ArrayList<>();

    @FXML
    void initialize() throws IOException {
        GameStarting();
        cb = new ChessBoard(chessBoard, "Marine");
        myPiece = cb.knight;
        computerPiece = cb.king;
        currentPlayer = "black";
        this.game = true;
        lbTimer.setText(timeSeconds.toString());
        lbTimer.setTextFill(Color.BLUE);
        lbTimer.setStyle("-fx-font-size: 2em;");
        String themes[] = {"Coral", "Dusk", "Wheat", "Marine", "Emerald", "Sandcastle"};
        choiceTheme.getItems().addAll(themes);
        choiceTheme.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Code to execute when the user selects a different item from the ChoiceBox
            System.out.println("You selected: " + newValue);
            cb.setThemeBoard(choiceTheme.getValue());
        });
        StartMyTimer();
        myNickName.setText(myNickHolder);
    }

    @FXML
    void ClickTheme(MouseEvent event) {
        System.out.println("yes");
        cb.setThemeBoard(choiceTheme.getValue());
    }

    @FXML
    void playButton(ActionEvent event) {

        if (timeline != null) {
            timeline.stop();
        }

        timeSeconds = STARTTIME;

        // update timerLabel
        lbTimer.setText(timeSeconds.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        (EventHandler) event1 -> {
                            timeSeconds++;
                            // update timerLabel
                            lbTimer.setText(
                                    timeSeconds.toString());
                            if (timeSeconds >= 100) {
                                timeline.stop();
                            }
                        }));
        timeline.playFromStart();
        chessBoard.setDisable(false);
        selectPiece(true);
    }


    void StartMyTimer()
    {
        if (timeline != null) {
            timeline.stop();
        }

        timeSeconds = STARTTIME;

        // update timerLabel
        lbTimer.setText(timeSeconds.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        (EventHandler) event1 -> {
                            timeSeconds++;
                            // update timerLabel
                            lbTimer.setText(
                                    timeSeconds.toString());
                            if (timeSeconds >= 100) {
                                timeline.stop();
                            }
                        }));
        timeline.playFromStart();
        chessBoard.setDisable(false);
        selectPiece(true);
    }


    @FXML
    void backButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = (Scene) ((Node) event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void getOnMouseClicked(MouseEvent event) {
        EventTarget target = event.getTarget();
        System.out.println(target.toString());
        // Clicked on Tile
        if (target.toString().equals("Tile")) {
            Tile tile = (Tile) target;
            if (tile.isOccupied()) {//Kill piece
                Piece newPiece = (Piece) tile.getChildren().get(0);
                killPiece(tile);
            } else { // Drop piece on blank tile
                dropPiece(tile);
            }
        } else { // Clicked on piece
            Piece newPiece = (Piece) target;
            Tile tile = (Tile) newPiece.getParent();
            killPiece(tile);
        }
    }

    private void selectPiece(boolean game) {
        if (!game) {
            myPiece = null;
            return;
        }

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.BLACK);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        myPiece.setEffect(borderGlow);
        myPiece.getAllPossibleMoves();
        myPiece.showAllPossibleMoves(true);
    }

    private void deselectPiece(boolean changePlayer) {
        myPiece.setEffect(null);
        myPiece.showAllPossibleMoves(false);
        selectPiece(true);
    }

    private void computerMove() {
        computerPiece.getAllPossibleMoves();
        ArrayList<String> moves = computerPiece.getPossibleMoves();
        System.out.println(moves);
        String str = bestMove(moves);
        System.out.println("bestMove: " + str);
        for (Tile t : cb.getTiles()) {
            if (t.getName().equals(str)) {
                if (t.isOccupied()) {
                    killMyPiece(t);
                } else {
                    dropComputer(t);
                }
            }
        }
    }

    private String bestMove(ArrayList<String> moves) {
        int x = computerPiece.getPosX(), y = computerPiece.getPosY();
        for (String move : moves) {
            Tile tile = Piece.getTileByName(move);
            if (Math.abs(tile.getX() - myPiece.getPosX()) < Math.abs(x - myPiece.getPosX())) {
                x = tile.getX();
            }
            if (Math.abs(tile.getY() - myPiece.getPosY()) < Math.abs(y - myPiece.getPosY())) {
                y = tile.getY();
            }
        }

        return String.format("Tile%d%d", x, y);
    }

    private void dropPiece(Tile tile) {
        if (!myPiece.getPossibleMoves().contains(tile.getName())) return;

        Tile initialSquare = (Tile) myPiece.getParent();
        tile.getChildren().add(myPiece);
        tile.setOccupied(true);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        myPiece.setPosX(tile.getX());
        myPiece.setPosY(tile.getY());
        if (computerPiece != null) computerMove();
        deselectPiece(true);
    }

    private void dropComputer(Tile tile) {
        if (!computerPiece.getPossibleMoves().contains(tile.getName())) return;

        Tile initialSquare = (Tile) computerPiece.getParent();
        tile.getChildren().add(computerPiece);
        tile.setOccupied(true);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        computerPiece.setPosX(tile.getX());
        computerPiece.setPosY(tile.getY());
    }

    private void killMyPiece(Tile tile) {
        if (!computerPiece.getPossibleMoves().contains(tile.getName())) return;

        Piece killedPiece = (Piece) tile.getChildren().get(0);
        if (killedPiece.getType().equals("Knight")) {
            this.game = false;
            System.out.println("Game Over");
            GameOver();
        }

        Tile initialSquare = (Tile) computerPiece.getParent();
        tile.getChildren().remove(0);
        tile.getChildren().add(computerPiece);
        tile.setOccupied(true);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        computerPiece.setPosX(tile.getX());
        computerPiece.setPosY(tile.getY());
        deselectPiece(true);
    }

    private void killPiece(Tile tile) {
        if (!myPiece.getPossibleMoves().contains(tile.getName())) return;

        Piece killedPiece = (Piece) tile.getChildren().get(0);
        if (killedPiece.getType().equals("King")) computerPiece = null;
        if (killedPiece.getType().equals("Queen")) computerPiece = null;

        Tile initialSquare = (Tile) myPiece.getParent();
        tile.getChildren().remove(0);
        tile.getChildren().add(myPiece);
        tile.setOccupied(true);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        myPiece.setPosX(tile.getX());
        myPiece.setPosY(tile.getY());
        deselectPiece(true);
    }

    private void GameOver() {
        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
        gameOverAlert.setTitle("Game Over");
        gameOverAlert.setHeaderText("Sorry, you lost the game!");
        gameOverAlert.setContentText("Better luck next time!");
        gameOverAlert.showAndWait();
        homePage();
    }
    private void homePage(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
            stage = (Stage) GamePage.getScene().getWindow();
            scene = (Scene) GamePage.getScene();
            scene.setRoot(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void GameStarting() {
        // create an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Let's Go");
        alert.setHeaderText("Fill in your nickname please");

        Label label = new Label("");

        TextField textField = new TextField();

        alert.getDialogPane().setContent(new VBox(label, textField));

        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent()){
            homePage();
        } else if(result.get() == ButtonType.OK){
            if (textField.getText().isEmpty()) {
                GameStarting();
            }
        }
        myNickHolder=textField.getText();
    }
}