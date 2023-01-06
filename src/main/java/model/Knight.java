package model;

import Enums.TileType;
import controller.GamePageController;

import java.util.ArrayList;
import java.util.List;
/**
 * This class represents the Knight chess piece. It extends the {@link Piece}
 * class and overrides the {@link Piece#getAllPossibleMoves()}
 * method to return a list of all the possible moves that the Knight can make.
 */
public class Knight extends Piece{
    /**
     * Constructor for the Knight class.
     * @param color the color of the Knight chess piece.
     */
    public Knight(String color) {
        super(color, 0, 0, "Knight");
    }
    /**
     * This method generates a list of all the possible moves that the Knight chess piece can make.
     * It takes into account the position of the Knight on the board and the positions of other chess pieces.
     */
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
        for(String move : moves){
            if(getTileByName(move) != null){
                if(getTileByName(move).isOccupied() || getTileByName(move).getType() == TileType.BlockedTile) continue;
                getPossibleMoves().add(move);
            }
        }
    }
    private List<int[]> getKnightMoves(int x, int y) {
        List<int[]> moves = new ArrayList<>();
        int[] dx, dy, dx2, dy2,dx3,dy3;
        if(GamePageController.myStage == 1){
            dx = new int[] {-1, 1, 2, -2, -2, 2, -1, 1};
            dy = new int[] {-2, -2, -1, -1, 1, 1, 2, 2};

        }else{
            System.out.println("Here");
            dx3 = new int[] {-3, -3, -1, 1, 3, 3, 1, -1};
            dy3= new int[] {-1, 1, 3, 3, 1, -1, -3, -3};
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
        return moves;
    }
}

