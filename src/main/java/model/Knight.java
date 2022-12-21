package model;

import controller.GamePageController;

import java.util.ArrayList;

public class Knight extends Piece{

    public Knight(String color) {
        super(color, 0, 0, "Knight");

    }
    @Override
    public void getAllPossibleMoves() {
        int x = this.getPosX();
        int y = this.getPosY();
        ArrayList<String> moves = new ArrayList<>();
        this.setPossibleMoves(new ArrayList<>());
//        moves.add("Tile" + (x+2)%8 + (y+1)%8);
//        moves.add("Tile" + (x+2)%8 + (y-1));
//        moves.add("Tile" + (x+1)%8 + (y+2)%8);
//        moves.add("Tile" + (x-1) + (y+2)%8);
//        moves.add("Tile" + (x-2) + (y+1)%8);
//        moves.add("Tile" + (x-2) + (y-1));
//        moves.add("Tile" + (x+1)%8 + (y-2));
//        moves.add("Tile" + (x-1) + (y-2));
//        System.out.println(moves);
        moves.add("Tile" + (x+2)%8 + (y+1)%8);
        moves.add("Tile" + (x+2)%8 + getPosition(y,-1));
        moves.add("Tile" + (x+1)%8 + (y+2)%8);
        moves.add("Tile" + getPosition(x,-1) + (y+2)%8);
        moves.add("Tile" + getPosition(x,-2) + (y+1)%8);
        moves.add("Tile" + getPosition(x,-2) + getPosition(y,-1));
        moves.add("Tile" + (x+1)%8 + getPosition(y,-2));
        moves.add("Tile" + getPosition(x,-1) + (getPosition(y,-2)));
        System.out.println(moves);
        for(String move : moves){

            if(getTileByName(move) != null){
                if(getTileByName(move).isOccupied() && getPieceByName(move).getColor().equals(GamePageController.currentPlayer)) continue;
                getPossibleMoves().add(move);
            }
        }
    }
    private int getPosition(int p, int n){
        return (p+n<0) ?8+p+n:p+n;
    }
}

