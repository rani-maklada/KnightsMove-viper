package model;

import Enums.QuestionLevel;
import Enums.TileType;
import javafx.scene.layout.StackPane;

public class Tile extends StackPane {
    private int x,y;
    private boolean occupied;
    private String name;
    private boolean visited;
    private TileType type;
    private Question question;
    private QuestionLevel level;
    public Tile(int x, int y){
//        if ((x>8 || x<0) || (y>8 || y<0)){
//            System.out.println("Can't create a tile outside of borders");
//            return;
//        }
        this.x = x;
        this.y = y;
        this.occupied = false;
        this.visited = false;
        this.name ="Tile"+x+y;
        this.type = TileType.Nothing;
        question = null;
    }
    public Question getQuestion(){
        if(this.type == TileType.QuestionTile){
            return question;
        }
        return null;
    }
    public void setQuestion(Question q){
        this.question = q;
        switch (q.getLevel()){
            case 1->{
                level = QuestionLevel.Easy;
            }
            case 2->{
                level = QuestionLevel.Medium;
            }
            case 3->{
                level = QuestionLevel.Hard;
            }
        }
    }
     void checkValidPosition(int x, int y){

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
    public boolean isVisited() {
        return visited;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        return name.equals(tile.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
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