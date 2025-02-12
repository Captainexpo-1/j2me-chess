
public class SimpleBot implements ChessBot {

    public Move getMove(Board board) {
        return MovesGenerator.validMoves(board).get(0);
    }

}
