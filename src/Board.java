public class Board {
    public static final int SIZE = 8;
    public Piece[][] board;
    public MoveList moveHistory = new MoveList();
    public boolean turn = true; // true = white, false = black

    public Board() {
        board = new Piece[SIZE][SIZE];

        initPieces();
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
        board[square.rank - 1][square.file - 1] = piece;
    }

    public Piece getPiece(Square square) {
        return board[square.rank - 1][square.file - 1];
    }

    public void movePiece(Move move) {
        Piece piece = getPiece(move.from);
        setPiece(move.to, piece);
        setPiece(move.from, null);
        moveHistory.add(move);
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
                    clone.setPiece(square, new Piece(piece.type, piece.color));
                }
            }
        }
        return clone;
    }

    public boolean isValidMove(Move move) {
        // Placeholder
        System.out.println("Move: " + move);
        MoveList moves = MovesGenerator.validPieceMoves(this, getPiece(move.from), move.from);
        System.out.println("Valid moves: " + moves);
        return moves.contains(move);
    }
}
