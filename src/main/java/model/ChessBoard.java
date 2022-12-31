package model;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;

import java.util.ArrayList;

public class ChessBoard {

    private GridPane chessBoard;
    protected King king;
    protected Queen queen;
    protected Knight knight;
    private String theme;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private int boardSize;

    public ChessBoard(GridPane chessBoard, String theme,int boardSize){
        this.chessBoard = chessBoard;
        this.theme = theme;
        this.boardSize=boardSize;
        makeBoard(this.chessBoard, theme);
    }

    public King getKing() {
        return king;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public Queen getQueen() {
        return queen;
    }

    public void setQueen(Queen queen) {
        this.queen = queen;
    }

    public Knight getKnight() {
        return knight;
    }

    public void setKnight(Knight knight) {
        this.knight = knight;
    }

    private void makeBoard(GridPane chessBoard, String theme){
        for(int i=0; i<boardSize; i++){
            for(int j=0; j<boardSize; j++){
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
//    public void clickButton() {
//        if(GridPane.getRowIndex(circle1) == 0){
//            GridPane.setRowIndex(circle1, 1);
//        } else {
//            GridPane.setRowIndex(circle1, 0);
//        }
//    }
    public void setThemeBoard(String theme){
        for (Tile tile : tiles){
            setTheme(tile, theme, tile.getX(), tile.getY());
        }
        this.theme=theme;
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

    public GridPane getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(GridPane chessBoard) {
        this.chessBoard = chessBoard;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    private void addPiece(Tile tile, Piece piece){
        tile.getChildren().add(0,piece);
//        tile.getChildren().add(piece);
        tile.setOccupied(true);
    }

    public void addPieces(){
        for(Tile tile : tiles){
            if(tile.isOccupied()) continue;
            if(tile.getY() == 0){
              if(tile.getX() == 0){
                    this.knight = (Knight) PieceFactory.createPiece("Knight","black", tile.getX(), tile.getY());
                    this.knight.setImage();
                    addPiece(tile, this.knight);
                }
               if(tile.getX() == 7){
                  this.king = (King) PieceFactory.createPiece("King","white", tile.getX(), tile.getY());
                   addPiece(tile, king);
               }
            //    if(tile.getX() == 7){
               //     this.queen = (Queen) PieceFactory.createPiece("Queen","white", tile.getX(), tile.getY());
                 //   addPiece(tile,this.queen );
               // }
            }
        }
    }
}