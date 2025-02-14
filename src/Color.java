public class Color {
    public static final Color WHITE = new Color(false);
    public static final Color BLACK = new Color(true);

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
}
