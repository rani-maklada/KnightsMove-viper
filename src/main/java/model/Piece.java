package model;

import javafx.scene.shape.Rectangle;

public abstract class Piece {
    public int  positionX;
    public int  positionY;



    public Piece(int positionX, int positionY) {

        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    abstract void movePiece(int positionX, int positionY);
}
