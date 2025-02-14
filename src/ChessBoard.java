import java.util.Vector;

public class ChessBoard {
    private Piece[][] board;
    private boolean whiteToMove;
    private MoveList moveHistory;
    private boolean isCheck;
    private boolean isCheckmate;
    private boolean isStalemate;

    private boolean whiteCastled = false;
    private boolean blackCastled = false;

    public Color turn = Color.WHITE;

    // Initialization & Board Setup
    public void initializeBoard() {
        board = new Piece[8][8];

        // Pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece(Piece.PAWN, Color.WHITE);
            board[6][i] = new Piece(Piece.PAWN, Color.BLACK);
        }

        // Rooks
        board[0][0] = new Piece(Piece.ROOK, Color.WHITE);
        board[0][7] = new Piece(Piece.ROOK, Color.WHITE);

        board[7][0] = new Piece(Piece.ROOK, Color.BLACK);
        board[7][7] = new Piece(Piece.ROOK, Color.BLACK);

        // Knights
        board[0][1] = new Piece(Piece.KNIGHT, Color.WHITE);
        board[0][6] = new Piece(Piece.KNIGHT, Color.WHITE);

        board[7][1] = new Piece(Piece.KNIGHT, Color.BLACK);
        board[7][6] = new Piece(Piece.KNIGHT, Color.BLACK);

        // Bishops
        board[0][2] = new Piece(Piece.BISHOP, Color.WHITE);
        board[0][5] = new Piece(Piece.BISHOP, Color.WHITE);

        board[7][2] = new Piece(Piece.BISHOP, Color.BLACK);
        board[7][5] = new Piece(Piece.BISHOP, Color.BLACK);

        // Queens
        board[0][3] = new Piece(Piece.QUEEN, Color.WHITE);
        board[7][3] = new Piece(Piece.QUEEN, Color.BLACK);

        // Kings
        board[0][4] = new Piece(Piece.KING, Color.WHITE);
        board[7][4] = new Piece(Piece.KING, Color.BLACK);

        moveHistory = new MoveList();

        whiteToMove = true;
        isCheck = false;
        isCheckmate = false;
        isStalemate = false;
    }

    public MoveList getMoveHistory() {
        return moveHistory;
    }

    public void clearBoard() {
    }

    // Move Handling
    public boolean makeMove(Move move) {
        // Temporary simple implementation
        setPieceAt(move.to, move.piece);
        setPieceAt(move.from, null);

        moveHistory.add(move);

        return true;
    }

    public boolean isValidMove(Move move) {
        return true; // TODO: Implement
    }

    public void undoMove() {
    }

    // Game State Checks
    public boolean isCheck(Color playerColor) {
        return isCheck;
    }

    public boolean isCheckmate(Color playerColor) {
        return isCheckmate;
    }

    public boolean isStalemate(Color playerColor) {
        return isStalemate;
    }

    // Piece & Board Management
    public Piece getPieceAt(Square square) {
        return this.board[square.rank][square.file];
    }

    public void setPieceAt(Square square, Piece piece) {
        this.board[square.rank][square.file] = piece;
    }

    public MoveList getValidMoves(Piece piece, Square square) {
        return null;
    }

    public boolean isSquareAttacked(Square square, Color attackerColor) {
        return false;
    }

    // Special Moves
    public boolean canCastle(Color playerColor) {
        if (playerColor.is(Color.WHITE)) {
            return whiteCastled;
        } else {
            return blackCastled;
        }
    }

    // Board Representation & Debugging
    public String[] getBoardStrings() {
        String[] boardString = new String[8];
        for (int i = 0; i < 8; i++) {
            String o = "";
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    o += ".";
                } else {
                    o += board[i][j].getChar();
                }
            }
            boardString[i] = o;
        }
        return boardString;
    }

    public String toString() {
        String o = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    o += ".";
                } else {
                    o += board[i][j].getChar();
                }
            }
            o += "\n";
        }
        return o;
    }
}