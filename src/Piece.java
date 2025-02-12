
public class Piece {
    public static final int PAWN = 0;
    public static final int KNIGHT = 1;
    public static final int BISHOP = 2;
    public static final int ROOK = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public int type;
    public int color;

    public int rank;
    public int file;

    public Piece(int type, int color, int rank, int file) {
        this.type = type;
        this.color = color;
        this.rank = rank;
        this.file = file;
    }

    public Piece(int type, int color) {
        this.type = type;
        this.color = color;
        this.rank = 0;
        this.file = 0;
    }

    public String toString() {
        String colorStr = color == WHITE ? "White" : "Black";
        String typeStr = "";
        switch (type) {
            case PAWN:
                typeStr = "Pawn";
                break;
            case KNIGHT:
                typeStr = "Knight";
                break;
            case BISHOP:
                typeStr = "Bishop";
                break;
            case ROOK:
                typeStr = "Rook";
                break;
            case QUEEN:
                typeStr = "Queen";
                break;
            case KING:
                typeStr = "King";
                break;
        }
        return "Piece(" + colorStr + " " + typeStr + " at " + (char) (file - 1 + 'a') + rank + ")";
    }

    public String toChar() {
        String typeStr = "";
        switch (type) {
            case PAWN:
                typeStr = "p";
                break;
            case KNIGHT:
                typeStr = "n";
                break;
            case BISHOP:
                typeStr = "b";
                break;
            case ROOK:
                typeStr = "r";
                break;
            case QUEEN:
                typeStr = "q";
                break;
            case KING:
                typeStr = "k";
                break;
        }
        return color == BLACK ? typeStr : typeStr.toUpperCase();
    }

    public boolean equals(Object o) {
        if (o instanceof Piece) {
            Piece p = (Piece) o;
            return p.type == type && p.color == color;
        }
        return false;
    }

    public int hashCode() {
        return type * 10 + color;
    }

    public Piece clone() {
        return new Piece(type, color, rank, file);
    }
}
