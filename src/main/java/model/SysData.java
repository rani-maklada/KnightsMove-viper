package model;

import controller.QuestionsPageController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SysData {
    private static SysData sys = null;
    private ArrayList<Question> questions;
    private String resourceName;

    public static SysData getInstance() {
        if (sys == null)
            sys = new SysData();
        return sys;
    }

    public SysData() {
        System.out.println("SysData");
        resourceName = "questions.json";
        questions = new ArrayList<>();
        ImportQuestions();
    }

    public ArrayList<Question> getQuestions() {

        return questions;
    }
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<GameHistory> getHistory() {
        return ImportHistory();
    }
    public void addQuestion(Question question) {
        String jsonString = String.valueOf(readJSONObject("questions.json"));
        // Parse the JSON string into a JSON object
        JSONObject json = new JSONObject(jsonString);

        // Get the "questions" array from the JSON object
        JSONArray jsonQuestions = json.getJSONArray("questions");
        JSONArray jsonAnswers = new JSONArray();
        HashMap<Integer, String> answers = question.getAnswers();
        for (String answer : answers.values()) {
            jsonAnswers.put(answer);
        }
        // Create a new JSON object for the new score
        JSONObject newQuestion = new JSONObject();
        newQuestion.put("question", question.getQuestionID());
        newQuestion.put("level", String.valueOf(question.getLevel()));
        newQuestion.put("answers", jsonAnswers);
        newQuestion.put("team", question.getTeam());
        newQuestion.put("correct_ans", String.valueOf(question.getCorrect_ans()));
        jsonQuestions.put(newQuestion);
        questions.add(question);
        // Write the updated JSON object back to the file
        saveJSONObject(json, "questions.json");
    }

    public void addHighScore(String mynickname, int myscore) throws IOException {
        String jsonString = String.valueOf(readJSONObject("history.json"));
        // Parse the JSON string into a JSON object
        JSONObject json = new JSONObject(jsonString);

        // Get the "history" array from the JSON object
        JSONArray history = json.getJSONArray("history");

        // Create a new JSON object for the new score
        JSONObject newScore = new JSONObject();
        newScore.put("player", mynickname);
        newScore.put("score", myscore);
        newScore.put("date", LocalDate.now());

        // Add the new score to the history array
        history.put(newScore);

        // Write the updated JSON object back to the file
        saveJSONObject(json, "history.json");
    }

    public ArrayList<GameHistory> ImportHistory(){
        ArrayList<GameHistory> history = new ArrayList<>();
        JSONObject base = readJSONObject("history.json");
        if(base == null){
            return null;
        }
        Iterator<Object> iterator = ((JSONArray) base.get("history")).iterator();
        while (iterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            history.add(new GameHistory(
                    String.valueOf(jsonObject.get("player")),
                    Integer.parseInt(String.valueOf(jsonObject.get("score"))),
                    parseDate((String.valueOf(jsonObject.get("date"))))
            ));
        }
        return history;
    }
    private LocalDate parseDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }
    public void ImportQuestions(){
        JSONObject base = readJSONObject("questions.json");
        if(base == null){
            return;
        }
        Iterator<Object> iterator = ((JSONArray) base.get("questions")).iterator();
        while (iterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            JSONArray jsonArray = jsonObject.getJSONArray("answers");
            HashMap<Integer, String> answers = new HashMap<Integer, String>();
            for (int i = 0; i < jsonObject.length() - 1; i++) {
                System.out.println(jsonArray.get(i).toString());
                answers.put(i + 1, jsonArray.get(i).toString());
            }
            questions.add(new Question(
                    String.valueOf(jsonObject.get("question")),
                    answers,
                    Integer.parseInt(String.valueOf(jsonObject.get("correct_ans"))),
                    Integer.parseInt(String.valueOf(jsonObject.get("level"))),
                    String.valueOf(jsonObject.get("team"))));
        }
    }
    private JSONObject readJSONObject(String jsonFile){
        InputStream stream;
        try {
            stream = new FileInputStream(jsonFile);
        } catch (FileNotFoundException e) {
            //throw new RuntimeException(e);
            return null;
        }
        System.out.println(stream);
        JSONObject base = new JSONObject(new JSONTokener(stream));
        return base;
    }

    public void removeQuestion(String question){
        JSONObject base = readJSONObject("questions.json");
        JSONArray array = ((JSONArray) base.get("questions"));
        int index = questions.indexOf(new Question(question));
        array.remove(index);
        questions.remove(index);
        System.out.println(array);
        saveJSONObject(base,"questions.json");
    }
    private void saveJSONObject(JSONObject object, String resource){
        try (FileWriter file = new FileWriter(resource)){
            file.write(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return "SysData{" +
                "questions=" + questions +
                ", history=" + getHistory() +
                '}';
    }
}