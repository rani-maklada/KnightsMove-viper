package model;

import javafx.scene.layout.StackPane;

public class Tile extends StackPane {
    private int x,y;
    private boolean occupied;
    private String name;
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.occupied = false;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String status;
        if(this.occupied) status = "Occupied";
        else status = "Not occupied";
//        return "Tile" + this.x + this.y + " - " + status;
        return "Tile";
    }


}