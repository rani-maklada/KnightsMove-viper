package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class GameHistoryTest {

    @Test
    void testPlayerHistory(){
        //initializing game history with a player and asserting if fields are correct
        LocalDate date = LocalDate.now();
        String actualDate = date.toString();
        GameHistory gameHistory = new GameHistory("Neil",12,date);
        Assertions.assertEquals(12,gameHistory.getScore());
        Assertions.assertEquals(gameHistory.getPlayerName(),"Neil");
        Assertions.assertEquals(date.toString(),actualDate);
    }
}