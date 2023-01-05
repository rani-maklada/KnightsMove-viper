package model;

import controller.GamePageController;

import java.util.ArrayList;

public class Queen extends Piece{

    public Queen(String color) {
        super(color, 7,0, "Queen");
    }
    @Override

    public void getAllPossibleMoves() {
        int x = this.getPosX();
        int y = this.getPosY();
        String name;
        this.setPossibleMoves(new ArrayList<>());
        for (int i = x - 1; i >= 0; i--) {
            name = "Tile" + i + y;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int i = x + 1; i < 8; i++) {
            name = "Tile" + i + y;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int j = y - 1; j >= 0; j--) {
            name = "Tile" + x + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int j = y + 1; j < 8; j++) {
            name = "Tile" + x + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int i = x - 1, j = y + 1; i >= 0 && j < 8; i--, j++) {
            name = "Tile" + i + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int i = x + 1, j = y + 1; i < 8 && j < 8; i++, j++) {
            name = "Tile" + i + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int i = x + 1, j = y - 1; i < 8 && j >= 0; i++, j--) {
            name = "Tile" + i + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }

        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            name = "Tile" + i + j;
            getPossibleMoves().add(name);
            if (getTileByName(name).isOccupied()) break;
        }
    }
}
