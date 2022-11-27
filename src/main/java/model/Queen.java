package model;

public class Queen extends Piece{

    public Queen(int positionX, int positionY) {
        super(positionX,positionY);
    }



    @Override
    void movePiece(int positionX, int positionY) {
        setPositionX(positionX);
        setPositionY(positionY);
    }
}
