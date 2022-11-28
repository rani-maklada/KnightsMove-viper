package model;

import controller.GamePageController;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Piece extends ImageView {
    private String type;
    private String color;
    private int posX, posY;
    private ArrayList<String> possibleMoves;

    public Piece(String color, int posX, int posY, String type) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.type = type;
        setImage();
        addEventHandler();
    }

    public String getColor() {
        return this.color;
    }

    public void setPiece(Image image) {
        this.setImage(image);
    }

    public void setImage() {
        this.setPiece(new Image(String.valueOf(getClass().getResource("/view/pieces/" + this.color + "" + this.type + ".png"))));

    }

    private void addEventHandler() {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getAllPossibleMoves();
            }
        });


    }

    public void getAllPossibleMoves() {
    }

    public void showAllPossibleMoves(boolean val) {
        if (val) {
            Glow glow = new Glow();
            glow.setLevel(0.3);
            for (String move : possibleMoves) {
                Tile tile = getTileByName(move);
                tile.setEffect(glow);

                Piece piece = getPieceByName(move);
                if (piece == null) continue;
                if (piece.type.equals("King")) {
                    tile.setBorder(new Border(new BorderStroke(Color.DARKRED,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.5))));
                } else {
                    tile.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.2))));
                }
            }
        } else {
            for (String move : possibleMoves) {
                Tile tile = getTileByName(move);
                tile.setEffect(null);
                tile.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
    }

    public Tile getTileByName(String name) {
        for (Tile tile : GamePageController.cb.getTiles()){
            if (tile.getName().equals(name)) {
                return tile;
            }
        }

        return null;
    }

    public Piece getPieceByName(String name) {
        for (Tile tile : GamePageController.cb.getTiles()) {
            if (tile.getChildren().size() == 0) continue;

            if (tile.getName().equals(name))
                return (Piece) tile.getChildren().get(0);

        }
        return null;
    }

    @Override
    public String toString() {
        return this.color + " " + this.type;
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
}