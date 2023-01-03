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

public class goToGameOver {
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML
    private ImageView goToOver;
    String nickName;
    @FXML
    private Label nickHolder;

    @FXML
    private Label scoreHolder;
    int myScore;
    boolean Result;
    @FXML
    private ImageView myimageScore;
    public goToGameOver(String name, int score, boolean isWon)
    {
        this.nickName=name;
        this.myScore=score;
        this.Result=isWon;
    }
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
        ImageView imageView = new ImageView(image);
        myimageScore.setImage(image);
        System.out.println(nickName);

        if(Result)
            image = new Image(String.valueOf(getClass().getResource("/view/images/gameOverGif.gif")));
        else {
            image = new Image(String.valueOf(getClass().getResource("/view/images/Winner.gif")));
        }

        imageView = new ImageView(image);
        goToOver.setImage(image);
        nickHolder.setText(nickName);
        scoreHolder.setText(String.valueOf(myScore));
    }

}
