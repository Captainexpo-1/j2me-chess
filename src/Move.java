public class Move {
    Square from;
    Square to;
    Piece piece;

    public Move(Square from, Square to, Piece piece) {
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    public String toString() {
        return from + "-" + to;
    }

    public boolean equals(Object o) {
        if (o instanceof Move) {
            Move m = (Move) o;
            return m.from.equals(from) && m.to.equals(to);
        }
        return false;
    }
}
