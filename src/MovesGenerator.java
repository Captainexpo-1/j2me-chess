
public class MovesGenerator {
    public static MoveList validPieceMoves(Board board, Piece piece, Square from) {
        MoveList moves = new MoveList();
        switch (piece.type) {
            case Piece.PAWN:
                moves.append(validPawnMoves(board, piece, from));
                break;
            case Piece.KNIGHT:
                moves.append(validKnightMoves(board, piece, from));
                break;
            case Piece.BISHOP:
                moves.append(validBishopMoves(board, piece, from));
                break;
            case Piece.ROOK:
                moves.append(validRookMoves(board, piece, from));
                break;
            case Piece.QUEEN:
                moves.append(validQueenMoves(board, piece, from));
                break;
            case Piece.KING:
                moves.append(validKingMoves(board, piece, from));
                break;
        }
        return moves;
    }

    public static boolean isPieceOfColor(Board board, Square square, int color) {
        Piece piece = board.getPiece(square);
        return piece != null && piece.color == color;
    }

    public static MoveList validPawnMoves(Board board, Piece piece, Square from) {
        // TODO: en passant
        // TODO: Handle promotion
        MoveList moves = new MoveList();
        int color = piece.color;
        int rank = from.rank;
        int file = from.file;

        int forward = color == Piece.WHITE ? 1 : -1;
        int startRank = color == Piece.WHITE ? 2 : 7;

        if (rank + forward >= 1 && rank + forward <= 8) {
            if (board.getPiece(new Square(rank + forward, file)) == null) {
                moves.add(new Move(from, new Square(rank + forward, file), piece, -1));
            }
        }

        if (rank == startRank && board.getPiece(new Square(rank + forward, file)) == null
                && board.getPiece(new Square(rank + 2 * forward, file)) == null) {
            moves.add(new Move(from, new Square(rank + 2 * forward, file), piece, -1));
        }

        if (rank + forward >= 1 && rank + forward <= 8 && file - 1 >= 1) {
            if (isPieceOfColor(board, new Square(rank + forward, file - 1), 1 - color)) {
                moves.add(new Move(from, new Square(rank + forward, file - 1), piece,
                        board.getPiece(new Square(rank + forward, file - 1)).type));
            }
        }

        if (rank + forward >= 1 && rank + forward <= 8 && file + 1 <= 8) {
            if (isPieceOfColor(board, new Square(rank + forward, file + 1), 1 - color)) {
                moves.add(new Move(from, new Square(rank + forward, file + 1), piece,
                        board.getPiece(new Square(rank + forward, file + 1)).type));
            }
        }

        return moves;
    }

    public static MoveList validKnightMoves(Board board, Piece piece, Square from) {
        int[][] offsets = { { 1, 2 }, { 2, 1 }, { -1, 2 }, { -2, 1 }, { 1, -2 }, { 2, -1 }, { -1, -2 }, { -2, -1 } };
        MoveList moves = new MoveList();
        int color = piece.color;
        int rank = from.rank;

        for (int i = 0; i < offsets.length; i++) {
            int newRank = rank + offsets[i][0];
            int newFile = from.file + offsets[i][1];
            if (newRank >= 1 && newRank <= 8 && newFile >= 1 && newFile <= 8) {
                if (!isPieceOfColor(board, new Square(newRank, newFile), color)) {
                    moves.add(new Move(from, new Square(newRank, newFile), piece,
                            board.getPiece(new Square(newRank, newFile)).type));
                }
            }
        }

        return moves;
    }

    public static boolean isOutOfBounds(int rank, int file) {
        return rank < 1 || rank > 8 || file < 1 || file > 8;
    }

    public static MoveList validBishopMoves(Board board, Piece piece, Square from) {
        int[][] offsets = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };
        MoveList moves = new MoveList();

        int color = piece.color;
        int rank = from.rank;
        int file = from.file;

        for (int i = 0; i < offsets.length; i++) {
            int newRank = rank + offsets[i][0];
            int newFile = file + offsets[i][1];
            while (!isOutOfBounds(newRank, newFile)) {
                if (isPieceOfColor(board, new Square(newRank, newFile), color)) {
                    break;
                }
                moves.add(new Move(from, new Square(newRank, newFile), piece,
                        board.getPiece(new Square(newRank, newFile)).type));
                if (isPieceOfColor(board, new Square(newRank, newFile), 1 - color)) {
                    break;
                }
                newRank += offsets[i][0];
                newFile += offsets[i][1];
            }
        }

        return moves;

    }

    public static MoveList validRookMoves(Board board, Piece piece, Square from) {
        int[][] offsets = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        MoveList moves = new MoveList();

        int color = piece.color;
        int rank = from.rank;
        int file = from.file;

        for (int i = 0; i < offsets.length; i++) {
            int newRank = rank + offsets[i][0];
            int newFile = file + offsets[i][1];
            while (!isOutOfBounds(newRank, newFile)) {
                if (isPieceOfColor(board, new Square(newRank, newFile), color)) {
                    break;
                }
                moves.add(new Move(from, new Square(newRank, newFile), piece,
                        board.getPiece(new Square(newRank, newFile)).type));
                if (isPieceOfColor(board, new Square(newRank, newFile), 1 - color)) {
                    break;
                }
                newRank += offsets[i][0];
                newFile += offsets[i][1];
            }
        }

        return moves;
    }

    public static MoveList validQueenMoves(Board board, Piece piece, Square from) {
        MoveList moves = new MoveList();
        moves.append(validBishopMoves(board, piece, from));
        moves.append(validRookMoves(board, piece, from));
        return moves;
    }

    public static MoveList validKingMoves(Board board, Piece piece, Square from) {
        int[][] offsets = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };
        MoveList moves = new MoveList();

        int color = piece.color;
        int rank = from.rank;
        int file = from.file;

        for (int i = 0; i < offsets.length; i++) {
            int newRank = rank + offsets[i][0];
            int newFile = file + offsets[i][1];
            if (!isOutOfBounds(newRank, newFile) && !isPieceOfColor(board, new Square(newRank, newFile), color)) {
                moves.add(new Move(from, new Square(newRank, newFile), piece,
                        board.getPiece(new Square(newRank, newFile)).type));
            }
        }

        return moves;

    }

    public static MoveList validMoves(Board board, int color) {
        MoveList moves = new MoveList();
        for (int rank = 1; rank <= 8; rank++) {
            for (int file = 1; file <= 8; file++) {
                Square square = new Square(rank, file);
                Piece piece = board.getPiece(square);
                if (piece != null && piece.color == color) {
                    moves.append(validPieceMoves(board, piece, square));
                }
            }
        }
        moves = filterMovesThatCauseCheck(board, moves, color);
        return moves;
    }

    public static MoveList validMoves(Board board) {
        return validMoves(board, board.turn ? Piece.WHITE : Piece.BLACK);
    }

    private static MoveList filterMovesThatCauseCheck(Board board, MoveList moves, int color) {
        MoveList validMoves = new MoveList();
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            Board newBoard = board.clone();
            newBoard.makeMove(move);
            if (!isInCheck(newBoard, color)) {
                System.out.println("Valid move: " + move);
                validMoves.add(move);
            } else {
                System.out.println("Invalid move: " + move);
            }
        }
        return validMoves;
    }

    private static boolean isInCheck(Board board, int color) {
        Square kingSquare = findKing(board, color);
        return isSquareAttacked(board, kingSquare, 1 - color);
    }

    private static Square findKing(Board board, int color) {
        for (int rank = 1; rank <= 8; rank++) {
            for (int file = 1; file <= 8; file++) {
                Square square = new Square(rank, file);
                Piece piece = board.getPiece(square);
                if (piece != null && piece.type == Piece.KING && piece.color == color) {
                    return square;
                }
            }
        }
        return null;
    }

    private static boolean isSquareAttacked(Board board, Square square, int color) {
        MoveList opponentMoves = validMoves(board, color);
        for (int i = 0; i < opponentMoves.size(); i++) {
            Move move = opponentMoves.get(i);
            if (move.to.equals(square)) {
                return true;
            }
        }
        return false;
    }

}
