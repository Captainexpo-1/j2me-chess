
public class Move {

    public static final int NORMAL = 0;
    public static final int CASTLING = 1;
    public static final int ENPASSANT = 2;
    public static final int PROMOTION = 3;

    Square from;
    Square to;
    Piece piece;
    int capturedPieceType;
    int type;

    public void checkType() {
        // TODO: Handle en passant
        if (piece.type == Piece.PAWN) {
            if (to.rank == 1 || to.rank == 8) {
                type = PROMOTION;
            } else {
                type = NORMAL;
            }
        } else if (piece.type == Piece.KING && Math.abs(from.file - to.file) == 2) {
            type = CASTLING;
        } else {
            type = NORMAL;
        }
    }

    public Move(Square from, Square to, Piece piece, int capturedPieceType) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.capturedPieceType = capturedPieceType;
    }

    public String toString() {
        return from + "-" + to;
    }

    public boolean equals(Object o) {
        if (o instanceof Move) {
            Move m = (Move) o;
            return m.from.equals(from) && m.to.equals(to);
        }
        return false;
    }
}
