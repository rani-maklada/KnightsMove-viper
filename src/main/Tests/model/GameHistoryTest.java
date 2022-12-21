package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class GameHistoryTest {

    @Test
    void testPlayerHistory(){
        Date date = new Date();
        String actualDate = date.toString();
        GameHistory gameHistory = new GameHistory("Neil",12,date);
        Assertions.assertEquals(12,gameHistory.getScore());
        Assertions.assertEquals(gameHistory.getPlayerName(),"Neil");
        Assertions.assertEquals(date.toString(),actualDate);
    }
}