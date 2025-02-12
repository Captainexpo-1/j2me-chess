import java.util.Vector;

public class BoardStateList {
    private Vector boards; // Piece[][] list

    BoardStateList() {
        boards = new Vector(10, 5);
    }

    public void add(Board board) {
        boards.addElement(board.board);
    }

    public void add(Piece[][] board) {
        boards.addElement(board);
    }

    public Piece[][] get(int index) {
        return (Piece[][]) boards.elementAt(index);
    }

    public int size() {
        return boards.size();
    }

    public void clear() {
        boards.removeAllElements();
    }

    public Piece[][] pop() {
        Piece[][] b = get(boards.size() - 1);
        boards.removeElementAt(boards.size() - 1);
        return b;
    }
}
