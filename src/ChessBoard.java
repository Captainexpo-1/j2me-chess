
public class ChessBoard {
    private Piece[][] board;
    private MoveList moveHistory;
    private boolean isCheck;
    private boolean isCheckmate;
    private boolean isStalemate;

    private boolean whiteCastled;
    private boolean blackCastled;

    public PieceList[] pieceLists;

    public Color turn;

    // Initialization & Board Setup
    public void initializeBoard() {
        board = new Piece[8][8];

        // Pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece(Piece.PAWN, Color.WHITE, new Square(1, i));
            board[6][i] = new Piece(Piece.PAWN, Color.BLACK, new Square(6, i));
        }

        // Rooks
        board[0][0] = new Piece(Piece.ROOK, Color.WHITE, new Square(0, 0));
        board[0][7] = new Piece(Piece.ROOK, Color.WHITE, new Square(0, 7));

        board[7][0] = new Piece(Piece.ROOK, Color.BLACK, new Square(7, 0));
        board[7][7] = new Piece(Piece.ROOK, Color.BLACK, new Square(7, 7));

        // Knights
        board[0][1] = new Piece(Piece.KNIGHT, Color.WHITE, new Square(0, 1));
        board[0][6] = new Piece(Piece.KNIGHT, Color.WHITE, new Square(0, 6));

        board[7][1] = new Piece(Piece.KNIGHT, Color.BLACK, new Square(7, 1));
        board[7][6] = new Piece(Piece.KNIGHT, Color.BLACK, new Square(7, 6));

        // Bishops
        board[0][2] = new Piece(Piece.BISHOP, Color.WHITE, new Square(0, 2));
        board[0][5] = new Piece(Piece.BISHOP, Color.WHITE, new Square(0, 5));

        board[7][2] = new Piece(Piece.BISHOP, Color.BLACK, new Square(7, 2));
        board[7][5] = new Piece(Piece.BISHOP, Color.BLACK, new Square(7, 5));

        // Queens
        board[0][3] = new Piece(Piece.QUEEN, Color.WHITE, new Square(0, 3));
        board[7][3] = new Piece(Piece.QUEEN, Color.BLACK, new Square(7, 3));

        // Kings
        board[0][4] = new Piece(Piece.KING, Color.WHITE, new Square(0, 4));
        board[7][4] = new Piece(Piece.KING, Color.BLACK, new Square(7, 4));

        this.moveHistory = new MoveList();

        this.turn = Color.WHITE;
        this.isCheck = false;
        this.isCheckmate = false;
        this.isStalemate = false;
        this.whiteCastled = false;
        this.blackCastled = false;

        initPieceLists();
    }

    public void initPieceLists() { // Assumes board has been set up
        this.pieceLists = new PieceList[12]; // 6 pieces for each color (6 * 2 = 12)
        for (int i = 0; i < 12; i++) {
            pieceLists[i] = new PieceList();
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null)
                    continue;
                int pieceType = board[i][j].getType();
                Color color = board[i][j].getColor();
                pieceLists[pieceType + (color == (Color.WHITE) ? 0 : 6)].add(board[i][j]);
            }
        }

        // Print piece lists
        for (int i = 0; i < 12; i++) {
            System.out.println(pieceLists[i].toString());
        }
    }

    public PieceList getPieceList(Color color, int pieceType) {
        return pieceLists[pieceType + (color == (Color.WHITE) ? 0 : 6)];
    }

    public Piece findPiece(int pieceType, Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null)
                    continue;
                if (board[i][j].getType() == pieceType && board[i][j].getColor() == (color)) {
                    return board[i][j];
                }
            }
        }
        return null;
    }

    public MoveList getMoveHistory() {
        return moveHistory;
    }

    public void clearBoard() {
        board = new Piece[8][8];
        moveHistory.clear();

        turn = Color.WHITE;
        isCheck = false;
        isCheckmate = false;
        isStalemate = false;
        whiteCastled = false;
        blackCastled = false;
    }

    // Move Handling
    public boolean makeMove(Move move) {
        // Temporary simple implementation
        setPieceAt(move.to, move.piece);
        setPieceAt(move.from, null);

        moveHistory.add(move);

        this.turn = this.turn.opposite();
        System.out.println("SET TURN TO: " + turn.toString());

        return true;
    }

    public boolean isValidMove(Move move) {
        return true; // TODO: Implement
    }

    public void undoMove() {
        Move lastMove = moveHistory.remove(moveHistory.size() - 1);
        Piece takenPiece = lastMove.capturedPiece;
        setPieceAt(lastMove.from, lastMove.piece);
        setPieceAt(lastMove.to, takenPiece);

        turn = turn.opposite();
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
        if (square == null) {
            throw new IllegalArgumentException("Square cannot be null");
        }
        this.board[square.rank][square.file] = piece;
        if (piece != null)
            piece.setSquare(square);
    }

    public MoveList getValidMoves(Piece piece, Square square) {
        return null;
    }

    public boolean isSquareAttacked(Square square, Color attackerColor) {
        return false;
    }

    // Special Moves
    public boolean canCastle(Color playerColor) {
        if (playerColor == (Color.WHITE)) {
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