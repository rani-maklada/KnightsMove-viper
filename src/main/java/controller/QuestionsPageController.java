package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.InputStream;

import model.Question;
import model.SysData;
import org.controlsfx.control.PropertySheet;
import org.json.*;
public class QuestionsPageController {
	@FXML private Parent root;
	@FXML private Scene scene;
	@FXML private Stage stage;
	@FXML  private ListView<Label> listViewQuestions;
	@FXML private ListView<Label> listViewAnswers;

	@FXML private TextField tfCorrectAnswer;

	@FXML private TextField tfLevel;

	@FXML private TextField tfTeam;
	@FXML
	void initialize() throws IOException {
		resetViewList();
		listViewQuestions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			getAnswers();
		});
	}
	void resetViewList(){
		listViewQuestions.getItems().clear();
		for (Question q : SysData.getInstance().getQuestions()) {
			Button myButton = new Button();
			myButton.setText("Click me");
			Label lbItem = new Label();
			lbItem.setStyle("-fx-text-alignment: center;");
			lbItem.setText(q.getQuestionID().toString());

			if (q.getLevel() == 1) {
				lbItem.setTextFill(Color.RED);
			}
			if (q.getLevel() == 2) {
				lbItem.setTextFill(Color.GREEN);
			}
			if (q.getLevel() == 3) {
				lbItem.setTextFill(Color.WHITE);
			}

			listViewQuestions.getItems().add(lbItem);
		}
		listViewQuestions.refresh();
	}
	@FXML
	void DeleteButton(ActionEvent event) {
		System.out.println(listViewQuestions.getSelectionModel().getSelectedItem());
		SysData.getInstance().removeQuestion(listViewQuestions.getSelectionModel().getSelectedItem().getText());
		resetViewList();

	}

	void getAnswers(){
		listViewAnswers.getItems().clear();

		int index = SysData.getInstance().getQuestions().indexOf(new Question(listViewQuestions.getSelectionModel().getSelectedItem().getText()));
		Question q = SysData.getInstance().getQuestions().get(index);
		for (String answer : q.getAnswers().values()) {
			Label lbItem = new Label();
			lbItem.setStyle("-fx-text-alignment: center;");
			lbItem.setText(answer);
			listViewAnswers.getItems().add(lbItem);
		}
		listViewAnswers.refresh();
		fillTextFields(q);
	}
	void fillTextFields(Question q){
		tfCorrectAnswer.setText(String.valueOf(q.getAnswers().get(q.getCorrect_ans())));
		tfLevel.setText(String.valueOf(q.getLevel()));
		tfTeam.setText(q.getTeam());
	}

    @FXML
    void BackButton(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = (Scene)((Node)event.getSource()).getScene();
		scene.setRoot(root);
		stage.setScene(scene);
		stage.show();
    }


}