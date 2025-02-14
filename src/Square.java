public class Square {
    public int rank; // 0-7
    public int file; // 0-7

    public Square(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    public Square(String square, boolean allowLetters) {
        if (square.length() != 2) {
            throw new IllegalArgumentException("Invalid square: " + square);
        }
        char fileChar = square.charAt(0);
        char rankChar = square.charAt(1);

        if (allowLetters) {
            if (fileChar < 'a' || fileChar > 'h') {
                throw new IllegalArgumentException("Invalid file: " + fileChar);
            }
            this.file = fileChar - 'a';
        } else {
            if (fileChar < '1' || fileChar > '8') {
                throw new IllegalArgumentException("Invalid file: " + fileChar);
            }
            this.file = fileChar - '1';
        }

        if (rankChar < '1' || rankChar > '8') {
            throw new IllegalArgumentException("Invalid rank: " + rankChar);
        }
        this.rank = 7 - (rankChar - '1');
    }

    public String toString() {
        return (char) (file + 'a') + "" + (rank + 1);
    }

    public boolean equals(Object o) {
        if (o instanceof Square) {
            Square s = (Square) o;
            return s.rank == rank && s.file == file;
        }
        return false;
    }

    public int hashCode() {
        return rank * 8 + file;
    }
}
