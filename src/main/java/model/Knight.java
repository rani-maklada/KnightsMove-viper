package model;

import controller.GamePageController;

import java.util.ArrayList;
import java.util.List;

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
        List<int[]> m = getKnightMoves(x, y);
        for (int[] move : m) {
            moves.add("Tile"+move[0]+move[1]);
        }
        System.out.println("Knight Moves:"+moves);
        for(String move : moves){
            if(getTileByName(move) != null){
                if(getTileByName(move).isOccupied() && getPieceByName(move).getColor().equals(GamePageController.currentPlayer)) continue;
                getPossibleMoves().add(move);
            }
        }
    }
    private List<int[]> getKnightMoves(int x, int y) {
        List<int[]> moves = new ArrayList<>();
        int[] dx, dy;
        if(GamePageController.myStage == 1){
            dx = new int[] {-1, 1, 2, -2, -2, 2, -1, 1};
            dy = new int[] {-2, -2, -1, -1, 1, 1, 2, 2};
        }else{
            dx = new int[] {-2, -2, -1, 1, 2, 2, 1, -1};
            dy = new int[] {-1, 1, 2, 2, 1, -1, -2, -2};
        }
        for (int i = 0; i < 8; i++) {
            int newX = (x + dx[i] % 8 + 8) % 8;
            int newY = (y + dy[i] % 8 + 8) % 8;
            moves.add(new int[]{newX, newY});
        }
        System.out.println("moves:"+moves);
        return moves;
    }
    private int getPosition(int p, int n){
        return (p+n<0) ?8+p+n:p+n;
    }
}

