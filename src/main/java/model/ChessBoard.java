package model;

import controller.GamePageController;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
/**
 * The ChessBoard class represents a chess board in the KnightMove game. It has a GridPane layout, an ArrayList of Tile
 * objects, King, Queen, and Knight objects, and a string to store the current theme of the chess board. It has methods
 * to create the tiles for the chess board and add them to the GridPane layout, set the theme of the tiles, and add the
 * chess pieces to the board.
 */
public class ChessBoard {
    // Declare a GridPane object to represent the chess board**
    private GridPane chessBoard;
    // Declare King, Queen, and Knight objects
    protected King king;
    protected Queen queen;
    protected Knight knight;
    // Declare a string to store the current theme of the chess board
    private String theme;
    // Declare an ArrayList to store Tile objects
    private ArrayList<Tile> tiles = new ArrayList<>();
    // Declare a variable to store the size of the chess board
    private int boardSize;

    /**
     * Constructor for the ChessBoard class
     * @param chessBoard GridPane from the FXML
     * @param theme the theme of the board
     * @param boardSize the size of the board
     */
    public ChessBoard(GridPane chessBoard, String theme,int boardSize){
        //**
        this.chessBoard = chessBoard;
        this.theme = theme;
        this.boardSize=boardSize;
        // Call the makeBoard method to create the board
        makeBoard(this.chessBoard, theme);
    }
    // Getters and setters
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

    /**
     * Method to create the tiles for the chess board
     * and add them to the GridPane layout
     * @param chessBoard GridPane from the FXML
     * @param theme the size of the board
     */
    private void makeBoard(GridPane chessBoard, String theme){
        //**
        // Loop through the chess board and create tiles for each position
        for(int i=0; i<boardSize; i++){
            for(int j=0; j<boardSize; j++){
                // Create a new tile and set its name, border, and theme
                Tile tile = new Tile(i,j);
                tile.setName("Tile" + i + j);
                tile.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                setTheme(tile, theme, i, j);
                // Add the tile to the chess board
                chessBoard.add(tile, i, j, 1, 1);
                // Add the tile to the list of tiles
                tiles.add(tile);
            }
        }
        // Add the chess pieces to the board
        addPieces();
    }
    public void setThemeBoard(String theme){
        for (Tile tile : tiles){
            setTheme(tile, theme, tile.getX(), tile.getY());
        }
        this.theme=theme;
    }
    /**
     This method sets the color of the tiles depending on the selected theme.
     @param tile - the Tile object whose color needs to be set
     @param theme - the selected theme
     @param i - the row index of the tile
     @param j - the column index of the tile
     */
    public void setTheme(Tile tile, String theme, int i, int j){
        //**
        // Set the color of the tiles based on the selected theme
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

    /**
     * Adds a chess piece to the specified tile and marks the tile as occupied.
     * @param tile the tile where the piece will be placed
     * @param piece the chess piece to be placed on the tile
     */
    private void addPiece(Tile tile, Piece piece){
        tile.getChildren().add(0,piece);
        tile.setOccupied(true);
    }

    /**
     * The addPieces method places pieces on the board.
     * It iterates through all the tiles in the tiles ArrayList.
     * If a tile is already occupied or is not on the first row,
     * the loop continues to the next tile.
     * Otherwise, if the tile is in the first column, a black knight is placed on the tile.
     * If the tile is in the seventh column and the game stage is less than 2,
     * a white queen is placed on the tile.
     * If the tile is in the seventh column and the game stage is 2 or greater,
     * a white king is placed on the tile.
     */
    public void addPieces(){
        Tile tile;
        tile = getTileByPos(0,0);
        this.knight = (Knight) PieceFactory.createPiece("Knight","black");
        this.knight.setImage();
        addPiece(tile, this.knight);
        tile = getTileByPos(7,0);
        if(boardSize == 8){
            if(GamePageController.myStage == 1){
                this.queen = (Queen) PieceFactory.createPiece("Queen","white");
                addPiece(tile,this.queen);
            } else{
            this.king = (King) PieceFactory.createPiece("King","white");
            addPiece(tile, king);
            }
        }
    }
    /**
     * This method returns the Tile object at the specified position on the chess board.
     * @param x - the row index of the tile
     * @param y - the column index of the tile
     * @return the Tile object at the specified position, or null if no such tile exists
     */
    private Tile getTileByPos(int x, int y){
        for(Tile tile : tiles){
            if(tile.getX() == x && tile.getY() == y){
                return tile;
            }
        }
        return null;
    }
}