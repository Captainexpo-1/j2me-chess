import java.util.Vector;

public class Board {
    public static final int SIZE = 8;
    public Piece[][] board;
    public BoardStateList boardHistory = new BoardStateList();
    public MoveList moveHistory = new MoveList();
    public boolean turn = true; // true = white, false = black
    PieceList[] pieceLists = new PieceList[12]; // One for each piece type and color

    public static final int STATE_CHECKMATE = 0;
    public static final int STATE_STALEMATE = 1;
    public static final int STATE_CHECK = 2;
    public static final int STATE_NORMAL = 3;

    public Board() {
        board = new Piece[SIZE][SIZE];

        initPieces();
        initPieceLists();
    }

    public void initPieceLists() {
        for (int i = 0; i < pieceLists.length; i++) {
            pieceLists[i] = new PieceList();
        }

        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                Square square = new Square(rank, file);
                Piece piece = getPiece(square);
                if (piece != null) {
                    pieceLists[piece.type * 2 + piece.color].add(piece);
                }
            }
        }
    }

    public void initPieces() {
        for (int file = 1; file <= SIZE; file++) {
            setPiece(new Square(2, file), new Piece(Piece.PAWN, Piece.WHITE));
            setPiece(new Square(7, file), new Piece(Piece.PAWN, Piece.BLACK));
        }
        setPiece(new Square(1, 1), new Piece(Piece.ROOK, Piece.WHITE));
        setPiece(new Square(1, 8), new Piece(Piece.ROOK, Piece.WHITE));
        setPiece(new Square(8, 1), new Piece(Piece.ROOK, Piece.BLACK));
        setPiece(new Square(8, 8), new Piece(Piece.ROOK, Piece.BLACK));

        setPiece(new Square(1, 2), new Piece(Piece.KNIGHT, Piece.WHITE));
        setPiece(new Square(1, 7), new Piece(Piece.KNIGHT, Piece.WHITE));
        setPiece(new Square(8, 2), new Piece(Piece.KNIGHT, Piece.BLACK));
        setPiece(new Square(8, 7), new Piece(Piece.KNIGHT, Piece.BLACK));

        setPiece(new Square(1, 3), new Piece(Piece.BISHOP, Piece.WHITE));
        setPiece(new Square(1, 6), new Piece(Piece.BISHOP, Piece.WHITE));
        setPiece(new Square(8, 3), new Piece(Piece.BISHOP, Piece.BLACK));
        setPiece(new Square(8, 6), new Piece(Piece.BISHOP, Piece.BLACK));

        setPiece(new Square(1, 4), new Piece(Piece.QUEEN, Piece.WHITE));
        setPiece(new Square(8, 4), new Piece(Piece.QUEEN, Piece.BLACK));

        setPiece(new Square(1, 5), new Piece(Piece.KING, Piece.WHITE));
        setPiece(new Square(8, 5), new Piece(Piece.KING, Piece.BLACK));

    }

    public void setPiece(Square square, Piece piece) {
        if (piece != null) {
            piece.rank = square.rank;
            piece.file = square.file;
        }
        board[square.rank - 1][square.file - 1] = piece;
    }

    public Piece getPiece(Square square) {
        return board[square.rank - 1][square.file - 1];
    }

    public void makeMove(Move move) {
        boardHistory.add(this);

        Piece piece = getPiece(move.from);
        setPiece(move.to, piece);
        setPiece(move.from, null);
        moveHistory.add(move);
        turn = !turn;
    }

    public void undoMove() {
        Piece[][] state = boardHistory.pop();
        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                board[rank - 1][file - 1] = state[rank - 1][file - 1];
            }
        }
        moveHistory.remove(moveHistory.size() - 1);
    }

    public void undoMove(Move move) {
        Piece piece = getPiece(move.to);
        setPiece(move.from, piece);
        setPiece(move.to, null);
        turn = !turn;
    }

    public String[] toStrings() {
        String[] strings = new String[SIZE];
        String str = "";
        for (int rank = SIZE; rank >= 1; rank--) {
            for (int file = 1; file <= SIZE; file++) {
                Piece piece = getPiece(new Square(rank, file));
                if (piece == null) {
                    str += ".";
                } else {
                    str += piece.toChar();
                }
            }
            strings[SIZE - rank] = str;
            str = "";
        }
        return strings;
    }

    public Board clone() {
        Board clone = new Board();
        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                Square square = new Square(rank, file);
                Piece piece = getPiece(square);
                if (piece != null) {
                    clone.setPiece(square, piece.clone());
                }
            }
        }
        return clone;
    }

    public boolean isInCheck() {
        Square kingSquare = null;
        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                Piece piece = getPiece(new Square(rank, file));
                if (piece != null && piece.type == Piece.KING && piece.color == (turn ? Piece.WHITE : Piece.BLACK)) {
                    kingSquare = new Square(rank, file);
                    break;
                }
            }
        }
        if (kingSquare == null) {
            return false;
        }
        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                Square square = new Square(rank, file);
                Piece piece = getPiece(square);
                if (piece != null && piece.color != (turn ? Piece.WHITE : Piece.BLACK)) {
                    MoveList moves = MovesGenerator.validPieceMoves(this, piece, square);
                    if (moves.contains(new Move(square, kingSquare, piece, Piece.KING))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getState() {
        MoveList moves = MovesGenerator.validMoves(this);
        if (moves.size() == 0) {
            if (isInCheck()) {
                return STATE_CHECKMATE;
            } else {
                return STATE_STALEMATE;
            }
        } else if (isInCheck()) {
            return STATE_CHECK;
        } else {
            return STATE_NORMAL;
        }
    }

    public String getStateString() {
        int state = getState();
        switch (state) {
            case STATE_CHECKMATE:
                return "Checkmate";
            case STATE_STALEMATE:
                return "Stalemate";
            case STATE_CHECK:
                return "Check";
            case STATE_NORMAL:
                return "Normal";
            default:
                return "Unknown";
        }
    }

    public PieceList getPieceList(int color, int pieceType) {
        return pieceLists[pieceType * 2 + color];
    }

    public boolean isValidMove(Move move) {
        MoveList moves = MovesGenerator.validPieceMoves(this, getPiece(move.from), move.from);
        return moves.contains(move);
    }
}
