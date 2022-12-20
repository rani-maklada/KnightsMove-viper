package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
import model.AlertDisplayer;
import model.Piece;
import model.Tile;

import static java.lang.Thread.sleep;

public class GamePageController {
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
    private TextArea textArea;
    @FXML private Label myNickName;
    @FXML private Label lblScore;
    public static Piece myPiece;
    public static Piece computerPiece;
    public static String currentPlayer;
    private static final Integer STARTTIME = 0;
    private Timeline timeline;
    private Integer timeSeconds = (STARTTIME);
    private String myNickHolder;
    public static ChessBoard cb;
    private String theme;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private boolean game;
    private ArrayList<Tile> visitedTiles = new ArrayList<>();
    private int myScore;
    private ArrayList<String> specialTiles;
    private int stageGame;
    public GamePageController(String name,String theme){
        this.myNickHolder = name;
        this.theme = theme;
    }
    @FXML
    void initialize() throws IOException {
        cb = new ChessBoard(chessBoard, theme,8);
        myPiece = cb.knight;
        computerPiece = cb.king;
        currentPlayer = "black";
        this.game = true;
        lbTimer.setText(timeSeconds.toString());
        lbTimer.setTextFill(Color.BLUE);
        lbTimer.setStyle("-fx-font-size: 2em;");
        StartMyTimer();
        myNickName.setText(myNickHolder);
        myScore=0;
        lblScore.setText("Score: " + myScore);
        stageGame=1;
        generateRandomTile();
    }
    void generateRandomTile(){
        Random rand = new Random();
        specialTiles = new ArrayList<String>();
        int rand_intX;
        int rand_intY;
        while(specialTiles.size()<15){
            rand_intX = rand.nextInt(8);
            rand_intY = rand.nextInt(8);
            specialTiles.add("Tile"+rand_intX+rand_intY);
        }
        System.out.println(specialTiles);
    }
    void stages(){

        switch(stageGame){
            case 1 -> {
                firstStage();
            }
            case 2 -> {
                secondStage();
            }
            case 3 -> {
                thirdStage();
            }
            case 4 -> {
                thirdStage();
            }
        }
    }

    void firstStage(){
        Random rand = new Random();
        dropRandomPiece(cb.getTiles().get(rand.nextInt(cb.getTiles().size())));
        System.out.println("Randommmmm");
    }
    void dropRandomPiece(Tile tile){
        textArea.setText(textArea.getText()+"\n jump to Randommmmm");
        Tile initialSquare = (Tile) myPiece.getParent();
        tile.getChildren().add(myPiece);
        tile.setOccupied(true);
        if(!tile.isVisited()){
            tile.setVisited(true);
            setBackgroundVisited(tile);
            myScore++;
            lblScore.setText("Score: " + myScore);
            visitedTiles.add(tile);
        }
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        myPiece.setPosX(tile.getX());
        myPiece.setPosY(tile.getY());
        if (computerPiece != null) computerMove();
        deselectPiece(true);
        if(specialTiles.contains(tile.getName())){
            stages();
        }
    }
    void alertDisplayer(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("title");
        alert.setHeaderText(null);
        alert.setContentText("message");
        alert.showAndWait();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // We'll ignore this exception
        }

        alert.close();
    }
    void secondStage(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Custom Dialog");
        dialog.setHeaderText("Question");
// Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

// Create the radio buttons.
        ToggleGroup group = new ToggleGroup();
        RadioButton radioButton1 = new RadioButton("Option 1");
        radioButton1.setToggleGroup(group);
        RadioButton radioButton2 = new RadioButton("Option 2");
        radioButton2.setToggleGroup(group);
        RadioButton radioButton3 = new RadioButton("Option 3");
        radioButton3.setToggleGroup(group);

// Add the radio buttons to the dialog.
        VBox vbox = new VBox(radioButton1, radioButton2, radioButton3);
        dialog.getDialogPane().setContent(vbox);

// Convert the result to a string when the OK button is clicked.
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButtonType) {
                if (radioButton1.isSelected()) {
                    return "Option 1";
                } else if (radioButton2.isSelected()) {
                    return "Option 2";
                } else if (radioButton3.isSelected()) {
                    return "Option 3";
                }
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Selected option: " + result.get());
        }

        System.out.println("Questionnn");
    }
    void thirdStage(){

    }
    void fourthStage(){

    }
    @FXML
    void resetButton(ActionEvent event) throws IOException {
        cb.getChessBoard().getChildren().clear();
        initialize();
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
//                Piece newPiece = (Piece) tile.getChildren().get(0);
//                killPiece(tile);
            } else { // Drop piece on blank tile
                dropPiece(tile);
            }
        }
//        else { // Clicked on piece
//            Piece newPiece = (Piece) target;
//            Tile tile = (Tile) newPiece.getParent();
//            killPiece(tile);
//        }
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
        if(tile.isVisited()){
            myScore = (myScore-1<0) ? 0:myScore-1;
        }else{
            tile.setVisited(true);
            setBackgroundVisited(tile);
            myScore++;
            visitedTiles.add(tile);
        }
        lblScore.setText("Score: " + myScore);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        myPiece.setPosX(tile.getX());
        myPiece.setPosY(tile.getY());
        if (computerPiece != null) computerMove();
        deselectPiece(true);
        if(specialTiles.contains(tile.getName())){
//            alertDisplayer();
            AlertDisplayer alertDisplayer1 = new AlertDisplayer();
            alertDisplayer1.showOneSecondAlert("","");
            stages();
        }
    }
    private void setBackgroundVisited(Tile tile){
        switch (cb.getTheme()) {
            case "Coral" -> {
                tile.setBackground(new Background(new BackgroundFill(Color.web("#e4c16f"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            case "Dusk" -> {
                tile.setBackground(new Background(new BackgroundFill(Color.web("#b1e4b9"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            case "Wheat" -> {
                tile.setBackground(new Background(new BackgroundFill(Color.web("#cbb7ae"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            case "Marine" -> {
                tile.setBackground(new Background(new BackgroundFill(Color.web("#eaefce"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            case "Emerald" -> {
                tile.setBackground(new Background(new BackgroundFill(Color.web("#9dacff"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            case "Sandcastle" -> {
                tile.setBackground(new Background(new BackgroundFill(Color.web("#adbd90"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
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

//    private void killPiece(Tile tile) {
//        if (!myPiece.getPossibleMoves().contains(tile.getName())) return;
//
//        Piece killedPiece = (Piece) tile.getChildren().get(0);
//        if (killedPiece.getType().equals("King")) computerPiece = null;
//        if (killedPiece.getType().equals("Queen")) computerPiece = null;
//
//        Tile initialSquare = (Tile) myPiece.getParent();
//        tile.getChildren().remove(0);
//        tile.getChildren().add(myPiece);
//        tile.setOccupied(true);
//        initialSquare.getChildren().removeAll();
//        initialSquare.setOccupied(false);
//        myPiece.setPosX(tile.getX());
//        myPiece.setPosY(tile.getY());
//        deselectPiece(true);
//    }

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
            scene = GamePage.getScene();
            scene.setRoot(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}