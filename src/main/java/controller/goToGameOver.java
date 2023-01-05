package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * goToGameOver Page Controller to view the score after losing the Game
 */
public class goToGameOver {
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML private ImageView goToOver;
    @FXML private Label nickHolder;
    @FXML private Label scoreHolder;
    @FXML private ImageView myImageScore;
    private String nickName;
    private int myScore;
    private boolean Result;

    /**
     * @param name nickName of the player
     * @param score the score of the game
     * @param isWon if the player finish the game
     */
    public goToGameOver(String name, int score, boolean isWon)
    {
        this.nickName=name;
        this.myScore=score;
        this.Result=isWon;
    }

    /**
     * Handle the "Back" button press and send you to the HomePage
     * @param event backToHome button event
     * @throws IOException
     */
    @FXML
    void backToHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void playAgain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/GameRegister.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void initialize(){
        Image image;
        image = new Image(String.valueOf(getClass().getResource("/view/images/YourScore.png")));
        myImageScore.setImage(image);
        System.out.println(nickName);

        if(Result)
            image = new Image(String.valueOf(getClass().getResource("/view/images/gameOverGif.gif")));
        else {
            image = new Image(String.valueOf(getClass().getResource("/view/images/Winner.gif")));
        }
        goToOver.setImage(image);
        nickHolder.setText(nickName);
        scoreHolder.setText(String.valueOf(myScore));
    }

}
