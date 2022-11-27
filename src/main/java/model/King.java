package model;

public class King extends Piece{

    public King(Tile currentTile) {
        super(currentTile);
    }



    @Override
    void movePiece(Tile tile) {
        setCurrentTile(tile);
    }
}
