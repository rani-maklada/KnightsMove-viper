package model;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
    //this method is used in order to initialize the knight which contains a connection
    // to java fx in it's code
    @BeforeAll
    public static void setupJavaFX() {
        // Initialize JavaFX by creating a new JFXPanel
        new JFXPanel();
    }
    @Test
    //test initializing knight and see if the init positioning is correct
    void initKnight(){
        Knight knight = new Knight("black");
        int posX = knight.getPosX();
        int posY = knight.getPosY();
        Assertions.assertEquals(posX,0);
        Assertions.assertEquals(posY,0);
    }
}