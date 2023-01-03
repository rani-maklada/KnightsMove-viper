package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class TileTest {

    @Test
    void tileTestPositionAndFunctions() throws IOException {
        //initializing tile and checking field including assert false that tile is not occupied
        //because it is not supposed to be
        Tile tile = new Tile(7,3);
        int x = tile.getX();
        int y = tile.getY();
        Assertions.assertEquals(7,x);
        Assertions.assertEquals(3,y);
        Assertions.assertTrue(!tile.isOccupied());
        tile.setName("Tile2");
        Assertions.assertTrue(tile.getName().equals("Tile2"));
           }
}
