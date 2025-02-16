public class MovesGenerator {
    private ChessBoard board;

    public MovesGenerator(ChessBoard board) {
        this.board = board;
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    public MoveList allValidMoves() {
        MoveList moves = allMoves();
        MoveList validMoves = new MoveList();
        // Make sure the king is not attacked after the move
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            board.makeMove(move);
            Piece king = board.findPiece(Piece.KING, board.turn.opposite());
            if (king == null) {
                throw new NullPointerException(board.turn.opposite().toString() + " King couldn't be found");
            }
            if (!isPieceAttacked(king, board.turn)) {
                validMoves.add(move);
            }
            board.undoMove();
        }
        return validMoves;
    }

    public MoveList allMoves() {
        MoveList moves = new MoveList();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board.getPieceAt(new Square(i, j));
                if (p != null && p.getColor() == board.turn) {
                    moves.append(getValidMoves(p));
                }
            }
        }
        return moves;
    }

    public MoveList allMoves(Color color) {
        MoveList moves = new MoveList();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board.getPieceAt(new Square(i, j));
                if (p != null && p.getColor() == (color)) {
                    moves.append(getValidMoves(p));
                } else if (p != null) {
                    System.out.println("Piece is not valid: " + p.toLongString());
                }
            }
        }
        return moves;
    }

    public MoveList getValidMoves(Piece piece) {
        switch (piece.getType()) {
            case Piece.PAWN:
                return getPawnMoves(piece);
            case Piece.KNIGHT:
                return getKnightMoves(piece);
            case Piece.BISHOP:
                return getBishopMoves(piece);
            case Piece.ROOK:
                return getRookMoves(piece);
            case Piece.QUEEN:
                return getQueenMoves(piece);
            case Piece.KING:
                return getKingMoves(piece);
            default:
                return new MoveList();
        }
    }

    public void log(String m) {
        System.out.println(m);
    }

    private MoveList getPawnMoves(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("Piece cannot be null");
        }
        MoveList moves = new MoveList();
        int direction = piece.isWhite() ? 1 : -1;
        int startRank = piece.isWhite() ? 1 : 6;
        int promotionRank = piece.isWhite() ? 7 : 0;
        Square current = piece.getSquare();
        if (current == null) {
            throw new IllegalArgumentException("Piece square cannot be null");
        }
        Square oneForward = new Square(current.rank + direction, current.file);
        Square twoForward = new Square(current.rank + 2 * direction, current.file);
        if (oneForward.isValid() && board.getPieceAt(oneForward) == null) {
            moves.add(new Move(board, current, oneForward, piece, null));
            if (current.rank == startRank && twoForward.isValid() && board.getPieceAt(twoForward) == null) {
                moves.add(new Move(board, current, twoForward, piece, null));
            }
        }

        addPawnCaptureMove(moves, piece, current, direction, -1); // left capture
        addPawnCaptureMove(moves, piece, current, direction, 1); // right capture

        if (current.rank + direction == promotionRank) {
            addPawnPromotionMoves(moves, piece, current, oneForward);
        }

        return moves;
    }

    private void addPawnCaptureMove(MoveList moves, Piece piece, Square current, int direction, int fileOffset) {
        if (piece == null || current == null) {
            throw new IllegalArgumentException("Piece and current square cannot be null");
        }
        Square captureSquare = new Square(current.rank + direction, current.file + fileOffset);
        if (captureSquare.isValid() && board.getPieceAt(captureSquare) != null
                && board.getPieceAt(captureSquare).getColor().is(board.turn.opposite())) {
            moves.add(new Move(board, current, captureSquare, piece, board.getPieceAt(captureSquare)));
        }
    }

    private void addPawnPromotionMoves(MoveList moves, Piece piece, Square current, Square oneForward) {
        if (piece == null || current == null || oneForward == null) {
            throw new IllegalArgumentException("Piece, current square, and oneForward square cannot be null");
        }
        moves.add(new Move(board, current, oneForward, piece, null));
    }

    // Generates all possible moves for a knight piece
    private MoveList getKnightMoves(Piece piece) {
        MoveList moves = new MoveList();
        Square current = piece.getSquare();
        int[][] offsets = { { 2, 1 }, { 1, 2 }, { -1, 2 }, { -2, 1 }, { -2, -1 }, { -1, -2 }, { 1, -2 }, { 2, -1 } };

        if (piece == null || current == null) {
            throw new IllegalArgumentException("Piece and current square cannot be null");
        }

        for (int i = 0; i < 8; i++) {
            Square to = new Square(current.rank + offsets[i][0], current.file + offsets[i][1]);
            if (!to.isValid()) {
                continue;
            }
            Piece targetPiece = board.getPieceAt(to);
            if (targetPiece == null || targetPiece.getColor().is(board.turn.opposite())) {
                moves.add(new Move(board, current, to, piece, targetPiece));
            }
        }

        return moves;
    }

    private MoveList getDiagonalMoves(Square square) {
        MoveList moves = new MoveList();
        int[][] offsets = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 8; j++) {
                Square to = new Square(square.rank + j * offsets[i][0], square.file + j * offsets[i][1]);
                if (to.isValid()) {
                    if (board.getPieceAt(to) == null) {
                        moves.add(
                                new Move(board, square, to, board.getPieceAt(square), board.getPieceAt(to)));
                    } else if (board.getPieceAt(to).getColor().is(board.turn.opposite())) {
                        moves.add(
                                new Move(board, square, to, board.getPieceAt(square), board.getPieceAt(to)));
                        break;
                    } else {
                        break;
                    }
                } else
                    break;
            }
        }

        return moves;
    }

    private MoveList getSlidingMoves(Square square) {
        if (square == null) {
            throw new IllegalArgumentException("Square cannot be null");
        }
        MoveList moves = new MoveList();
        int[][] offsets = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 8; j++) {
                Square to = new Square(square.rank + j * offsets[i][0], square.file + j * offsets[i][1]);
                if (to.isValid()) {
                    Piece targetPiece = board.getPieceAt(to);
                    Piece movingPiece = board.getPieceAt(square);

                    if (movingPiece == null) {
                        throw new IllegalStateException("Moving piece at " + square + " is null in getSlidingMoves()");
                    }

                    if (targetPiece == null) {
                        moves.add(new Move(board, square, to, movingPiece, null)); // -1 for no captured piece
                    } else if (targetPiece.getColor().is(board.turn.opposite())) {
                        moves.add(new Move(board, square, to, movingPiece, targetPiece));
                        break; // Stop sliding after capturing
                    } else {
                        break; // Stop sliding when hitting own piece
                    }
                } else
                    break;
            }
        }

        return moves;
    }

    private MoveList getBishopMoves(Piece piece) {
        return getDiagonalMoves(piece.getSquare());
    }

    private MoveList getRookMoves(Piece piece) {
        return getSlidingMoves(piece.getSquare());
    }

    private MoveList getQueenMoves(Piece piece) {
        MoveList moves = getDiagonalMoves(piece.getSquare());
        moves.append(getSlidingMoves(piece.getSquare()));
        return moves;
    }

    private MoveList getKingMoves(Piece piece) {
        MoveList moves = new MoveList();
        Square current = piece.getSquare();
        int[][] offsets = { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { -1, 1 }, { -1, 0 }, { -1, -1 } };

        if (piece == null || current == null) {
            throw new IllegalArgumentException("Piece and current square cannot be null");
        }

        for (int i = 0; i < 8; i++) {
            Square to = new Square(current.rank + offsets[i][0], current.file + offsets[i][1]);
            if (!to.isValid()) {
                continue;
            }
            Piece targetPiece = board.getPieceAt(to);
            if (targetPiece == null || targetPiece.getColor().is(board.turn.opposite())) {
                moves.add(new Move(board, current, to, piece, targetPiece));
            }
        }

        return moves;
    }

    public boolean isPieceAttacked(Piece piece, Color attackerColor) {
        if (piece == null) {
            throw new IllegalArgumentException("Piece cannot be null");
        }
        return isSquareAttacked(piece.getSquare(), attackerColor);
    }

    public boolean pieceIsOfTypes(int[] types, Piece piece) {
        for (int i = 0; i < types.length; i++) {
            if (piece.getType() == types[i])
                return true;
        }
        return false;
    }

    public boolean attackedLinear(Square square, Color attackerColor) {
        int[][] offsets = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 8; j++) {
                Square to = new Square(square.rank + j * offsets[i][0], square.file + j * offsets[i][1]);
                if (to.isValid()) {
                    Piece targetPiece = board.getPieceAt(to);
                    if (targetPiece == null)
                        continue;
                    if (targetPiece.getColor().is(attackerColor)) {
                        if (pieceIsOfTypes(new int[] { Piece.ROOK, Piece.QUEEN }, targetPiece)) {
                            return true;
                        } else if (targetPiece.getType() == Piece.KING && j == 1) {
                            return true;
                        } else
                            break;
                    } else
                        break;
                } else
                    break;
            }
        }
        return false;
    }

    public boolean attackedDiagonal(Square square, Color attackerColor) {
        int[][] offsets = new int[][] { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 8; j++) {
                Square to = new Square(square.rank + j * offsets[i][0], square.file + j * offsets[i][1]);
                if (to.isValid()) {

                    Piece targetPiece = board.getPieceAt(to);
                    if (targetPiece == null)
                        continue;
                    if (targetPiece.getColor().is(attackerColor)) {
                        if (pieceIsOfTypes(new int[] { Piece.BISHOP, Piece.QUEEN }, targetPiece)) {
                            return true;
                        } else if (targetPiece.getType() == Piece.KING && j == 1) {
                            return true;
                        } else if (targetPiece.getType() == Piece.PAWN && j == 1 && attackerColor == (Color.WHITE)
                                ? (to.rank > square.rank)
                                : (to.rank < square.rank)) {
                            return true;
                        } else
                            break;
                    } else
                        break;
                } else
                    break;
            }
        }
        return false;
    }

    public boolean knightAttacked(Square square, Color attackerColor) {
        int[][] offsets = new int[][] { { 2, 1 }, { 1, 2 }, { -1, 2 }, { -2, 1 }, { -2, -1 }, { -1, -2 }, { 1, -2 },
                { 2, -1 } };

        for (int i = 0; i < 8; i++) {
            Square to = new Square(square.rank + offsets[i][0], square.file + offsets[i][1]);
            if (!to.isValid()) {
                continue;
            }
            Piece targetPiece = board.getPieceAt(to);
            if (targetPiece != null && targetPiece.getColor().is(attackerColor)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSquareAttacked(Square square, Color attackerColor) {
        // Don't run through all the moves just to find the attacker.
        // It's probably better to do a sort of reverse move generation

        // Check Horizontal/Vertical (king, queens, rooks)
        if (attackedLinear(square, attackerColor)) {
            return true;
        }
        // Check Diagonal (king, queens, pawns, bishops)
        if (attackedDiagonal(square, attackerColor)) {
            return true;
        }
        // Check knight
        if (knightAttacked(square, attackerColor)) {
            return true;
        }
        return false;
    }
}
