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

/**
 * Page to add new question to the database
 */
public class AddQuestion {
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
    @FXML private TextField teamTextFie;
    @FXML private ChoiceBox<String> levelChoiceBox;
    @FXML private TextField answer4TextField;
    /**
     * This method is called when the FXML file is loaded.
     * It initializes the radio buttons for the multiple choice
     * answers and adds options for the level choice box.
     */
    @FXML
    void initialize() throws IOException {
        // Create a toggle group for the radio buttons
        ToggleGroup toggleGroup = new ToggleGroup();
        answer1.setToggleGroup(toggleGroup);
        answer3.setToggleGroup(toggleGroup);
        answer2.setToggleGroup(toggleGroup);
        answer4.setToggleGroup(toggleGroup);
        // Add options to the level choice box
        levelChoiceBox.getItems().add("1");
        levelChoiceBox.getItems().add("2");
        levelChoiceBox.getItems().add("3");
        levelChoiceBox.getItems().add("4");
    }

    /**
     * This method is called when the add button is clicked.
     * It checks if all the necessary fields are filled in,
     * creates a new Question object with the given information,
     * and adds it to the list of questions in the system data.
     * Finally, it navigates back to the QuestionsPage scene.
     * @param event addButton event
     * @throws IOException
     */
    @FXML
    void addButton(ActionEvent event) throws IOException {
        // Check if all the fields are filled in
        if(!checkFields()){
            // If any of the fields are empty, show an alert and return
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Let's Go");
            alert.setHeaderText("Fill in the empty fields");
            alert.showAndWait();
            return;
        }
        // Create a map of the answers, with the keys being the answer number
        // and the values being the answer text
        HashMap<Integer, String> answers = new HashMap<>();
        answers.put(1,answer1TextField.getText());
        answers.put(2,answer2TextField.getText());
        answers.put(3,answer3TextField.getText());
        answers.put(4,answer4TextField.getText());
        // Determine which answer is the correct one
        int ans = 0;
        if(answer1.isSelected())
            ans = 1;
        else if (answer2.isSelected())
            ans = 2;
        else if (answer3.isSelected())
            ans = 3;
        else if (answer4.isSelected())
            ans = 4;
        // Create a new Question object with the given information
        Question question = new Question(
                questionField.getText(),
                answers,
                ans,
                Integer.parseInt(levelChoiceBox.getValue()),
                teamTextFie.getText()
                );
        SysData.getInstance().addQuestion(question);
        // Navigate back to the QuestionsPage scene
        root = FXMLLoader.load(getClass().getResource("/view/QuestionsPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method checks if all the necessary fields are filled in
     * @return true or false
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