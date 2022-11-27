package model;

public class Queen extends Piece{

    public Queen(Tile currentTile) {
        super(currentTile);
    }



    @Override
    void movePiece(Tile tile) {
        setCurrentTile(tile);
    }
}
