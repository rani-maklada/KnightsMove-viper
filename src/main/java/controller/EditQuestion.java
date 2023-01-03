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
import java.util.ArrayList;
import java.util.HashMap;
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

    String originalQuestion;

    @FXML



    void initialize()
    {
        originalQuestion = question.getQuestionID();
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
    void addButton(ActionEvent event) throws IOException {

        if(!checkFields())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Let's Go");
            alert.setHeaderText("Fill in the empty fields");
            alert.showAndWait();
            return;
        }

       ArrayList<Question> myQuestions =  SysData.getInstance().getQuestions();
        HashMap<Integer, String> myAnswers = new HashMap<Integer, String>();
        myAnswers.put(1, answer1TextField.getText());
        myAnswers.put(2, answer2TextField.getText());
        myAnswers.put(3, answer3TextField.getText());
        myAnswers.put(4, answer4TextField.getText());
        int clicked= question.getCorrect_ans();
        if(answer1.isSelected())
            clicked=1;
        if(answer2.isSelected())
            clicked=2;
        if(answer3.isSelected())
            clicked=3;
        if(answer4.isSelected())
            clicked=4;

       Question questionToCheck = new Question(questionField.getText(),myAnswers,clicked,Integer.parseInt(levelChoiceBox.getValue()),teamTextFie.getText());
//if(questionToCheck.equals(question))
//{
//    question.setAnswers(myAnswers);
//    question.setLevel(questionToCheck.getLevel());
//    question.setTeam(questionToCheck.getTeam());
//    question.setCorrect_ans(questionToCheck.getCorrect_ans());
//}
//else {
//    myQuestions.remove(question);
//    myQuestions.add(questionToCheck);
//}


        SysData.getInstance().removeQuestion(question.getQuestionID());
        SysData.getInstance().addQuestion(questionToCheck);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/QuestionsPage.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = (Scene) ((Node) event.getSource()).getScene();
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
