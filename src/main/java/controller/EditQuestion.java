package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Question;
import model.SysData;

import java.io.IOException;
import java.util.Objects;

public class EditQuestion {
 Question question;
    @FXML
    private TextField questionField;

    @FXML
    private RadioButton answer1;

    @FXML
    private TextField answer1TextField;

    @FXML
    private RadioButton answer2;

    @FXML
    private TextField answer2TextField;

    @FXML
    private RadioButton answer3;

    @FXML
    private TextField answer3TextField;
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML
    private RadioButton answer4;

    @FXML
    private TextField answer4TextField;

    @FXML
    private TextField teamTextFie;

    @FXML
    private ChoiceBox<String> levelChoiceBox;
    public EditQuestion(Question question){
        this.question = question;
    }
    @FXML
    void initialize()
    {
        questionField.setText(question.getQuestionID());
        ToggleGroup toggleGroup = new ToggleGroup();
        answer1.setToggleGroup(toggleGroup);
        answer3.setToggleGroup(toggleGroup);
        answer2.setToggleGroup(toggleGroup);
        answer4.setToggleGroup(toggleGroup);
        levelChoiceBox.getItems().add("1");
        levelChoiceBox.getItems().add("2");
        levelChoiceBox.getItems().add("3");
        levelChoiceBox.getItems().add("4");
        levelChoiceBox.setValue(String.valueOf(question.getLevel()));
        answer1TextField.setText(question.getAnswers().get(1));
        answer2TextField.setText(question.getAnswers().get(2));
        answer3TextField.setText(question.getAnswers().get(3));
        answer4TextField.setText(question.getAnswers().get(4));
        int myClicked = question.getCorrect_ans();
        if(myClicked==1)
        {
            answer1.setSelected(true);
        }
        if(myClicked==2)
        {
            answer2.setSelected(true);
        }
        if(myClicked==3)
        {
            answer3.setSelected(true);
        }
        if(myClicked==4)
        {
            answer4.setSelected(true);
        }
teamTextFie.setText(question.getTeam());
    }
    @FXML
    void addButton(ActionEvent event) {

    }
    @FXML
    void backButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/QuestionsPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

}
