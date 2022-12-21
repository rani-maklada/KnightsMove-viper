package model;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
    @BeforeAll
    public static void setupJavaFX() {
        // Initialize JavaFX by creating a new JFXPanel
        new JFXPanel();
    }
    @Test
    void initKnight(){
        Knight knight = new Knight("black");
    }
}