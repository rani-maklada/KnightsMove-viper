package model;

import controller.GamePageController;

import java.util.ArrayList;

public class King extends Piece{

    public King(String color) {
        super(color, 7, 0, "King");
    }

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
//                if(getTileByName(move).isOccupied() && getPieceByName(move).getColor().equals(GamePageController.currentPlayer)) continue;
                getPossibleMoves().add(move);

            }
        }
    }
}
