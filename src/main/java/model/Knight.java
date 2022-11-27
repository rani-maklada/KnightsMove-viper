package model;

public class Knight extends Piece{

    public Knight(int positionX, int positionY) {
        super(positionX,positionY);
    }
    @Override
    void movePiece(int positionX, int positionY) {
        setPositionX(positionX);
        setPositionY(positionY);
    }
}

