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
import java.util.Objects;
/**
 * Page to edit existing question and update it in the database
 */
public class EditQuestion {
    // FXML elements
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;
    @FXML private TextField questionField;
    @FXML private RadioButton answer1;
    @FXML private TextField answer1TextField;
    @FXML private RadioButton answer2;
    @FXML private TextField answer2TextField;
    @FXML private RadioButton answer3;
    @FXML private TextField answer3TextField;
    @FXML private RadioButton answer4;
    @FXML private TextField answer4TextField;
    @FXML private TextField teamTextFie;
    @FXML private ChoiceBox<String> levelChoiceBox;
    private Question question;
    private String originalQuestion;

    /**
     * This is a constructor for the EditQuestion class,
     * which takes in a Question object as a parameter.
     * It initializes the question field of the EditQuestion class with the given question.
     * @param question get the original question that the user want to edit
     */
    public EditQuestion(Question question){
        this.question = question;
    }
    /**
     This method is called when the FXML file is loaded.
     It initializes the radio buttons for the multiple choice
     answers and adds options for the level choice box.
     */
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

    /**
     * This method is called when the add button is clicked.
     * It checks if all the necessary fields are filled in,
     * Edit the Question object with the given information,
     * and adds it to the list of questions in the system data.
     * Finally, it navigates back to the QuestionsPage scene.
     * @param event addButton event
     */
    @FXML
    void addButton(ActionEvent event) throws IOException {
        // Check if all the fields are filled in
        if(!checkFields())
        {
            // If any of the fields are empty, show an alert and return
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Let's Go");
            alert.setHeaderText("Fill in the empty fields");
            alert.showAndWait();
            return;
        }
        // Create a map of the answers, with the keys being the answer number
        // and the values being the answer text
        HashMap<Integer, String> myAnswers = new HashMap<Integer, String>();
        myAnswers.put(1, answer1TextField.getText());
        myAnswers.put(2, answer2TextField.getText());
        myAnswers.put(3, answer3TextField.getText());
        myAnswers.put(4, answer4TextField.getText());

        // Determine which answer is the correct one
        int clicked= question.getCorrect_ans();
        if(answer1.isSelected())
            clicked=1;
        if(answer2.isSelected())
            clicked=2;
        if(answer3.isSelected())
            clicked=3;
        if(answer4.isSelected())
            clicked=4;
        // Create a new Question object with the given information
        Question questionToCheck = new Question(
                questionField.getText(),
                myAnswers,
                clicked,
                Integer.parseInt(levelChoiceBox.getValue()),
                teamTextFie.getText());
        SysData.getInstance().removeQuestion(question.getQuestionID());
        SysData.getInstance().addQuestion(questionToCheck);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/QuestionsPage.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = (Scene) ((Node) event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method checks if all the necessary fields are filled in
     * @return return whether the user filled all the fields
     */
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
        return answer1.isSelected() || answer2.isSelected() || answer3.isSelected()
                || answer4.isSelected();
    }

    /**
     * Handle the "Back" button press.
     * Navigate back to the QuestionsPage scene
     * @param event backButton event
     */
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
