package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    @FXML private Label lblStage;
    @FXML private ColorPicker colorPicker;
    public static Piece myPiece;
    public static Piece computerPiece;
    public static String currentPlayer;
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
    private int myStage;
    private long startTime;
    private long currentTime;
    private AnimationTimer timer;
    private boolean paused = false;
    private long elapsedTime = 0;
    private int numberOfGlow;

    public GamePageController(String name,String theme){
        this.myNickHolder = name;
        this.theme = theme;
    }
    @FXML
    void initialize() throws IOException {
        questions = (ArrayList<Question>) SysData.getInstance().getQuestions().clone();
//        colorPicker.getCustomColors();
        cb = new ChessBoard(chessBoard, theme,8);
        myPiece = cb.getKnight();
        computerPiece = cb.getKing();
        currentPlayer = "black";
        this.game = true;
//        lbTimer.setText(timeSeconds.toString());
        lbTimer.setTextFill(Color.BLUE);
        lbTimer.setStyle("-fx-font-size: 2em;");
        myNickName.setText(myNickHolder);
        myScore=0;
        lblScore.setText("Score: " + myScore);
        myStage=1;
        lblStage.setText("Stage: " + myStage);
//        StartMyTimer();
        generateRandomTile();
        selectPiece(true);
        questionMark();
        questionMark();
        questionMark();
        timer();
//        myPiece.getAllPossibleMoves();

        Timeline mytimeline = new Timeline();

// Set the duration between each execution to 2 seconds
        Duration duration = Duration.seconds(2);

// Create a KeyFrame object that specifies the code to be executed
// and the duration to wait before executing it
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            // This code will be executed every 2 seconds
            computerMove();
            System.out.println("Executing code");
        });
        // Add the KeyFrame to the Timeline
        mytimeline.getKeyFrames().add(keyFrame);

// Set the number of times the Timeline should repeat
// Use Timeline.INDEFINITE to repeat indefinitely
        mytimeline.setCycleCount(Timeline.INDEFINITE);

// Start the Timeline
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
void timer(){
    startTime = System.currentTimeMillis();
    elapsedTime=0;
    timer = new AnimationTimer() {
        @Override
            public void handle(long now) {
                if (!paused) {
                    elapsedTime += System.currentTimeMillis() - startTime;
                    startTime = System.currentTimeMillis();
                    // 60 seconds in milliseconds
                    long countDown = 60000;
                    long remainingTime = countDown - elapsedTime;
                    int seconds = (int) (60 - elapsedTime / 1000);
                    lbTimer.setText(String.valueOf(seconds));
                    if (remainingTime <= 0) {
                        if (myScore < 15){
                            game=false;
                            GameOver();
                        }else{
                            // countdown is finished, stop the timer
                            resetTimer();
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
    }
    public void resumeTimer() {
        paused = false;  // resume the countdown timer
        startTime = System.currentTimeMillis();  // reset start time
        System.out.println("resumeTimer");
    }
    void resetTimer() {
        timer.stop();
        timer();
    }
    public void someMethod(){
        pauseTimer();
        System.out.println("Wait");
        resumeTimer();
    }

    void kingTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!paused) {
                    elapsedTime += System.currentTimeMillis() - startTime;
                    startTime = System.currentTimeMillis();
                    // 10 seconds in milliseconds
                    long kingCountDown = 10000;
                    long remainingTime = kingCountDown - elapsedTime;
                    if (remainingTime <= 0) {
                        // countdown is finished, move the king
                        moveKing();
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

    private void moveKing() {

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

    void questionMark(){
        Tile tile = selectRandomEmptyTile();
        Image image = new Image(String.valueOf(getClass().getResource("/view/pieces/QuestionMark.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
//        cb.getTiles().get(x).getChildren().add(imageView);
//        cb.getTiles().get(x).setType("Question");
        System.out.println(tile);
        tile.getChildren().add(imageView);
        tile.setType("Question");
    }
    Tile selectRandomEmptyTile() {
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
                if (tile.getType() == null) {
                    return tile;
                }
            }
        }
        // If no empty tiles were found after maxIterations iterations, return null
        return null;
    }
    void generateRandomTile(){
        specialTiles = new ArrayList<String>();
        int rand_intX;
        int rand_intY;
        while(specialTiles.size()<20){
            rand_intX = rand.nextInt(8);
            rand_intY = rand.nextInt(8);
            specialTiles.add("Tile"+rand_intX+rand_intY);
        }
        for (Tile tile:cb.getTiles()) {
            if(specialTiles.contains(tile.getName())){
                tile.setType("RandomJump");
            }
        }
        System.out.println("specialTiles:"+specialTiles);
    }
    void stages(){
        switch(myStage){
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

    void firstStage() {


        numberOfGlow = 8;
        pauseTimer();
        Image image = new Image("C:\\Users\\the_l\\Documents\\GitHub\\KnightsMove-viper\\src\\main\\resources\\view\\images\\giphy.gif");


        ImageView imageView = new ImageView(image);
        imgTimer.setImage(image);
        animationRandom();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.7), event -> {
            // code to execute after the pause
            dropRandomPiece(cb.getTiles().get(rand.nextInt(cb.getTiles().size())));
            System.out.println("Randommmmm!!!");
            imgTimer.setImage(null);
            resumeTimer();
        }));
        timeline.play();



    }

    void randomTimer() {
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
    void animationRandom(){
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
            if(numberOfGlow>0)
                animationRandom();


        }));
        timeline.play();






        }

//        for (Tile tile:cb.getTiles()) {
//            tile.setBorder(new Border(new BorderStroke(Color.BLACK,
//                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//        }

    void dropRandomPiece(Tile tile){
        textArea.setText(textArea.getText()+"\n jump to Randommmmm");
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
        if(!tile.isVisited()){
            tile.setVisited(true);
            setBackgroundVisited(tile);
            myScore++;
            lblScore.setText("Score: " + myScore);
            visitedTiles.add(tile);
        }
        System.out.println("2 of func");
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        myPiece.setPosX(tile.getX());
        myPiece.setPosY(tile.getY());
        if (computerPiece != null) computerMove();
        deselectPiece(true);
        System.out.println("3 of func");
        if(specialTiles.contains(tile.getName())){
            stages();
        }
        System.out.println("end of func");
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
        pauseTimer();
        if(questions.isEmpty()){
            questions = (ArrayList<Question>) SysData.getInstance().getQuestions().clone();
        }
        Question q = questions.get(rand.nextInt(questions.size()));
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
        VBox vbox = new VBox(answer1, answer2, answer3,answer4);
        dialog.getDialogPane().setContent(vbox);

// Convert the result to a string when the OK button is clicked.
        int correctAnswer = q.getCorrect_ans();
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButtonType) {
                if (answer1.isSelected()) {
                    return String.valueOf(correctAnswer==1);
                } else if (answer2.isSelected()) {
                    return String.valueOf(correctAnswer==2);
                } else if (answer3.isSelected()) {
                    return String.valueOf(correctAnswer==3);
                }else if (answer4.isSelected()){
                    return String.valueOf(correctAnswer==4);
                }
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Selected option: " + result.get());
            if(result.get().equals("true")){
                switch(q.getLevel()){
                    case 1 -> {
                        myScore++;

                    }
                    case 2 -> {
                        myScore=myScore+2;
                    }
                    case 3 -> {
                        myScore=myScore+3;
                    }
                }
            }else{
                System.out.println(q.getLevel());
                switch(q.getLevel()){
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
        System.out.println("myScore"+myScore);
        lblScore.setText("Score: " + myScore);
        questions.remove(q);
        questionMark();
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
    void resetBoard(){
        cb.getChessBoard().getChildren().clear();
        cb = new ChessBoard(chessBoard, theme,8);
        myPiece = cb.getKnight();
        computerPiece = cb.getQueen();
        currentPlayer = "black";
        myStage++;
        myScore=0;
        lblStage.setText("Stage: " + myStage);
//        StartMyTimer();
        generateRandomTile();
        questionMark();
        questionMark();
        questionMark();
        selectPiece(true);
        timer();
    }
//    void StartMyTimer()
//    {
//        if (timeline != null) {
//            timeline.stop();
//        }
//
//        timeSeconds = STARTTIME;
//
//        // update timerLabel
//        lbTimer.setText(timeSeconds.toString());
//        timeline = new Timeline();
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        // KeyFrame event handler
//        timeline.getKeyFrames().add(
//                new KeyFrame(Duration.seconds(1),
//                        (EventHandler) event1 -> {
//                            timeSeconds++;
//                            // update timerLabel
//                            lbTimer.setText(
//                                    timeSeconds.toString());
//                            System.out.println(timeSeconds);
//                            if (timeSeconds >= 20){
//                                timeline.stop();
////                                if(myScore<15){
////                                    this.game=false;
////                                    resetBoard();
////                                    GameOver();
////                                }else if(myStage<4){
////                                    resetBoard();
////                                }else{
////                                    GameOver();
////                                }
//                            }
//                        }));
//        timeline.playFromStart();
//        chessBoard.setDisable(false);
//        selectPiece(true);
//    }


    @FXML
    void backButton(ActionEvent event) throws IOException {
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
        System.out.println("computerMove:"+moves);
        String str = findBestMove(moves);
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

    private String findBestMove(ArrayList<String> moves) {
        int x = computerPiece.getPosX(), y = computerPiece.getPosY();
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
            if (score > maxScore){
                maxScore = score;
                bestMove = move;
            }
            if ((Math.abs(tile.getX() - myPiece.getPosX()) < Math.abs(x - myPiece.getPosX()))
            && (Math.abs(tile.getY() - myPiece.getPosY()) < Math.abs(y - myPiece.getPosY()))) {
                x = tile.getX();
                y = tile.getY();
            }
//            if (Math.abs(tile.getY() - myPiece.getPosY()) < Math.abs(y - myPiece.getPosY())) {
//                y = tile.getY();
//            }
        }
//        return String.format("Tile%d%d", x, y);
        return bestMove;
    }
    
    private int evaluateMove(Tile tile){
        Piece myKnight = PieceFactory.createPiece(myPiece.getType(),"black",myPiece.getPosX(),myPiece.getPosY());
        int score = 0;
        Piece piece = PieceFactory.createPiece(computerPiece.getType(),"white",tile.getX(),tile.getY());
        piece.setPosX(tile.getX());
        piece.setPosY(tile.getY());
        piece.getAllPossibleMoves();
        System.out.println("myPieceMove"+myPiece.getPossibleMoves());
        System.out.println("computerMove:"+piece.getPossibleMoves());
        for (String move:piece.getPossibleMoves()) {
            if(myPiece.getPossibleMoves().contains(move)){
                System.out.println("move:"+move);
                score++;
            }
        }
        System.out.println(tile.getName()+" Score:"+score);
        return score;
    }

    private void dropPiece(Tile tile) {
        boolean questionTile = false;
        if (!myPiece.getPossibleMoves().contains(tile.getName())) return;
        if(tile.getChildren().size()>0){
            System.out.println("secondStage():"+tile.getChildren());
//            secondStage();
            tile.getChildren().clear();
            questionTile = true;
        }
        Tile initialSquare = (Tile) myPiece.getParent();
        tile.getChildren().add(0,myPiece);
        tile.setOccupied(true);
        if(tile.isVisited()){
            myScore = Math.max(myScore - 1, 0);
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
        if(questionTile){
            secondStage();
        }
        deselectPiece(true);







        if(specialTiles.contains(tile.getName())){
//            alertDisplayer();
            AlertDisplayer alertDisplayer1 = new AlertDisplayer();
//            alertDisplayer1.showOneSecondAlert("","");
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
        System.out.println("dropComputer");
        Tile initialSquare = (Tile) computerPiece.getParent();
        tile.getChildren().add(0,computerPiece);
        tile.setOccupied(true);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupied(false);
        computerPiece.setPosX(tile.getX());
        computerPiece.setPosY(tile.getY());
    }

    private void killMyPiece(Tile tile) {
        if (!computerPiece.getPossibleMoves().contains(tile.getName())) return;
        System.out.println("killMyPiece:"+tile.getChildren());
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
        if(!this.game){
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
        if(!game){
            Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
            gameOverAlert.setTitle("Game Over");
            gameOverAlert.setHeaderText("Sorry, you lost the game!");
            gameOverAlert.setContentText("Better luck next time!");
//            gameOverAlert.showAndWait();
//            gameOverAlert.setOnHidden(evt -> Platform);
            gameOverAlert.showAndWait();
            homePage();
        }else{
            Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
            gameOverAlert.setTitle("Game Over");
            gameOverAlert.setHeaderText("you Won the game!");
            gameOverAlert.setContentText("Your Score Is"+myScore);
            gameOverAlert.showAndWait();
            homePage();
        }
        timer.stop();
    }
    private void homePage(){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomePage.fxml")));
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