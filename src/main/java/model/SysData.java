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
        resourceName = "/files/questions.json";
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
        InputStream inputStream = QuestionsPageController.class.getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new NullPointerException("Cannot find resource file " + resourceName);
        }
        //JSONObject base = new JSONObject(new JSONTokener(inputStream));
        //JSONObject base = new JSONObject("questions.json");
        //JSONObject base = new JSONObject();
        //StringBuilder sb = new StringBuilder();
       /* try (FileReader reader = new FileReader("questions.json")) {
            base = new JSONObject(reader);
            System.out.println(reader);
            System.out.println(base.getJSONArray("questions"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //try {
            //JSONObject json = new JSONObject(new FileReader("questions.json"));
           // System.out.println(json.getJSONArray("questions"));
        //} catch (FileNotFoundException e) {
            //throw new RuntimeException(e);
        //}

        //System.out.println(base.getJSONArray("questions"));
        /*
        System.out.println(sb.toString());
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
        System.out.println(questions);

         */
    }

    public void removeQuestion(String question){
        InputStream inputStream = QuestionsPageController.class.getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new NullPointerException("Cannot find resource file " + resourceName);
        }
        JSONObject base = new JSONObject(new JSONTokener(inputStream));
        JSONArray array = ((JSONArray) base.get("questions"));
        int index = questions.indexOf(new Question(question));
        array.remove(index);
        questions.remove(index);
        /*
        FileWriter file = null;
        try {
            file = new FileWriter("questions.json");
            System.out.println(QuestionsPageController.class.getResourceAsStream(resourceName));
            file.write(base.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(1);
        Iterator<Object> iterator = ((JSONArray) base.get("questions")).iterator();
        while (iterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            System.out.println(jsonObject.get("question"));
            if (jsonObject.get("question").equals(question)){
                System.out.println(iterator);
                iterator.remove();
            }
        }
        JSONArray array1 = (JSONArray) base.get("questions");
        System.out.println(array1);
         */
        System.out.println(array);
        //saveJSONObject(base,"questions.json");

    }
    private void saveJSONObject(JSONObject object, String fileName){
        try (FileWriter file = new FileWriter(fileName)){
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
