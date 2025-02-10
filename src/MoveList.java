import java.util.Vector;

class MoveList {
    private Vector moves;

    public MoveList() {
        moves = new Vector(10, 10);
    }

    public void add(Move move) {
        moves.addElement(move);
    }

    public void append(MoveList moves) {
        for (int i = 0; i < moves.size(); i++) {
            add(moves.get(i));
        }
    }

    public Move get(int index) {
        return (Move) moves.elementAt(index);
    }

    public int size() {
        return moves.size();
    }

    public void clear() {
        moves.removeAllElements();
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < moves.size(); i++) {
            str += moves.elementAt(i) + " ";
        }
        return str;
    }

    public boolean contains(Move move) {
        for (int i = 0; i < moves.size(); i++) {
            if (get(i).equals(move)) {
                return true;
            }
        }
        return false;
    }

    public void remove(Move move) {
        moves.removeElement(move);
    }

    public void remove(int index) {
        moves.removeElementAt(index);
    }

    public MoveList clone() {
        MoveList clone = new MoveList();
        for (int i = 0; i < moves.size(); i++) {
            clone.add(get(i));
        }
        return clone;
    }
}