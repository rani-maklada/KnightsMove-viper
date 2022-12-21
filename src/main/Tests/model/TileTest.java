package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class TileTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    @Test
    void tileTestPosition() throws IOException {
        Tile tile = new Tile(7,3);
        int x = tile.getX();
        int y = tile.getY();
        Assertions.assertEquals(7,x);
        Assertions.assertEquals(3,y);
        Tile tile2 = new Tile(15,3);
//        // check that an error will be thrown when creating an invalid tile
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(System.in));
//            // Reading data using readLine
//            String error = reader.readLine();
//            System.out.println(error);
            // Printing the read line
        System.setOut(new PrintStream(outputStreamCaptor));
        Assertions.assertEquals("Can't create a tile outside of borders",
                outputStreamCaptor.toString()
                        .trim());
    }
}
