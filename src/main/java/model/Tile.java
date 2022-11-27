package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Tile extends Rectangle {
    public boolean isVisited;
    public String type;

    public Tile() {
        this.isVisited = true;
        this.type = String.valueOf(Type.REGULAR);
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public void changeColor(String color){
        switch(color) {
            case "red":
                this.setFill(Color.RED);
            break;
            case "orange":
                this.setFill(Color.ORANGE);
            break;
            case "blue":
                this.setFill(Color.BLUE);
            case "green":
                this.setFill(Color.GREEN);
        }
    }

    @Override
    public String toString() {
        return "Tile{" +
                "isVisited=" + isVisited +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile tile)) return false;
        return isVisited() == tile.isVisited() && Objects.equals(getType(), tile.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isVisited(), getType());
    }
}
