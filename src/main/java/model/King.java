package model;

import Enums.TileType;
import controller.GamePageController;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the King chess piece. It extends the {@link Piece}
 * class and overrides the {@link Piece#getAllPossibleMoves()}
 * method to return a list of all the possible moves that the King can make.
 */
public class King extends Piece{
    /**
     * Constructor for the King class.
     * @param color the color of the King chess piece.
     */
    public King(String color) {
        super(color, 7, 0, "King");
    }
    /**
     * This method generates a list of all the possible moves that the King chess piece can make.
     * It takes into account the position of the King on the board and the positions of other chess pieces.
     */
    @Override
    public void getAllPossibleMoves() {
        int x = this.getPosX();
        int y = this.getPosY();
        ArrayList<String> moves = new ArrayList<>();
        this.setPossibleMoves(new ArrayList<>());
        moves.add("Tile" + (x) + (y-1));
        moves.add("Tile" + (x+1) + (y-1));
        moves.add("Tile" + (x+1) + (y));
        moves.add("Tile" + (x+1) + (y+1));
        moves.add("Tile" + (x) + (y+1));
        moves.add("Tile" + (x-1) + (y+1));
        moves.add("Tile" + (x-1) + (y));
        moves.add("Tile" + (x-1) + (y-1));
        for(String move : moves){
            if(getTileByName(move) != null){
                if(getTileByName(move).getType().equals(TileType.BlockedTile))continue;
                getPossibleMoves().add(move);

            }
        }
    }
}
