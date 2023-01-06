package model;
/**
 * A simple factory class for creating Piece objects.
 */
public class PieceFactory {
    /**
     * Creates a new Piece object based on the specified pieceType and color.
     * @param pieceType the type of Piece to create (e.g. "knight", "queen", "king")
     * @param color the color of the Piece (e.g. "white", "black")
     * @return a new Piece object, or null if the pieceType is not recognized
     */
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
