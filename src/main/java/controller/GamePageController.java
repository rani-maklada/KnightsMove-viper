package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import Enums.TileType;
import javafx.geometry.Pos;
import javafx.scene.effect.ColorAdjust;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import javafx.animation.AnimationTimer;

/**
 * The game Page to play KnightMove.
 * this class is responsible for handling the game
 * logic and UI of KnightMove.
 */
public class GamePageController {
    @FXML private AnchorPane GamePage;
    @FXML private ImageView imgTimer;

    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML private GridPane chessBoard;
    @FXML private Label lbTimer;
    @FXML private TextArea textArea;
    @FXML private Label myNickName;
    @FXML private Label lblScore;
    @FXML private Label lblStage;
    public static Piece myPiece;
    public static Piece computerPiece;
    private Timeline kingTimeLine;
    private final String myNickHolder;
    public static ChessBoard cb;
    private final String theme;
    private boolean game;
    private ArrayList<Tile> visitedTiles;
    private int myScore;
    private final Random rand = new Random();
    private ArrayList<Question> questions = new ArrayList<>();
    public static int myStage;
    private long startTime;
    private AnimationTimer timer;
    private Timeline myRateTimeLine;
    private boolean paused = false;
    private long elapsedTime = 0;
    private int numberOfGlow;
    private double myRate;
    private int totalScore;
    /**
     * Creates a new instance of the class with the given nickname and theme.
     * @param name the nickname of the player
     * @param theme the theme of the chess board
     */
    public GamePageController(String name, String theme) {
        this.myNickHolder = name;
        this.theme = theme;
    }
    /**
     * Initializes the GamePageController.
     * This method is called automatically when the FXML file is loaded.
     * @throws IOException if there is an issue loading the FXML file
     */
    @FXML
    void initialize() throws IOException {
        // Initialize variables and set text for labels
        myStage = 1;
        totalScore = 0;
        myScore = 0;
        myNickName.setText(myNickHolder);
        lblScore.setText("Your Stage Score: " + myScore);
        lblStage.setText("Stage: " + myStage);
        textArea.setText("");
        // Get a copy of the list of questions
        questions = (ArrayList<Question>) SysData.getInstance().getQuestions().clone();
        // Create a new chess board with the specified theme and size
        cb = new ChessBoard(chessBoard, theme, 8);
        // Set the knight and queen pieces
        myPiece = cb.getKnight();
        computerPiece = cb.getQueen();
        // Set game to true
        this.game = true;
        // Set the timer label's color and font size
        lbTimer.setTextFill(Color.BLUE);
        lbTimer.setStyle("-fx-font-size: 2em;");
        // Initialize the list of visited tiles and add the first tile
        visitedTiles = new ArrayList<>();
        visitedTile(cb.getTiles().get(0));
        // Generate 3 random tiles
        generateRandomTile();
        generateRandomTile();
        generateRandomTile();
        // Select the knight piece and set question marks for each level
        selectPiece(true);
        questionMarkByLevel(1);
        questionMarkByLevel(2);
        questionMarkByLevel(3);
        // Start the timer
        timer();
    }
    /**
     Returns a list of questions with the specified level
     @param level the level of the questions to be returned
     @return a list of questions with the specified level
     */
    private ArrayList<Question> questionsByLevel(int level) {
        ArrayList<Question> q = new ArrayList<>();
        for (Question question : questions) {
            if (question.getLevel() == level) {
                q.add(question);
            }
        }
        return q;
    }
    /**
     * Select a random question from the list of questions with the specified level.
     * @param level the level of the question
     * @return a random question with the specified level
     */
    private Question selectRandomQuestionByLevel(int level) {
        // Get a list of questions with the specified level
        ArrayList<Question> q = questionsByLevel(level);
        // Return a random question from the list
        return q.get(rand.nextInt(q.size()));
    }
    /**
     * Plays a timeline to control the computer's movement.
     * The original rate of the timeline decreases every 10 seconds to increase
     * the difficulty of the game. The timeline will repeat indefinitely until the game ends.
     */
    private void playKingTimeLine() {
        // Initialize myRate to 7
        myRate = 7;
        // Create a new timeline that will execute a computerMove every 10 seconds
        kingTimeLine = new Timeline(new KeyFrame(Duration.seconds(myRate), event -> {
            computerMove();
        }));
        // Set the timeline to repeat indefinitely
        kingTimeLine.setCycleCount(Timeline.INDEFINITE);
        // Start the timeline
        kingTimeLine.play();
        // Create another timeline that will execute every 10 seconds
        myRateTimeLine = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            // Decrease myRate by 1, but make sure it doesn't go below 1
            myRate = Math.max(1, myRate - 1);
            // Stop the original timeline
            kingTimeLine.stop();
            // Clear the keyframes of the original timeline
            kingTimeLine.getKeyFrames().clear();
            // Add a new keyframe to the original timeline with the updated rate
            kingTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(myRate), event2 -> {
                computerMove();
            }));
            // Start the original timeline again
            kingTimeLine.play();
        }));
        // Set the second timeline to repeat indefinitely
        myRateTimeLine.setCycleCount(Timeline.INDEFINITE);
        // Start the second timeline
        myRateTimeLine.play();
    }
    /**
     * A method to start the timer for the game. The timer counts down from 60 seconds.
     * When the timer reaches 0 seconds, the game ends if it is the final stage or
     * the player's score is less than 15. Otherwise, the board is reset and a new stage begins.
     */
    void timer() {
        // Set the start time to the current system time in milliseconds
        startTime = System.currentTimeMillis();
        // Set the elapsed time to 0
        elapsedTime = 0;
        // Create a new AnimationTimer
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // If the game is not paused
                if (!paused) {
                    // Add the elapsed time since the last update to the total elapsed time
                    elapsedTime += System.currentTimeMillis() - startTime;
                    // Set the start time to the current system time in milliseconds
                    startTime = System.currentTimeMillis();
                    // Set the countdown time to 60 seconds in milliseconds
                    long countDown = 60000;
                    // Calculate the remaining time
                    long remainingTime = countDown - elapsedTime;
                    // Calculate the number of seconds remaining
                    int seconds = (int) (60 - elapsedTime / 1000);
                    // Set the text of the timer label to the number of seconds remaining
                    lbTimer.setText(String.valueOf(seconds));
                    // If the timer has reached 0 seconds
                    if (remainingTime <= 0) {
                        // If it is the final stage
                        if (myStage == 4) {
                            // End the game
                            GameOver();
                        }// If the player's score is less than 15
                        else if (myScore < 15) {
                            game = false;
                            GameOver();
                        }// If it is not the final stage and the player's score is greater than or equal to 15
                        else {
                            // Reset the board and start a new stage
                            resetBoard();
                        }

                    }
                }
            }
        };
        // Start the AnimationTimer
        timer.start();
    }
    /**
     * Pauses the countdown timer.
     */
    public void pauseTimer() {
        paused = true;  // pause the countdown timer
        elapsedTime += System.currentTimeMillis() - startTime;  // update elapsed time
        // If the current stage is 3 or 4, stop the king's timeline and rate timeline
        if (myStage >= 3) {
            myRateTimeLine.stop();
            kingTimeLine.stop();
        }
    }
    /**
     * Resumes the countdown timer.
     */
    public void resumeTimer() {
        paused = false;  // resume the countdown timer
        startTime = System.currentTimeMillis();  // reset start time
        // If the current stage is 3 or 4, stop the king's timeline and rate timeline
        if (myStage >= 3)
            myRateTimeLine.play();
    }
    /**
     * Resets the countdown timer.
     */
    void resetTimer() {
        timer.stop();
        // If the current stage is 3, start the king's timeline and rate timeline
        if (myStage == 3) {
            playKingTimeLine();
        }
        // If the current stage is 4, stop the king's timeline,
        // start the rate timeline from the beginning
        if (myStage == 4) {
            kingTimeLine.stop();
            myRateTimeLine.stop();
            myRateTimeLine.playFromStart();
        }
        // Start the countdown timer
        timer();
    }
    /**
     * Generates a random empty tile and sets it as a blocked tile.
     */
    void generateBlockedTiles() {
        // Select a random empty tile
        Tile tile = selectRandomEmptyTile();
        // Load the image for the blocked tile
        Image image = new Image(String.valueOf(getClass().getResource("/view/pieces/Blocked.png")));
        // Create an ImageView for the blocked tile image
        ImageView imageView = new ImageView(image);
        // Set the dimensions of the ImageView
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        // Add the ImageView to the tile's children
        tile.getChildren().add(imageView);
        // Set the tile's type to BlockedTile
        tile.setType(TileType.BlockedTile);
    }

    /**
     * Adds a question mark to a random empty tile on the board. The question mark is associated with a
     * randomly selected question of the specified level. The border color of the tile is also set based on
     * the level of the question.
     * @param level the level of the question to be associated with the question mark
     */
    private void questionMarkByLevel(int level) {
        // Select a random empty tile
        Tile tile = selectRandomEmptyTile();
        // Load the question mark image and set its dimensions
        Image image = new Image(String.valueOf(getClass().getResource("/view/pieces/QuestionMark.png")));
        ImageView imageView = new ImageView(image);
        // Add the image view to the selected tile
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        // Set the type of the tile to QuestionTile and associate a random question
        // of the specified level with the tile
        tile.getChildren().add(imageView);
        tile.setType(TileType.QuestionTile);
        tile.setQuestion(selectRandomQuestionByLevel(level));
        // Set the border color of the tile based on the level of the question
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

    /**
     * Select a random empty tile from the chess board.
     * @return The selected empty tile, or null if no empty tiles were found.
     */
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

    /**
     *
     */
    private void generateRandomTile() {
        Tile tile = selectRandomEmptyTile();
        if (tile.getType() == TileType.Nothing) {
            tile.setType(TileType.RandomTile);
        }
    }

    private void generateForgetTile() {
        Tile tile = selectRandomEmptyTile();
        tile.setType(TileType.ForgetTile);
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
    void dropRandomPiece(Tile tile) {
        textArea.setText(textArea.getText() + "\n jump to Randommmmm");
        Tile initialSquare = (Tile) myPiece.getParent();
        System.out.println(initialSquare);
        System.out.println(initialSquare.getX());
        System.out.println(initialSquare.getY());
        System.out.println(tile);
        System.out.println(tile.getX());
        System.out.println(tile.getY());
        tile.getChildren().add(myPiece);
        tile.setOccupied(true);
        if (!tile.isVisited()) {
            visitedTile(tile);
        }
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        myPiece.setPosX(tile.getX());
        myPiece.setPosY(tile.getY());
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (computerPiece != null && computerPiece.getType().equals("Queen")) {
                computerMove();
            } else {
                myRateTimeLine.play();
            }
            deselectPiece(true);
        }));
        timeline.play();
        if (computerPiece.getType().equals("King")) {
            kingTimeLine.stop();
            myRateTimeLine.stop();
        }
    }

    void visitedTile(Tile tile) {
        tile.setVisited(true);
        setBackgroundVisited(tile);
        myScore++;
        lblScore.setText("Your Stage Score: " + myScore);
        visitedTiles.add(tile);
    }
    @FXML
    void resetButton(ActionEvent event) throws IOException {
        cb.getChessBoard().getChildren().clear();
        if (myStage > 2) {
            kingTimeLine.stop();
            myRateTimeLine.stop();
        }
        initialize();
    }
    @FXML
    void stageTwo() {
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
        lblScore.setText("Your Stage Score: " + myScore);
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
        String str = "";
        if (computerPiece.getType().equals("Queen")) {
            str = findBestMove(moves, "Queen");
        } else {
            str = findBestMove(moves, "King");
        }
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
        for (String move : moves) {
            Tile tile = Piece.getTileByName(move);
            if ((tile.getX() == myPiece.getPosX()) && (tile.getY() == myPiece.getPosY())) {
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
        if (pieceType.equals("Queen")) {
            return queenBestMove(moves);
        } else {//pieceType.equals("King")
            return kingBestMove(moves);
        }
    }

    private int evaluateMove(Tile tile) {
        int score = 0;
        Piece piece = PieceFactory.createPiece(computerPiece.getType(), "white");
        piece.setPosX(tile.getX());
        piece.setPosY(tile.getY());
        piece.getAllPossibleMoves();
        for (String move : piece.getPossibleMoves()) {
            if (myPiece.getPossibleMoves().contains(move)) {
                System.out.println("move:" + move);
                score++;
            }
        }
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
        lblScore.setText("Your Stage Score: " + myScore);
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
        Image image = new Image(String.valueOf(getClass().getResource("/view/images/giphy.gif")));
        imgTimer.setImage(image);
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().setMinSize(600,400);
        dialog.getDialogPane().setMaxSize(600,400);
        dialog.setTitle("Question");
        // Style the header
        StackPane headerPane = new StackPane();
        headerPane.setStyle(" -fx-padding: 10px;");
        Label headerLabel = new Label(q.getQuestionID());
        headerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 11pt;");
        headerLabel.setWrapText(true);
        headerPane.getChildren().add(headerLabel);
        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
        // Style the content
        String URL = String.valueOf(getClass().getResource("/view/images/backQ.jpg"));
        String URL2 = String.valueOf(getClass().getResource("/view/application.css"));
        dialog.getDialogPane().getStylesheets().add(URL2);
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
        answer1.setWrapText(true);
        answer1.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        answer2.setWrapText(true);
        answer2.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        answer3.setWrapText(true);
        answer3.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        answer4.setWrapText(true);
        answer4.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        // Add the radio buttons to the dialog.
        VBox contentPane = new VBox();
        contentPane.setSpacing(20);
        contentPane.setAlignment(Pos.CENTER_LEFT);
        contentPane.getChildren().addAll(answer1, answer2, answer3, answer4);
        dialog.getDialogPane().setContent(contentPane);
        // Set the header and content
        VBox root = new VBox(headerPane, contentPane);
        root.setPadding(new Insets(30, 50, 50, 75));
        root.setSpacing(40);
        dialog.getDialogPane().setContent(root);
        dialog.getDialogPane().setStyle("-fx-background-image:url('"+URL+"');");
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
        lblScore.setText("Your Stage Score: " + myScore);
        questions.remove(q);
        questionMarkByLevel(q.getLevel());
        imgTimer.setImage(null);

    }

    private void randomTile() {
        chessBoard.setDisable(true);
        numberOfGlow = 8;
        pauseTimer();
        Image image = new Image(String.valueOf(getClass().getResource("/view/images/giphy.gif")));
        imgTimer.setImage(image);
        animationRandom();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.7), event -> {
            // code to execute after the pause
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
        totalScore = totalScore+myScore;
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