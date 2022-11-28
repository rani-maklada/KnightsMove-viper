package controller;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;

import java.util.ArrayList;

public class ChessBoard {

    private GridPane chessBoard;
    private String theme;
    private ArrayList<Tile> tiles = new ArrayList<>();

    public ChessBoard(GridPane chessBoard, String theme){
        this.chessBoard = chessBoard;
        this.theme = theme;

        makeBoard(this.chessBoard, theme);
    }
    private void makeBoard(GridPane chessBoard, String theme){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                Tile tile = new Tile(i,j);
                tile.setName("Tile" + i + j);
                tile.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                setTheme(tile, theme, i, j);
                chessBoard.add(tile, i, j, 1, 1);
                tiles.add(tile);
            }
        }
        addPieces();
    }

    private void setTheme(Tile tile, String theme, int i, int j){
        Color color1 = Color.web("#ffffff00");
        Color color2 = Color.web("#ffffff00");

        switch (theme) {
            case "Coral" -> {
                color1 = Color.web("#b1e4b9");
                color2 = Color.web("#70a2a3");
            }
            case "Dusk" -> {
                color1 = Color.web("#cbb7ae");
                color2 = Color.web("#716677");
            }
            case "Wheat" -> {
                color1 = Color.web("#eaefce");
                color2 = Color.web("#bbbe65");
            }
            case "Marine" -> {
                color1 = Color.web("#9dacff");
                color2 = Color.web("#6f74d2");
            }
            case "Emerald" -> {
                color1 = Color.web("#adbd90");
                color2 = Color.web("#6e8f72");
            }
            case "Sandcastle" -> {
                color1 = Color.web("#e4c16f");
                color2 = Color.web("#b88b4a");
            }
        }

        if((i+j)%2==0){
            tile.setBackground(new Background(new BackgroundFill(color1, CornerRadii.EMPTY, Insets.EMPTY)));
        }else{
            tile.setBackground(new Background(new BackgroundFill(color2, CornerRadii.EMPTY, Insets.EMPTY)));
        }

    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    private void addPiece(Tile tile, Piece piece){
        tile.getChildren().add(piece);
        tile.setOccupied(true);
    }

    private void addPieces(){
        for(Tile tile : tiles){
            if(tile.isOccupied()) continue;
            if(tile.getY() == 0){
                if(tile.getX() == 0){
                    addPiece(tile, new Knight("black", tile.getX(), tile.getY()));
                }
                if(tile.getX() == 1){
                    addPiece(tile, new King("white", tile.getX(), tile.getY()));
                }
                if(tile.getX() == 7){
                    addPiece(tile, new Queen("white", tile.getX(), tile.getY()));
                }
            }
        }
    }
}