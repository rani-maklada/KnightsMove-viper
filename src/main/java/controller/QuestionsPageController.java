package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Question;
import model.SysData;

/**
 * Questions Page Controller to view/add/delete/edit questions
 */
public class QuestionsPageController {
	// FXML elements
	@FXML private Parent root;
	@FXML private Scene scene;
	@FXML private Stage stage;
	@FXML  private ListView<String> listViewQuestions;
	@FXML private ListView<String> listViewAnswers;
	@FXML private AnchorPane QuestionsPage;
	@FXML private TextField tfCorrectAnswer;
	@FXML private TextField tfLevel;
	@FXML private TextField tfTeam;

	/**
	 * Initialize the list view for questions and answers
	 * and set up the double click event for editing a question.
	 * @throws IOException
	 */
	@FXML
	void initialize() throws IOException {
		resetViewList();
		listViewQuestions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			getAnswers();
		});

		// Set the cell factory for the questions list view to wrap the cell text
		// and bind it to the item property.
		listViewQuestions.setCellFactory(lv -> {
			ListCell<String> cell = new ListCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			cell.setWrapText(true);
			text.wrappingWidthProperty().bind(listViewQuestions.widthProperty());
			cell.itemProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue != null) {
					Question question = new Question(newValue);
					int index = SysData.getInstance().getQuestions().indexOf(question);
					int level = SysData.getInstance().getQuestions().get(index).getLevel();
					cell.setStyle(colorByLevel(level));
				}
			});
			cell.setOnMouseEntered(event -> {
				cell.setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058)");
			});
			cell.setOnMouseExited(event -> {
				if(cell.getItem() != null){
					String mouseExited = cell.getItem();
					Question question = new Question(mouseExited);
					int index = SysData.getInstance().getQuestions().indexOf(question);
					int level = SysData.getInstance().getQuestions().get(index).getLevel();
					cell.setStyle(colorByLevel(level));
					cell.setStyle(colorByLevel(level));

				}
			});
			text.textProperty().bind(cell.itemProperty());
			return cell;

		});
		listViewAnswers.setCellFactory(lv -> {
			ListCell<String> cell = new ListCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			cell.setWrapText(true);
			text.wrappingWidthProperty().bind(listViewAnswers.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});


		// Set up the double-click event for the questions list view
		// to edit the selected question.
		listViewQuestions.setOnMouseClicked(mouseEvent -> {
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
				if(mouseEvent.getClickCount() == 2){
					String selectedItem = (String) listViewQuestions.getSelectionModel().getSelectedItem();
					try {
						RunToEdit(selectedItem);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					System.out.println("Double clicked");
				}
			}
		});
	}

	public String colorByLevel(int level){
		if (level == 1) {
			return "-fx-background-color: white;";
		} else if (level == 2) {
			return "-fx-background-color: yellow;";
		} else if (level == 3) {
			return "-fx-background-color: red;";
		} else {
			return "";
		}
	}

	/**
	 * Load the edit question page and pass the selected question to its controller.
	 * @param question selected question by the user
	 * @throws IOException
	 */
	@FXML
	public void RunToEdit(String question) throws IOException {
		// Return if no question is selected.
		if(question == null){return;}
		// Get the index of the selected question in the list of questions.
		int index = SysData.getInstance().getQuestions().indexOf(new Question(question));
		// Get the selected question from the list of questions.
		Question q = SysData.getInstance().getQuestions().get(index);
		// Load the edit question page FXML file and set its controller to a new
		// instance of EditQuestion with the selected question passed as a parameter.
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditQuestion.fxml"));
			loader.setControllerFactory((Class<?> type) -> {
				if (type == EditQuestion.class) {
					return new EditQuestion(q);
				} else {
					try {
						return type.newInstance();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
		    Parent root = loader.load();
			stage = (Stage) QuestionsPage.getScene().getWindow();
			scene = QuestionsPage.getScene();
			scene.setRoot(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Reset the list view of questions in the QuestionsPage.
	 */
	void resetViewList(){
		// Clear the current list of items in the list view.
		listViewQuestions.getItems().clear();
		// Add all the question IDs from the list of questions in the SysData class.
		for (Question q : SysData.getInstance().getQuestions()) {
			listViewQuestions.getItems().add(q.getQuestionID());
		}
		// Refresh the list view to display the updated list of question IDs.
		listViewQuestions.refresh();
	}
	/**
	 * Delete the selected question from the list of questions in the SysData class.
	 * @param event DeleteButton event
	 */
	@FXML
	void DeleteButton(ActionEvent event) throws IOException {
		// Get the ID of the selected question.
		String selectedItem = listViewQuestions.getSelectionModel().getSelectedItem();
		// Remove the selected question from the list of questions in the SysData class.
		SysData.getInstance().removeQuestion(selectedItem);
		// Reload the QuestionsPage to display the updated list of questions.
		root = FXMLLoader.load(getClass().getResource("/view/QuestionsPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = (Scene)((Node)event.getSource()).getScene();
		scene.setRoot(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * Open the addQuestion view to allow the user to add a new question.
	 * @param event addButton event
	 * @throws IOException
	 */
	@FXML
	void addButton(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/view/addQuestion.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = (Scene)((Node)event.getSource()).getScene();
		scene.setRoot(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * Display the answers for the selected question in the listViewAnswers
	 * and fill the text fields with the question and answer data.
	 */
	void getAnswers(){
		// Clear the listViewAnswers.
		listViewAnswers.getItems().clear();
		// Get the index of the selected question in the list of questions.
		int index = SysData.getInstance().getQuestions().indexOf(new Question(listViewQuestions.getSelectionModel().getSelectedItem()));
		// Get the selected question object.
		Question q = SysData.getInstance().getQuestions().get(index);
		// Add the answers for the selected question to the listViewAnswers.
		for (String answer : q.getAnswers().values()) {
			Text text = new Text();
			// Set the text alignment of the label to center.
			text.setStyle("-fx-text-alignment: center;");
			text.setText(answer);
			listViewAnswers.getItems().add(text.getText());
		}
		// Refresh the listViewAnswers.
		listViewAnswers.refresh();
		// Fill the text fields with the question and answer data.
		fillTextFields(q);
	}

	/**
	 * Fill the text fields with the question and answer data.
	 * @param q question data
	 */
	void fillTextFields(Question q){
		// Set the text of the tfCorrectAnswer text field to the correct answer
		// for the selected question.
		tfCorrectAnswer.setText(String.valueOf(q.getAnswers().get(q.getCorrect_ans())));
		// Set the text of the tfLevel text field to the level of the selected question.
		tfLevel.setText(String.valueOf(q.getLevel()));
		// Set the text of the tfTeam text field to the team of the selected question.
		tfTeam.setText(q.getTeam());
	}
	/**
	 * Handle the "Back" button press and send you to the HomePage
	 * @param event BackButton event
	 */
    @FXML
    void BackButton(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = (Scene)((Node)event.getSource()).getScene();
		scene.setRoot(root);
		stage.setScene(scene);
		stage.show();
    }


}