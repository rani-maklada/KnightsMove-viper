package model;

public class King extends Piece{

    public King(int positionX, int positionY) {
        super(positionX,positionY);
    }



    @Override
    void movePiece(int positionX, int positionY) {
        setPositionX(positionX);
        setPositionY(positionY);
    }
}
