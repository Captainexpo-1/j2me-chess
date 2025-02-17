public class SimpleBot implements ChessBot {

    private static final float[] pieceValues = {
            10, // Pawn
            30, // Knight
            30, // Bishop
            50, // Rook
            90, // Queen
            0 // King
    };

    private static final float[][][] squareTables = new float[][][] {
            // Pawn
            {
                    { 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 50, 50, 50, 50, 50, 50, 50, 50 },
                    { 10, 10, 20, 30, 30, 20, 10, 10 },
                    { 5, 5, 10, 25, 25, 10, 5, 5 },
                    { 0, 0, 0, 20, 20, 0, 0, 0 },
                    { 5, -5, -10, 0, 0, -10, -5, 5 },
                    { 5, 10, 10, -20, -20, 10, 10, 5 },
                    { 0, 0, 0, 0, 0, 0, 0, 0 }
            },
            // Knight
            {
                    { -50, -40, -30, -30, -30, -30, -40, -50 },
                    { -40, -20, 0, 0, 0, 0, -20, -40 },
                    { -30, 0, 10, 15, 15, 10, 0, -30 },
                    { -30, 5, 15, 20, 20, 15, 5, -30 },
                    { -30, 0, 15, 20, 20, 15, 0, -30 },
                    { -30, 5, 10, 15, 15, 10, 5, -30 },
                    { -40, -20, 0, 5, 5, 0, -20, -40 },
                    { -50, -40, -30, -30, -30, -30, -40, -50 }
            },
            // Bishop
            {
                    { -20, -10, -10, -10, -10, -10, -10, -20 },
                    { -10, 0, 0, 0, 0, 0, 0, -10 },
                    { -10, 0, 5, 10, 10, 5, 0, -10 },
                    { -10, 5, 5, 10, 10, 5, 5, -10 },
                    { -10, 0, 10, 10, 10, 10, 0, -10 },
                    { -10, 10, 10, 10, 10, 10, 10, -10 },
                    { -10, 5, 0, 0, 0, 0, 5, -10 },
                    { -20, -10, -10, -10, -10, -10, -10, -20 }
            },
            // Rook
            {
                    { 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 5, 10, 10, 10, 10, 10, 10, 5 },
                    { -5, 0, 0, 0, 0, 0, 0, -5 },
                    { -5, 0, 0, 0, 0, 0, 0, -5 },
                    { -5, 0, 0, 0, 0, 0, 0, -5 },
                    { -5, 0, 0, 0, 0, 0, 0, -5 },
                    { -5, 0, 0, 0, 0, 0, 0, -5 },
                    { 0, 0, 0, 5, 5, 0, 0, 0 }
            },
            // Queen
            {
                    { -20, -10, -10, -5, -5, -10, -10, -20 },
                    { -10, 0, 0, 0, 0, 0, 0, -10 },
                    { -10, 0, 5, 5, 5, 5, 0, -10 },
                    { -5, 0, 5, 5, 5, 5, 0, -5 },
                    { 0, 0, 5, 5, 5, 5, 0, -5 },
                    { -10, 5, 5, 5, 5, 5, 0, -10 },
                    { -10, 0, 5, 0, 0, 0, 0, -10 },
                    { -20, -10, -10, -5, -5, -10, -10, -20 }
            },
            // King
            {
                    { -30, -40, -40, -50, -50, -40, -40, -30 },
                    { -30, -40, -40, -50, -50, -40, -40, -30 },
                    { -30, -40, -40, -50, -50, -40, -40, -30 },
                    { -30, -40, -40, -50, -50, -40, -40, -30 },
                    { -20, -30, -30, -40, -40, -30, -30, -20 },
                    { -10, -20, -20, -20, -20, -20, -20, -10 },
                    { 20, 20, 0, 0, 0, 0, 20, 20 },
                    { 20, 30, 10, 0, 0, 10, 30, 20 }
            }
    };

    public Move getMove(ChessBoard board) {
        MovesGenerator generator = new MovesGenerator(board);
        MoveList moves = generator.allValidMoves();
        Move bestMove = null;
        float bestValue = board.turn == Color.WHITE ? Float.MIN_VALUE : Float.MAX_VALUE;
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            board.makeMove(move);
            float value = minimax(board, 2, Float.MIN_VALUE, Float.MAX_VALUE, board.turn != Color.WHITE);
            board.undoMove();
            if ((board.turn == Color.WHITE && value > bestValue)
                    || (board.turn == Color.BLACK && value < bestValue)) {
                bestValue = value;
                bestMove = move;
            }
        }
        System.out.println("Best move: " + bestMove + " with value " + bestValue);
        return bestMove;
    }

    private float minimax(ChessBoard board, int depth, float alpha, float beta, boolean maximizingPlayer) {
        MovesGenerator generator = new MovesGenerator(board);
        MoveList moves = generator.allValidMoves();
        if (depth == 0 || moves.size() == 0) {
            return getEvaluation(board);
        }
        if (maximizingPlayer) {
            float value = Float.MIN_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                Move move = moves.get(i);
                board.makeMove(move);
                value = Math.max(value, minimax(board, depth - 1, alpha, beta, false));
                board.undoMove();
                if (value > beta)
                    break;
                alpha = Math.max(alpha, value);
            }
            return value;
        } else {
            float value = Float.MAX_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                Move move = moves.get(i);
                board.makeMove(move);
                value = Math.min(value, minimax(board, depth - 1, alpha, beta, true));
                board.undoMove();
                if (value < alpha)
                    break;
                beta = Math.min(beta, value);
            }
            return value;
        }
    }

    private float pieceMaterialEvaluation(ChessBoard board) {
        float eval = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPieceAt(i, j);
                if (piece != null) {
                    if (piece.color == Color.WHITE) {
                        eval += pieceValues[piece.type];
                    } else {
                        eval -= pieceValues[piece.type];
                    }
                }
            }
        }
        return eval;
    }

    private float pieceSquareEvaluation(ChessBoard board) {
        float white = 0;
        float black = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPieceAt(i, j);
                if (piece != null) {
                    if (piece.color == Color.WHITE) {
                        white += squareTables[piece.type][7 - i][j];
                    } else {
                        black += squareTables[piece.type][i][j];
                    }
                }
            }
        }
        return white - black;
    }

    private float getEvaluation(ChessBoard board) {
        float material = pieceMaterialEvaluation(board);
        float pieceSquare = pieceSquareEvaluation(board);
        return material * 10 + pieceSquare / 100;
    }
}
