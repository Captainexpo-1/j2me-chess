public class Move {

    public static final int NORMAL = 0;
    public static final int CASTLING = 1;
    public static final int ENPASSANT = 2;
    public static final int PROMOTION = 3;

    Square from;
    Square to;
    Piece piece;
    Piece capturedPiece;
    int type;

    public Move(ChessBoard board, Square from, Square to, Piece piece, Piece capturedPiece) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        if (piece == null) {
            throw new IllegalArgumentException("Piece cannot be null");
        }
        this.capturedPiece = capturedPiece;
        checkType(board); // Ensure type is set when move is created
    }

    private void checkType(ChessBoard board) {
        if (piece.type == Piece.KING && Math.abs(from.file - to.file) == 2) {
            type = CASTLING;
        } else if (piece.type == Piece.PAWN && (to.rank == 0 || to.rank == 7)) {
            type = PROMOTION;
        } else if (piece.type == Piece.PAWN && board.getPieceAt(to) == null && from.file != to.file) {
            type = ENPASSANT;
        } else {
            type = NORMAL;
        }
    }

    public String toString() {
        return from + "-" + to;
    }

    public boolean equals(Object o) {
        if (o instanceof Move) {
            Move m = (Move) o;
            return m.from.equals(from) && m.to.equals(to) && m.piece.equals(piece);
        }
        return false;
    }
}