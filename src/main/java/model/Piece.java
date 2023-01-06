package model;

import controller.GamePageController;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
/**
 * This class represents a piece on a chessboard. It has a color (white or black), a position (x,y) on the board,
 * and a type (king, queen, knight). It also has an ArrayList of possible moves that it can make.
 * The Piece class has a setImage method to set the image of the piece based on its color and type, and an
 * addEventHandler method to add a MouseClick event handler to the piece. When the piece is clicked, its
 * getAllPossibleMoves method is called to calculate all the possible moves it can make. The
 * showAllPossibleMoves method is used to highlight all the tiles that the piece can move to. The getTileByName
 * method is a static helper method that returns a Tile object with a given name.
 */
public class Piece extends ImageView {
    private String type;
    private String color;
    private int posX, posY;
    private ArrayList<String> possibleMoves;
    /**
     * Constructor for the Piece class.
     * @param color - the color of the piece (black or white)
     * @param posX - the x-coordinate of the piece on the chess board
     * @param posY - the y-coordinate of the piece on the chess board
     * @param type - the type of the piece (King, Queen, or Knight)
     */
    public Piece(String color, int posX, int posY, String type) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.type = type;
        setImage();
        addEventHandler();
    }
    /**
     * Add an event handler to the Piece object that shows all possible moves when the piece is clicked.
     */
    private void addEventHandler() {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getAllPossibleMoves();
            }
        });
    }
    /**
     * Abstract method to be implemented by subclasses to calculate all possible moves for the piece.
     */
    public void getAllPossibleMoves() {
    }
    /**
     * Method to show or hide all possible moves for the piece.
     * @param val a boolean value indicating whether to show (true) or hide (false) the possible moves
     */
    public void showAllPossibleMoves(boolean val) {
        if (val) {
            Glow glow = new Glow();
            glow.setLevel(0.7);
            for (String move : possibleMoves) {
                Tile tile = getTileByName(move);
                tile.setEffect(glow);
            }
        } else {
            for (String move : possibleMoves) {
                Tile tile = getTileByName(move);
                tile.setEffect(null);
            }
        }
    }
    /**
     * This method searches for a Tile object in the list of tiles in the ChessBoard object by its name.
     * @param name - the name of the Tile object to search for
     * @return the Tile object with the specified name, or null if no such Tile object is found
     */
    static public Tile getTileByName(String name) {
        for (Tile tile : GamePageController.cb.getTiles()){
            if (tile.getName().equals(name)) {
                return tile;
            }
        }
        return null;
    }
    // Getters and setters for the fields
    public String getColor() {
        return this.color;
    }

    public void setPiece(Image image) {
        this.setImage(image);
    }

    public void setImage() {
        this.setPiece(new Image(String.valueOf(getClass().getResource("/view/pieces/" + this.color + "" + this.type + ".png"))));
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public int getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public ArrayList<String> getPossibleMoves() {
        return possibleMoves;
    }
    public void setPossibleMoves(ArrayList<String> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
    @Override
    public String toString() {
        return this.color + " " + this.type;
    }
}