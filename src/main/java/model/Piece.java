package model;

public abstract class Piece {
    public Tile currentTile;

    public Piece(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }



    abstract void movePiece(Tile tile);
}
