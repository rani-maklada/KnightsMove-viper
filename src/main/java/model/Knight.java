package model;

import controller.GamePageController;

import java.util.ArrayList;

public class Knight extends Piece{

    public Knight(String color, int positionX, int positionY) {
        super(color, positionX, positionY, "Knight");

    }
    @Override
    public void getAllPossibleMoves() {
        int x = this.getPosX();
        int y = this.getPosY();
        ArrayList<String> moves = new ArrayList<>();
        this.setPossibleMoves(new ArrayList<>());
        moves.add("Tile" + (x+2) + (y+1));
        moves.add("Tile" + (x+2) + (y-1));
        moves.add("Tile" + (x+1) + (y+2));
        moves.add("Tile" + (x-1) + (y+2));
        moves.add("Tile" + (x-2) + (y+1));
        moves.add("Tile" + (x-2) + (y-1));
        moves.add("Tile" + (x+1) + (y-2));
        moves.add("Tile" + (x-1) + (y-2));


        for(String move : moves){
            if(getTileByName(move) != null){
                if(getTileByName(move).isOccupied() && getPieceByName(move).getColor().equals(GamePageController.currentPlayer)) continue;
                getPossibleMoves().add(move);

            }
        }
    }
}

