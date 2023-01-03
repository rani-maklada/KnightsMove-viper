package model;

import Enums.TileType;
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
                if(getTileByName(move).isOccupied() || getTileByName(move).getType() == TileType.BlockedTile) continue;
                getPossibleMoves().add(move);
            }
        }
    }
    private List<int[]> getKnightMoves(int x, int y) {
        List<int[]> moves = new ArrayList<>();
        int[] dx, dy, dx2, dy2;
        if(GamePageController.myStage == 1){
            dx = new int[] {-1, 1, 2, -2, -2, 2, -1, 1};
            dy = new int[] {-2, -2, -1, -1, 1, 1, 2, 2};

        }else{
            System.out.println("Here");
            int[] dx3 = new int[] {-3, -3, -1, 1, 3, 3, 1, -1};
            int []dy3= new int[] {-1, 1, 3, 3, 1, -1, -3, -3};
             dx = new int[] {-3, -3, -2, 2, 3, 3, 2, -2};
             dy = new int[] {-2, 2, 3, 3, 2, -2, -3, -3};
             dx2 = new int[] {-2, -2, -1, 1, 2, 2, 1, -1};
             dy2 = new int[] {-1, 1, 2, 2, 1, -1, -2, -2};

            for (int i = 0; i < 8; i++) {
                int newX = (x + dx2[i] % 8 + 8) % 8;
                int newY = (y + dy2[i] % 8 + 8) % 8;
                moves.add(new int[]{newX, newY});
            }
            for (int i = 0; i < 8; i++) {
                int newX = (x + dx3[i] % 8 + 8) % 8;
                int newY = (y + dy3[i] % 8 + 8) % 8;
                moves.add(new int[]{newX, newY});
            }
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

