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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.InputStream;

import model.Question;
import org.controlsfx.control.PropertySheet;
import org.json.*;
public class QuestionsPageController {
	@FXML private Parent root;
	@FXML private Scene scene;
	@FXML private Stage stage;
	@FXML  private ListView<Label> listView;
	@FXML
	void initialize() throws IOException {

		ArrayList<Question> questions = new ArrayList<>();
		String resourceName = "/files/questions.json";
		InputStream inputStream = QuestionsPageController.class.getResourceAsStream(resourceName);
		if (inputStream == null) {
			throw new NullPointerException("Cannot find resource file " + resourceName);
		}
		JSONObject base = new JSONObject(new JSONTokener(inputStream));
		Iterator<Object> iterator = ((JSONArray) base.get("questions")).iterator();
		while (iterator.hasNext()) {
			JSONObject jsonObject = (JSONObject) iterator.next();
			JSONArray jsonArray = jsonObject.getJSONArray("answers");
			HashMap<Integer, String> answers = new HashMap<Integer, String>();
			for (int i = 0; i < jsonObject.length() - 1; i++) {
				answers.put(i + 1, jsonArray.get(i).toString());
			}
			questions.add(new Question(
					String.valueOf(jsonObject.get("question")),
					answers,
					Integer.parseInt(String.valueOf(jsonObject.get("correct_ans"))),
					Integer.parseInt(String.valueOf(jsonObject.get("level"))),
					String.valueOf(jsonObject.get("team"))));
		}

		for (Question q : questions) {
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

			listView.getItems().add(lbItem);
		}



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