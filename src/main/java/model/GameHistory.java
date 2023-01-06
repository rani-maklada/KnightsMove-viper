package model;
import java.time.LocalDate;
/**
 * Represents a game history record with a player name, score, and date.
 */
public class GameHistory {
    // Fields for player name, score, and date
    private String playerName;
    private int score;
    private LocalDate date;
    /**
     * Constructs a new GameHistory object with the given player name, score, and date.
     * @param playerName the name of the player
     * @param score the score achieved in the game
     * @param date the date the game was played
     */
    public GameHistory(String playerName, int score, LocalDate date) {
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }
    // Getters and setters for the fields
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
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
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
