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
import java.util.HashMap;

public class AddQuestion {
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML
    private TextField questionField;

    @FXML
    private Button addButton;

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

    @FXML
    private RadioButton answer4;
    @FXML
    private TextField teamTextFie;

    @FXML
    private ChoiceBox<String> levelChoiceBox;
    @FXML
    private TextField answer4TextField;
    @FXML
    void initialize() throws IOException {
        ToggleGroup toggleGroup = new ToggleGroup();
        answer1.setToggleGroup(toggleGroup);
        answer3.setToggleGroup(toggleGroup);
        answer2.setToggleGroup(toggleGroup);
        answer4.setToggleGroup(toggleGroup);
        levelChoiceBox.getItems().add("1");
        levelChoiceBox.getItems().add("2");
        levelChoiceBox.getItems().add("3");
        levelChoiceBox.getItems().add("4");
    }
    @FXML
    void addButton(ActionEvent event) throws IOException {
        if(!checkFields()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Let's Go");
            alert.setHeaderText("Fill in the empty fields");
            alert.showAndWait();
            return;
        }
        HashMap<Integer, String> answers = new HashMap<>();
        answers.put(1,answer1TextField.getText());
        answers.put(2,answer2TextField.getText());
        answers.put(3,answer3TextField.getText());
        answers.put(4,answer4TextField.getText());
        int ans = 0;
        if(answer1.isSelected())
            ans = 1;
        else if (answer2.isSelected())
            ans = 2;
        else if (answer3.isSelected())
            ans = 3;
        else if (answer4.isSelected())
            ans = 4;
        Question question = new Question(
                questionField.getText(),
                answers,
                ans,
                Integer.valueOf(levelChoiceBox.getValue()),
                teamTextFie.getText()
                );
        SysData.getInstance().addQuestion(question);

        root = FXMLLoader.load(getClass().getResource("/view/QuestionsPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }
    private boolean checkFields(){
        if(questionField.getText().isEmpty()){
            return false;
        }
        if(answer1TextField.getText().isEmpty()){
            return false;
        }
        if(answer2TextField.getText().isEmpty()){
            return false;
        }
        if(answer3TextField.getText().isEmpty()){
            return false;
        }
        if(answer4TextField.getText().isEmpty()){
            return false;
        }
        if(!answer1.isSelected() && !answer2.isSelected() && !answer3.isSelected()
        && !answer4.isSelected() ){
            return false;
        }
        return true;
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