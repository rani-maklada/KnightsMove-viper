package model;

public class PieceFactory {
    static public Piece createPiece(String pieceType, String color, int x, int y) {
        if (pieceType == null) {
            return null;
        }
        if (pieceType.equalsIgnoreCase("knight")) {
            return new Knight(color,x,y);
        } else if (pieceType.equalsIgnoreCase("queen")) {
            return new Queen(color,x,y);
        }else if (pieceType.equalsIgnoreCase("king")) {
            return new King(color,x,y);
        }
        return null;
    }
}
