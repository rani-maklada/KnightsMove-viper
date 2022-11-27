package model;

public class Knight extends Piece{

    public Knight(Tile currentTile) {
        super(currentTile);
    }
    @Override
    void movePiece(Tile tile) {
        setCurrentTile(tile);
    }
}

