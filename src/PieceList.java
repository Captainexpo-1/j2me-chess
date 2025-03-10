
public class PieceList {
    private Piece[] pieces;
    public int pieceType;
    public Color color;
    private int size;

    public PieceList() {
        pieces = new Piece[10];
        size = 0;
    }

    public void add(Piece piece) {
        if (size == 0) {
            color = piece.color;
            pieceType = piece.type;
        } else {
            if (color != piece.color) {
                throw new IllegalArgumentException("PieceList must contain pieces of the same color");
            }
            if (pieceType != piece.type) {
                throw new IllegalArgumentException("PieceList must contain pieces of the same type");
            }
        }
        if (size == pieces.length) {
            Piece[] newPieces = new Piece[pieces.length * 2];
            for (int i = 0; i < size; i++) {
                newPieces[i] = pieces[i];
            }
            pieces = newPieces;
        }
        pieces[size++] = piece;
    }

    public Piece get(int index) {
        return pieces[index];
    }

    public int size() {
        return size;
    }

    public Piece remove(int index) {
        Piece piece = pieces[index];
        for (int i = index; i < size - 1; i++) {
            pieces[i] = pieces[i + 1];
        }
        size--;
        return piece;
    }

    public Piece remove(Piece piece) {
        for (int i = 0; i < size; i++) {
            if (pieces[i] == piece) {
                return remove(i);
            }
        }
        return null;
    }

    public String toString() {
        String str = "[";
        for (int i = 0; i < size; i++) {
            str += pieces[i].toLongString() + ",";
        }
        return str + "]";
    }
}
