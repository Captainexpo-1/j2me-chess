import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class ChessGame extends Canvas implements Runnable {
    // ASCII display of the chess board

    Board board;

    public String moveInp = "";

    public ChessGame(Board b) {
        board = b;
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    private void paintBoard(Graphics g) {
        g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE));

        g.setColor(0, 0, 0);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(255, 255, 255);
        String[] strings = board.toStrings();
        for (int i = 0; i < strings.length; i++) {
            g.drawString(strings[i], 0, i * 10, Graphics.TOP | Graphics.LEFT);
        }
    }

    private void paintMoveInput(Graphics g) {
        g.setColor(255, 255, 255);
        g.drawString("Move: " + moveInp, 0, 80, Graphics.TOP | Graphics.LEFT);
    }

    private void paintMultiLineString(Graphics g, String s, int x, int y) {
        String[] lines = Utils.split(s, ' ');
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], x, y + i * 10, Graphics.TOP | Graphics.LEFT);
        }
    }

    private void paintMoveHistory(Graphics g) {
        g.setColor(255, 255, 255);
        g.drawString("Move history:", 0, 100, Graphics.TOP | Graphics.LEFT);
        String moves = board.moveHistory.toString();
        paintMultiLineString(g, moves, 0, 110);
    }

    protected void paint(Graphics g) {
        paintBoard(g);
        paintMoveInput(g);
        paintMoveHistory(g);
    }

    public void onEnterPressed() {
        if (moveInp.length() == 4) {
            Square from, to;
            try {
                from = new Square(moveInp.substring(0, 2), moveInp.charAt(0) >= 'a');
                to = new Square(moveInp.substring(2, 4), moveInp.charAt(2) >= 'a');
            } catch (IllegalArgumentException e) {
                return;
            }
            Move move = new Move(from, to, board.getPiece(from));
            if (board.isValidMove(move)) {
                board.movePiece(move);
                moveInp = "";
            }

        }
    }

    protected void keyPressed(int keyCode) {
        char key = (char) keyCode;
        System.out.println("Key pressed: " + keyCode);

        switch (keyCode) {
            case -5:
            case 10:
                // Enter
                onEnterPressed();
                break;
            case -8:
            case 8:
                // Backspace
                if (moveInp.length() > 0) {
                    moveInp = moveInp.substring(0, moveInp.length() - 1);
                }
                break;
            default:
                if (!(moveInp.length() >= 4)) {
                    moveInp += key;
                }
                break;
        }

    }

}
