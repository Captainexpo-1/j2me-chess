
public class SimpleBot implements ChessBot {
    MovesGenerator generator = new MovesGenerator(null);

    public Move getMove(ChessBoard board) {
        generator.setBoard(board);
        MoveList moves = generator.allValidMoves();
        System.out.println("BOT MOVES: " + moves.toString());
        return moves.random();
    }

}
