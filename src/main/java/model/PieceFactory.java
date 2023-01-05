package model;

public class PieceFactory {
    static public Piece createPiece(String pieceType, String color) {
        if (pieceType == null) {
            return null;
        }
        if (pieceType.equalsIgnoreCase("knight")) {
            return new Knight(color);
        } else if (pieceType.equalsIgnoreCase("queen")) {
            return new Queen(color);
        }else if (pieceType.equalsIgnoreCase("king")) {
            return new King(color);
        }
        return null;
    }
}
