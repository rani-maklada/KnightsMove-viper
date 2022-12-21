package model;

import controller.QuestionsPageController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SysData {
    private static SysData sys = null;
    private ArrayList<Question> questions;
    private ArrayList<GameHistory> history;
    private String resourceName;

    public static SysData getInstance()
    {
        if(sys == null)
            sys = new SysData();
        return sys;
    }
    public SysData() {
        resourceName = "questions.json";
        questions = new ArrayList<>();
        history = new ArrayList<>();
        ImportQuestions();
        ImportHistory();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<GameHistory> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<GameHistory> history) {
        this.history = history;
    }

    public void ImportHistory(){

    }
    public void ImportQuestions(){
        JSONObject base = readJSONObject();
        if(base == null){
            return;
        }
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

    }
    private JSONObject readJSONObject(){
        InputStream stream;
        try {
            stream = new FileInputStream(resourceName);
        } catch (FileNotFoundException e) {
            //throw new RuntimeException(e);
            return null;
        }
        JSONObject base = new JSONObject(new JSONTokener(stream));
        return base;
    }

    public void removeQuestion(String question){
        JSONObject base = readJSONObject();
        JSONArray array = ((JSONArray) base.get("questions"));
        int index = questions.indexOf(new Question(question));
        array.remove(index);
        questions.remove(index);
        System.out.println(array);
        saveJSONObject(base);
    }
    private void saveJSONObject(JSONObject object){
        try (FileWriter file = new FileWriter(resourceName)){
            file.write(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return "SysData{" +
                "questions=" + questions +
                ", history=" + history +
                '}';
    }
}
