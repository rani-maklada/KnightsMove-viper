package model;

import java.util.HashMap;

public class Question {
    private String questionID;
    private HashMap<Integer, String> answers;
    private int correct_ans;
    private int level;

    private String team;

    public Question(String questionID, HashMap<Integer, String> answers, int correct_ans, int level, String team) {
        this.questionID = questionID;
        this.answers = answers;
        this.correct_ans = correct_ans;
        this.level = level;
        this.team = team;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public HashMap<Integer, String> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<Integer, String> answers) {
        this.answers = answers;
    }

    public int getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(int correct_ans) {
        this.correct_ans = correct_ans;
    }

    public int getLevel() {
        return level;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return questionID;
    }
}
