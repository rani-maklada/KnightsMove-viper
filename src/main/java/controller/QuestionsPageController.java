package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.InputStream;

import model.Question;
import org.json.*;
public class QuestionsPageController {
	@FXML private Parent root;
	@FXML private Scene scene;
	@FXML private Stage stage;
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
			for(int i=0 ; i < jsonObject.length()-1; i++){
				answers.put(i+1, jsonArray.get(i).toString());
			}
			questions.add(new Question(
					String.valueOf(jsonObject.get("question")),
					answers,
					Integer.parseInt(String.valueOf(jsonObject.get("correct_ans"))),
					Integer.parseInt(String.valueOf(jsonObject.get("level"))),
					String.valueOf(jsonObject.get("team"))));
		}
		System.out.println(questions);
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