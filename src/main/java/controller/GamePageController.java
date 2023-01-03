package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Enums.TileType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.effect.ColorAdjust;
import org.controlsfx.control.Notifications;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import javafx.animation.AnimationTimer;

import static java.lang.Thread.sleep;

public class GamePageController {
    @FXML
    private AnchorPane GamePage;
    @FXML
    private ImageView imgTimer;

    @FXML
    private Parent root;
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
    @FXML
    private Label myNickName;
    @FXML
    private Label lblScore;
    @FXML
    private Label lblStage;
    @FXML
    private ColorPicker colorPicker;
    public static Piece myPiece;
    public static Piece computerPiece;
    //    private static final Integer STARTTIME = 0;
    private Timeline timeline;
    //    private Integer timeSeconds = (STARTTIME);
    private String myNickHolder;
    public static ChessBoard cb;
    private String theme;
    private boolean game;
    private ArrayList<Tile> visitedTiles = new ArrayList<>();
    private int myScore;
    private ArrayList<String> specialTiles;
    private int stageGame;
    private Random rand = new Random();
    private ArrayList<Question> questions = new ArrayList<>();
    public static int myStage;
    private long startTime;
    private long currentTime;
    private AnimationTimer timer;
    private Timeline mytimeline;
    private boolean paused = false;
    private long elapsedTime = 0;
    private int numberOfGlow;
    private double myRate;
    private ArrayList<Question> stageQuestions = new ArrayList<>();
    private int totalScore;

    public GamePageController(String name, String theme) {
        this.myNickHolder = name;
        this.theme = theme;
    }

    @FXML
    void initialize() throws IOException {
        myStage = 1;
        totalScore = 0;
        myScore = 0;
        myNickName.setText(myNickHolder);
        lblScore.setText("Score: " + myScore);
        lblStage.setText("Stage: " + myStage);
        questions = (ArrayList<Question>) SysData.getInstance().getQuestions().clone();
        cb = new ChessBoard(chessBoard, theme, 8);
        myPiece = cb.getKnight();
        computerPiece = cb.getQueen();
        this.game = true;
        lbTimer.setTextFill(Color.BLUE);
        lbTimer.setStyle("-fx-font-size: 2em;");
        visitedTile(cb.getTiles().get(0));
        generateRandomTile();
        generateRandomTile();
        generateRandomTile();
        selectPiece(true);
        questionMarkByLevel(1);
        questionMarkByLevel(2);
        questionMarkByLevel(3);
        timer();
//        myPiece.getAllPossibleMoves();
    }

    private ArrayList<Question> questionsByLevel(int level) {
        ArrayList<Question> q = new ArrayList<>();
        for (Question question : questions) {
            if (question.getLevel() == level) {
                q.add(question);
            }
        }
        return q;
    }

    private Question selectRandomQuestionByLevel(int level) {
        ArrayList<Question> q = questionsByLevel(level);
        return q.get(rand.nextInt(q.size()));
    }

    private void startMyTimeLine() {
        updatemytimeline();
    }

    private void updatemytimeline() {
        myRate = 5;
        timeline = new Timeline(new KeyFrame(Duration.seconds(myRate), event -> {
            computerMove();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

// Every 10 seconds, change the original timeline rate of X
        //rateChanger
        mytimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            myRate = Math.max(1, myRate - 1);
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(myRate), event2 -> {
                computerMove();
            }));
            timeline.play();
        }));
        mytimeline.setCycleCount(Timeline.INDEFINITE);
        mytimeline.play();
    }

    //    void timer() {
//        startTime = System.currentTimeMillis();
    // Create a new AnimationTimer
//        timer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                if (!paused) {
//                    currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//                    int seconds = (int) (60 - elapsedTime / 1000);
//                    lbTimer.setText(String.valueOf(seconds));
//                    if (seconds <= 0) {
//                        this.stop();
//                        timeOut();
//                    }
//                    if (game == false) {
//                        this.stop();
//                    }
//                }
//            }
//        };
    void timer() {
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!paused) {
                    elapsedTime += System.currentTimeMillis() - startTime;
                    startTime = System.currentTimeMillis();
                    // 60 seconds in milliseconds
                    long countDown = 10000;
                    long remainingTime = countDown - elapsedTime;
                    int seconds = (int) (10 - elapsedTime / 1000);
                    lbTimer.setText(String.valueOf(seconds));
                    if (remainingTime <= 0) {
                        if (myStage == 4) {
                            GameOver();
                        } else if (myScore < 0) {
                            game = false;
                            GameOver();
                        } else {
                            // countdown is finished, stop the timer
                            resetBoard();
                        }

                    }
                }
            }
        };
        timer.start();
        // Start the timer

    }

    public void pauseTimer() {
        paused = true;  // pause the countdown timer
        elapsedTime += System.currentTimeMillis() - startTime;  // update elapsed time
        System.out.println("pauseTimer");
        if (myStage >= 3) {
            mytimeline.stop();
            timeline.stop();
        }
    }

    public void resumeTimer() {
        paused = false;  // resume the countdown timer
        startTime = System.currentTimeMillis();  // reset start time
        System.out.println("resumeTimer");
        if (myStage >= 3)
            mytimeline.play();
    }

    void resetTimer() {
        timer.stop();
        if (myStage == 3) {
            startMyTimeLine();
        }
        if (myStage == 4) {
            timeline.stop();
            mytimeline.stop();
            mytimeline.playFromStart();
        }
        timer();
    }

    private void timeOut() {
        // Create a new alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Set the message for the alert
        alert.setContentText("This is a message to the user");

        // Set the timeout for the alert
        alert.setOnCloseRequest(event -> {
            // Get the stage of the alert
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

            // Set the timeout for the stage
            stage.setOnCloseRequest(timeoutEvent -> {
                // Close the alert after a specified timeout period
                stage.close();
            });
            resetBoard();
        });
        // Show the dialog to the user
        Platform.runLater(alert::showAndWait);
    }

    void generateBlockedTiles() {
        Tile tile = selectRandomEmptyTile();
        Image image = new Image(String.valueOf(getClass().getResource("/view/pieces/Blocked.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        tile.getChildren().add(imageView);
        tile.setType(TileType.BlockedTile);
    }

    //generate 3 quastions in the Game with different levels
    private void questionMarkByLevel(int level) {
        Tile tile = selectRandomEmptyTile();
        Image image = new Image(String.valueOf(getClass().getResource("/view/pieces/QuestionMark.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        System.out.println(tile);
        tile.getChildren().add(imageView);
        tile.setType(TileType.QuestionTile);
        tile.setQuestion(selectRandomQuestionByLevel(level));
        switch(level){
            case 1->{
                tile.setBorder(new Border(new BorderStroke(Color.WHITE,
                        BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(7))));
            }
            case 2->{
                tile.setBorder(new Border(new BorderStroke(Color.YELLOW,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(7))));
            }
            case 3->{
                tile.setBorder(new Border(new BorderStroke(Color.RED,
                        BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(7))));
            }
        }

    }

    private Tile selectRandomEmptyTile() {
        // Set a maximum number of iterations
        int maxIterations = cb.getTiles().size();

        // Try to find an empty tile a maximum of maxIterations times
        for (int i = 0; i < maxIterations; i++) {
            // Select a random index from the list of tiles
            int index = rand.nextInt(cb.getTiles().size());

            // Make sure the index is within the bounds of the list
            if (index < cb.getTiles().size()) {
                // Get the tile at the selected index
                Tile tile = cb.getTiles().get(index);

                // If the tile is empty, return it
                if (tile.getType() == TileType.Nothing && !tile.isOccupied()) {
                    return tile;
                }
            }
        }
        // If no empty tiles were found after maxIterations iterations, return null
        return null;
    }

    private void generateRandomTile() {
        Tile tile = selectRandomEmptyTile();
        if (tile.getType() == TileType.Nothing) {
            tile.setType(TileType.RandomTile);
        }
    }

    private void generateQuestionTile() {

    }

    private void generateForgetTile() {
        Tile tile = selectRandomEmptyTile();
        tile.setType(TileType.ForgetTile);
    }


//   private ArrayList<Tile> emptyTiles()
//    {
//        ArrayList<Tile> emptyTiles = new ArrayList<>();
//        for (Tile tile : cb.getTiles()) {
//            if (tile.getType() == TileType.Nothing && !tile.isOccupied()) ;
//            {
//                emptyTiles.add(tile);
//            }
//
//        }
//        return emptyTiles;
//    }

//    private Tile dropToRandomTile() {
//        ArrayList<Tile>  myTiles = emptyTiles();
//        return myTiles.get(rand.nextInt(myTiles.size()));
//    }

    private void randomTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!paused) {
                    elapsedTime += System.currentTimeMillis() - startTime;
                    startTime = System.currentTimeMillis();
                    // 10 seconds in milliseconds
                    long randomCountDown = 500;
                    long remainingTime = randomCountDown - elapsedTime;
                    if (remainingTime <= 0) {
                        // countdown is finished, move the king


                        animationRandom();

                        // reset the timer
                        elapsedTime = 0;
                        startTime = System.currentTimeMillis();
                    }
                }
            }
        };
        timer.start();
        // Start the timer
    }

    void animationRandom() {
        Random rand = new Random();
        Tile tile = cb.getTiles().get(rand.nextInt(cb.getTiles().size()));
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.9);  // shift the hue towards red
        colorAdjust.setSaturation(1.0);  // increase the saturation
        colorAdjust.setBrightness(0.5);  // increase the brightness by 0.5
        colorAdjust.setContrast(0.5);  // increase the contrast by 0.5
        tile.setEffect(colorAdjust);
        numberOfGlow--;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
            tile.setEffect(null);
            if (numberOfGlow > 0)
                animationRandom();
        }));
        timeline.play();
    }

    //        for (Tile tile:cb.getTiles()) {
//            tile.setBorder(new Border(new BorderStroke(Color.BLACK,
//                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//        }
    void dropRandomPiece(Tile tile) {
        textArea.setText(textArea.getText() + "\n jump to Randommmmm");
        System.out.println("0 of func");
        Tile initialSquare = (Tile) myPiece.getParent();
        System.out.println(initialSquare);
        System.out.println(initialSquare.getX());
        System.out.println(initialSquare.getY());
        System.out.println(tile);
        System.out.println(tile.getX());
        System.out.println(tile.getY());
        tile.getChildren().add(myPiece);
        System.out.println("0.7 of func");
        tile.setOccupied(true);
        System.out.println("1 of func");
        if (!tile.isVisited()) {
            visitedTile(tile);
        }
        System.out.println("2 of func");
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        myPiece.setPosX(tile.getX());
        myPiece.setPosY(tile.getY());
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (computerPiece != null && computerPiece.getType().equals("Queen")) {
                computerMove();
            } else {
                mytimeline.play();
            }
            deselectPiece(true);
//            System.out.println("3 of func");
//            if (!tile.getType().equals(TileType.Nothing)) {
//                stages();
//            }
//            System.out.println("end of func");
        }));
        timeline.play();
        if (computerPiece.getType().equals("King")) {
            this.timeline.stop();
            mytimeline.stop();
        }
    }

    void visitedTile(Tile tile) {
        tile.setVisited(true);
        setBackgroundVisited(tile);
        myScore++;
        lblScore.setText("Score: " + myScore);
        visitedTiles.add(tile);
    }

    void alertDisplayer() {
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
    @FXML
    void resetButton(ActionEvent event) throws IOException {
        cb.getChessBoard().getChildren().clear();
        if (myStage > 2) {
            timeline.stop();
            mytimeline.stop();
        }
        initialize();
    }

    @FXML
    void stageTwo() {
        //        StartMyTimer();
        computerPiece = cb.getQueen();
        generateForgetTile();
        generateForgetTile();
        generateForgetTile();
        questionMarkByLevel(1);
        questionMarkByLevel(2);
        questionMarkByLevel(3);
    }

    @FXML
    void stageThree() {
        computerPiece = cb.getKing();
        generateForgetTile();
        generateForgetTile();
        generateRandomTile();
        generateRandomTile();
        questionMarkByLevel(1);
        questionMarkByLevel(2);
        questionMarkByLevel(3);
    }

    @FXML
    void stageFour() {
        computerPiece = cb.getKing();
        for (int i = 0; i < 8; i++) {
            generateBlockedTiles();
        }
        questionMarkByLevel(1);
        questionMarkByLevel(2);
        questionMarkByLevel(3);
    }

    @FXML
    void resetBoard() {
        chessBoard.setDisable(true);
        cb.getChessBoard().getChildren().clear();
        cb = new ChessBoard(chessBoard, theme, 8);
        myPiece = cb.getKnight();
        switch (myStage) {
            case 1 -> {
                stageTwo();
            }
            case 2 -> {
                stageThree();
            }
            case 3 -> {
                stageFour();
            }
        }
        myStage++;
        totalScore = myScore + totalScore;
        myScore = 1;
        lblStage.setText("Stage: " + myStage);
        lblScore.setText("Score: " + myScore);
        visitedTile(cb.getTiles().get(0));
        resetTimer();
        selectPiece(true);
        chessBoard.setDisable(false);
    }

    @FXML
    void backButton(ActionEvent event) throws IOException {
        if (myStage >= 3) {
            pauseTimer();
        }
        timer.stop();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomePage.fxml")));
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
            if (!tile.isOccupied()) {// Drop piece on blank tile
                dropPiece(tile);
            }
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
        System.out.println("computerMove:" + moves);
        String str = "";
        if (computerPiece.getType().equals("Queen")) {
            str = findBestMove(moves, "Queen");
        } else {
            str = findBestMove(moves, "King");
        }

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

    private String queenBestMove(ArrayList<String> moves) {
        int score = 0;
        int maxScore = score;
        String bestMove = null;
        for (String move : moves) {
            Tile tile = Piece.getTileByName(move);
            if (tile != null && (tile.getX() == myPiece.getPosX()) && (tile.getY() == myPiece.getPosY())) {
                System.out.println("Kill!!!");
                return String.format("Tile%d%d", tile.getX(), tile.getY());
            }
            score = evaluateMove(tile);
            if (score > maxScore) {
                maxScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private String kingBestMove(ArrayList<String> moves) {
        int x = computerPiece.getPosX(), y = computerPiece.getPosY();
        System.out.println("myPiece.getPosX():" + myPiece.getPosX());
        System.out.println("myPiece.getPosY():" + myPiece.getPosY());
        for (String move : moves) {
            Tile tile = Piece.getTileByName(move);
            if ((tile.getX() == myPiece.getPosX()) && (tile.getY() == myPiece.getPosY())) {
                System.out.println("Kill!!!");
                return String.format("Tile%d%d", tile.getX(), tile.getY());
            }
            if ((Math.abs(tile.getX() - myPiece.getPosX()) <= Math.abs(x - myPiece.getPosX()))
                    && (Math.abs(tile.getY() - myPiece.getPosY()) <= Math.abs(y - myPiece.getPosY()))) {
                x = tile.getX();
                y = tile.getY();
            }
        }
        return String.format("Tile%d%d", x, y);
    }

    private String findBestMove(ArrayList<String> moves, String pieceType) {
        System.out.println("Computer Piece:" + pieceType);
        if (pieceType.equals("Queen")) {
            return queenBestMove(moves);
        } else {//pieceType.equals("King")
            return kingBestMove(moves);
        }
    }

    private int evaluateMove(Tile tile) {
        Piece myKnight = PieceFactory.createPiece(myPiece.getType(), "black", myPiece.getPosX(), myPiece.getPosY());
        int score = 0;
        Piece piece = PieceFactory.createPiece(computerPiece.getType(), "white", tile.getX(), tile.getY());
        piece.setPosX(tile.getX());
        piece.setPosY(tile.getY());
        piece.getAllPossibleMoves();
        System.out.println("myPieceMove" + myPiece.getPossibleMoves());
        System.out.println("computerMove:" + piece.getPossibleMoves());
        for (String move : piece.getPossibleMoves()) {
            if (myPiece.getPossibleMoves().contains(move)) {
                System.out.println("move:" + move);
                score++;
            }
        }
        System.out.println(tile.getName() + " Score:" + score);
        return score;
    }

    private void dropPiece(Tile tile) {
        if (!myPiece.getPossibleMoves().contains(tile.getName())) return;
        if (tile.getChildren().size() > 0) {
            tile.getChildren().clear();
        }
        Tile initialSquare = (Tile) myPiece.getParent();
        tile.getChildren().add(0, myPiece);
        tile.setOccupied(true);
        if (tile.isVisited()) {
            myScore = Math.max(myScore - 1, 0);
        } else {
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
        if (!tile.getType().equals(TileType.Nothing)) {
            System.out.println(tile.getType());
            handleSpecialTiles(tile);
        }
        deselectPiece(true);
        if (computerPiece != null) {
            if (!tile.getType().equals(TileType.RandomTile))
                computerMove();
        }
//        if(!tile.getType().equals(TileType.Nothing)){
////            alertDisplayer();
//            AlertDisplayer alertDisplayer1 = new AlertDisplayer();
////            alertDisplayer1.showOneSecondAlert("","");
//            stages();
//        }
    }

    private void handleSpecialTiles(Tile tile) {
        switch (tile.getType()) {
            case ForgetTile -> {
                forgetTile();
            }
            case RandomTile -> {
                randomTile();
            }
            case QuestionTile -> {
                questionTile(tile.getQuestion());
                tile.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
        tile.setType(TileType.Nothing);
    }

    private void forgetTile() {
        int count = 3;
        while (visitedTiles.size() > 0 && count > 0) {
            Tile tile = visitedTiles.get(visitedTiles.size() - 1);
            visitedTiles.remove(visitedTiles.size() - 1);
            tile.setVisited(false);
            setBackgroundNotVisited(tile);
            count--;
        }
        Tile tile = selectRandomEmptyTile();
        tile.setType(TileType.ForgetTile);
    }

    private void questionTile(Question q) {
        pauseTimer();
//        if(questions.isEmpty()){
//            questions = (ArrayList<Question>) SysData.getInstance().getQuestions().clone();
//        }
//        Question q = questions.get(rand.nextInt(questions.size()));
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Question");
        dialog.setHeaderText(q.getQuestionID());
        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the radio buttons.
        ToggleGroup group = new ToggleGroup();
        RadioButton answer1 = new RadioButton(q.getAnswers().get(1));
        answer1.setToggleGroup(group);
        RadioButton answer2 = new RadioButton(q.getAnswers().get(2));
        answer2.setToggleGroup(group);
        RadioButton answer3 = new RadioButton(q.getAnswers().get(3));
        answer3.setToggleGroup(group);
        RadioButton answer4 = new RadioButton(q.getAnswers().get(4));
        answer4.setToggleGroup(group);

// Add the radio buttons to the dialog.
        VBox vbox = new VBox(answer1, answer2, answer3, answer4);
        dialog.getDialogPane().setContent(vbox);

// Convert the result to a string when the OK button is clicked.
        int correctAnswer = q.getCorrect_ans();
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButtonType) {
                if (answer1.isSelected()) {
                    return String.valueOf(correctAnswer == 1);
                } else if (answer2.isSelected()) {
                    return String.valueOf(correctAnswer == 2);
                } else if (answer3.isSelected()) {
                    return String.valueOf(correctAnswer == 3);
                } else if (answer4.isSelected()) {
                    return String.valueOf(correctAnswer == 4);
                }
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Selected option: " + result.get());
            if (result.get().equals("true")) {
                switch (q.getLevel()) {
                    case 1 -> {
                        myScore++;

                    }
                    case 2 -> {
                        myScore = myScore + 2;
                    }
                    case 3 -> {
                        myScore = myScore + 3;
                    }
                }
            } else {
                System.out.println(q.getLevel());
                switch (q.getLevel()) {
                    case 1 -> {
                        myScore = Math.max(myScore - 2, 0);

                    }
                    case 2 -> {
                        myScore = Math.max(myScore - 3, 0);
                    }
                    case 3 -> {
                        myScore = Math.max(myScore - 4, 0);
                    }
                }
            }
            resumeTimer();
        }
        System.out.println("myScore" + myScore);
        lblScore.setText("Score: " + myScore);
        questions.remove(q);
        questionMarkByLevel(q.getLevel());
        //////////////////////////////////////
    }

    private void randomTile() {
        chessBoard.setDisable(true);
        numberOfGlow = 8;
        pauseTimer();
        Image image = new Image(String.valueOf(getClass().getResource("/view/images/giphy.gif")));
        ImageView imageView = new ImageView(image);
        imgTimer.setImage(image);
        animationRandom();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.7), event -> {
            // code to execute after the pause
//            Tile randomTile = dropToRandomTile();
            Tile randomTile = selectRandomEmptyTile();
            dropRandomPiece(randomTile);
            System.out.println("Randommmmm!!!");
            imgTimer.setImage(null);
            resumeTimer();
            chessBoard.setDisable(false);
        }));
        generateRandomTile();
        timeline.play();
    }

    private void setBackgroundVisited(Tile tile) {
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

    private void setBackgroundNotVisited(Tile tile) {
        cb.setTheme(tile, cb.getTheme(), tile.getX(), tile.getY());
    }


    private void dropComputer(Tile tile) {
        if (!computerPiece.getPossibleMoves().contains(tile.getName())) return;
        System.out.println("dropComputer");
        Tile initialSquare = (Tile) computerPiece.getParent();
        tile.getChildren().add(0, computerPiece);
        tile.setOccupied(true);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        computerPiece.setPosX(tile.getX());
        computerPiece.setPosY(tile.getY());
    }

    private void killMyPiece(Tile tile) {
        if (!computerPiece.getPossibleMoves().contains(tile.getName())) return;
        System.out.println("killMyPiece:" + tile.getChildren());
        Piece killedPiece = (Piece) tile.getChildren().get(0);
        if (killedPiece.getType().equals("Knight")) {
            this.game = false;
            System.out.println("Game Over");
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
        if (!this.game) {
            GameOver();
        }
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
        pauseTimer();

        timer.stop();
        if (!game) {
            gameOverPage("GameOver");
        } else {
            gameOverPage("Win");
        }
        try {
            SysData.getInstance().addHighScore(myNickName.getText(), totalScore);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void gameOverPage(String myString){
        FXMLLoader loader;
        boolean result;
        if (myString.equals("Win")) {
         result = true;
        } else {
            result = false;

        }
        loader = new FXMLLoader(getClass().getResource("/view/GoToGameOver.fxml"));

        loader.setControllerFactory((Class<?> type) -> {
            if (type == goToGameOver.class) {
                return new goToGameOver(myNickName.getText(), totalScore,result);
            } else {
                try {
                    return type.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) GamePage.getScene().getWindow();
        scene = GamePage.getScene();
        scene.setRoot(root);
        stage.setScene(scene);

        stage.show();
    }
}