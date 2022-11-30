package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
//    @FXML private AnchorPane GameBoard;
//    @FXML private Parent root;
//    @FXML private Scene scene;
//    @FXML private Stage stage;
    @FXML private GridPane chessBoard;
    @FXML private Label lbTimer;
    @FXML private Button btPlay;
    public static Piece currentPiece;
    public static String currentPlayer;
    private static final Integer STARTTIME = 0;
    private Timeline timeline;
    private Integer timeSeconds =
            (STARTTIME);

    public static ChessBoard cb;
    private String theme;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private boolean game;
    @FXML
    void initialize() throws IOException {

        cb = new ChessBoard(chessBoard, "Marine");
        currentPiece = null;
        currentPlayer = "black";
        this.game = true;
        lbTimer.setText(timeSeconds.toString());
        lbTimer.setTextFill(Color.BLUE);
        lbTimer.setStyle("-fx-font-size: 2em;");

    }

    @FXML
    void playButton(ActionEvent event){

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
    }






    @FXML
    void getOnMouseClicked(MouseEvent event) {
        EventTarget target = event.getTarget();
        System.out.println(target.toString());

        // Clicked on square
        if (target.toString().equals("Tile")) {
            Tile tile = (Tile) target;
            if (tile.isOccupied()) {
                Piece newPiece = (Piece) tile.getChildren().get(0);

                // Selecting a new piece
                if (currentPiece == null) {
                    currentPiece = newPiece;


//                            currentPiece.getAllPossibleMoves();
                    if (!currentPiece.getColor().equals(currentPlayer)) {
                        currentPiece = null;
                        return;
                    }
                    selectPiece(game);
                }
                // Selecting other piece of same color || Killing a piece
                else {
                    if (currentPiece.getColor().equals(newPiece.getColor())){
                        deselectPiece(false);
                        currentPiece = newPiece;
//                                currentPiece.getAllPossibleMoves();
                        selectPiece(game);
                    } else {
                        killPiece(tile);
                    }
                }

            }
            // Dropping a piece on blank square
            else {
                dropPiece(tile);
            }
        }
        // Clicked on piece
        else {
            Piece newPiece = (Piece) target;
            Tile tile = (Tile) newPiece.getParent();
            // Selecting a new piece
            if (currentPiece == null) {
                currentPiece = newPiece;
                if (!currentPiece.getColor().equals(currentPlayer)) {
                    currentPiece = null;
                    return;
                }
                selectPiece(game);
            }
            // Selecting other piece of same color || Killing a piece
            else {
                if (currentPiece.getColor().equals(newPiece.getColor())) {
                    deselectPiece(false);
                    currentPiece = newPiece;
                    selectPiece(game);
                } else {
                    killPiece(tile);
                }
            }

        }
    }

    private void selectPiece(boolean game) {
        if (!game) {
            currentPiece = null;
            return;
        }

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.BLACK);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        currentPiece.setEffect(borderGlow);
        currentPiece.getAllPossibleMoves();
        currentPiece.showAllPossibleMoves(true);
    }

    private void deselectPiece(boolean changePlayer) {
        currentPiece.setEffect(null);
        currentPiece.showAllPossibleMoves(false);
        currentPiece = null;
        if (changePlayer) currentPlayer = currentPlayer.equals("white") ? "black" : "white";
    }

    private void dropPiece(Tile tile) {
        if (!currentPiece.getPossibleMoves().contains(tile.getName())) return;

        Tile initialSquare = (Tile) currentPiece.getParent();
        tile.getChildren().add(currentPiece);
        tile.setOccupied(true);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        currentPiece.setPosX(tile.getX());
        currentPiece.setPosY(tile.getY());
        deselectPiece(true);
    }

    private void killPiece(Tile tile) {
        if (!currentPiece.getPossibleMoves().contains(tile.getName())) return;

        Piece killedPiece = (Piece) tile.getChildren().get(0);
        if (killedPiece.getType().equals("Knight")) this.game = false;


        Tile initialSquare = (Tile) currentPiece.getParent();
        tile.getChildren().remove(0);
        tile.getChildren().add(currentPiece);
        tile.setOccupied(true);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        currentPiece.setPosX(tile.getX());
        currentPiece.setPosY(tile.getY());
        deselectPiece(true);
    }
}
