public class Color {
    public static final Color WHITE = new Color(true);
    public static final Color BLACK = new Color(false);

    private boolean c;

    private Color(boolean col) {
        c = col;
    }

    public boolean is(Color col) {
        return c == col.c;
    }

    public boolean is(boolean col) {
        return c == col;
    }

    public Color opposite() {
        if (this.c == true) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    public String toString() {
        return c ? "White" : "Black";
    }
}
