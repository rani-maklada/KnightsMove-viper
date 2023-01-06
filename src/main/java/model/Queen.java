package model;
import java.util.ArrayList;
/**
 * This class represents the Queen chess piece. It extends the {@link Piece}
 * class and overrides the {@link Piece#getAllPossibleMoves()}
 * method to return a list of all the possible moves that the Queen can make.
 */
public class Queen extends Piece{
    /**
     * Constructor for the Queen class.
     * @param color the color of the Queen chess piece.
     */
    public Queen(String color) {
        super(color, 7,0, "Queen");
    }
    /**
     * This method generates a list of all the possible moves that the Queen chess piece can make.
     * It takes into account the position of the Queen on the board and the positions of other chess pieces.
     */
    @Override
    public void getAllPossibleMoves() {
        int x = this.getPosX();
        int y = this.getPosY();
        String name;
        this.setPossibleMoves(new ArrayList<>());
        for (int i = x - 1; i >= 0; i--) {
            name = "Tile" + i + y;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int i = x + 1; i < 8; i++) {
            name = "Tile" + i + y;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int j = y - 1; j >= 0; j--) {
            name = "Tile" + x + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int j = y + 1; j < 8; j++) {
            name = "Tile" + x + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int i = x - 1, j = y + 1; i >= 0 && j < 8; i--, j++) {
            name = "Tile" + i + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int i = x + 1, j = y + 1; i < 8 && j < 8; i++, j++) {
            name = "Tile" + i + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int i = x + 1, j = y - 1; i < 8 && j >= 0; i++, j--) {
            name = "Tile" + i + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            name = "Tile" + i + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }
    }
}
