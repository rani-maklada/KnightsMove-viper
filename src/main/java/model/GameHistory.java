package model;

import java.util.Date;

public class GameHistory {
    private String playerName;
    private int score;
    private Date date;

    public GameHistory(String playerName, int score, Date date) {
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "GameHistory{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                ", date=" + date +
                '}';
    }
}
