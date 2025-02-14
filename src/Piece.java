public class Piece {
    public static final int PAWN = 0;
    public static final int KNIGHT = 1;
    public static final int BISHOP = 2;
    public static final int ROOK = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;

    protected int type;
    protected Color color;

    public Piece(int type, Color color) {
        this.type = type;
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public boolean isWhite() {
        return color == Color.WHITE;
    }

    public Color getColor() {
        return color;
    }

    public String getChar() {
        if (isWhite()) {
            return getPieceSymbol();
        } else {
            return getPieceSymbol().toLowerCase();
        }
    }

    private String getPieceSymbol() {
        switch (type) {
            case PAWN:
                return "P";
            case KNIGHT:
                return "N";
            case BISHOP:
                return "B";
            case ROOK:
                return "R";
            case QUEEN:
                return "Q";
            case KING:
                return "K";
            default:
                return "U";
        }
    }
}
