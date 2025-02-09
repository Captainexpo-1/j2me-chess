import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

class PixilartCanvas extends Canvas implements Runnable {
    private boolean mRunning;
    private Thread mThread;

    private final int mWidth = 190;
    private final int mHeight = 190;

    public int[][] mPixels = null;

    public Vector mLogs = new Vector(10, 2);
    public Vector pixelChanges = new Vector(10, 2);
    public boolean isFirstPaint = true;

    public int mode = Mode.DRAW;

    public int lastXpos = 0;
    public int lastYpos = 0;

    public int xPos = 0;
    public int yPos = 0;

    public PixilartCanvas(int width, int height) {
        mPixels = new int[width][height];
    }

    public PixilartCanvas createCanvas(int width, int height) {
        return new PixilartCanvas(width, height);
    }

    public void start() {
        mRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    public void run() {
        while (mRunning) {
            repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        mRunning = false;
    }

    public int round(float value) {
        if (value - (int) value >= 0.5) {
            return (int) value + 1;
        } else {
            return (int) value;
        }
    }

    private Tuple getPixelPosition(int ix, int iy) {
        Integer x = new Integer(
                round((float) ix * (float) mWidth / (float) mPixels.length + (float) (getWidth() - mWidth) / 2));
        Integer y = new Integer(round((float) iy * (float) mHeight / (float) mPixels[0].length));
        return new Tuple(x, y);
    }

    private void drawPixel(Graphics g, int x, int y) {
        Tuple pixelPos = getPixelPosition(x, y);
        g.fillRect(((Integer) pixelPos.x).intValue(), ((Integer) pixelPos.y).intValue(),
                mWidth / mPixels.length,
                mHeight / mPixels[0].length);
    }

    private void drawPixilart(Graphics g) {
        if (isFirstPaint) {
            g.setColor(0x000000);
            g.fillRect((getWidth() - mWidth) / 2, 0, mWidth, mHeight);
            isFirstPaint = false;
            return;
        }

        while (!pixelChanges.isEmpty()) {
            Tuple pos = (Tuple) pixelChanges.firstElement();
            int x = ((Integer) pos.x).intValue();
            int y = ((Integer) pos.y).intValue();
            g.setColor(mPixels[x][y] & 0xFFFFFF);
            drawPixel(g, x, y);
            pixelChanges.removeElementAt(0);
        }

    }

    public int rgbFrom8Bit(byte color) {
        return (color << 16) | (color << 8) | color;
    }

    private void setPixel(int x, int y, int color) {
        if (x < 0 || x >= mPixels.length || y < 0 || y >= mPixels[0].length) {
            return;
        }
        mPixels[x][y] = color;
        pixelChanges.addElement(new Tuple(new Integer(x), new Integer(y)));
    }

    private void drawPointer(Graphics g) {
        g.setColor(0xFF0000);
        Tuple pixelPos = getPixelPosition(xPos, yPos);
        // Draw the pointer as a square in the middle of the pixel
        drawPixel(g, new Integer(xPos).intValue(), new Integer(yPos).intValue());
        g.setColor(mPixels[lastXpos][lastYpos] & 0xFFFFFF);
        drawPixel(g, new Integer(lastXpos).intValue(), new Integer(lastYpos).intValue());
    }

    protected void paint(Graphics arg0) {
        // Clear the screen
        arg0.setColor(0xFFFFFF);
        arg0.fillRect(0, mHeight, getWidth(), getHeight());
        if (mPixels == null) {
            return;
        }

        drawPixilart(arg0);
        drawPointer(arg0);
        // Draw the logs
        arg0.setColor(0x000000);
        if (mLogs.size() > 0) {
            arg0.drawString((String) mLogs.lastElement(), 25,
                    mHeight + 25, arg0.TOP | arg0.LEFT);
        }

    }

    private void doLogs(int keyCode) {
        mLogs.addElement("Key pressed: " + (char) keyCode);
        System.out.println("Key pressed: " + keyCode);
        System.out.println("xPos: " + xPos + ", yPos: " + yPos);
        for (int i = 0; i < pixelChanges.size(); i++) {
            Tuple pos = (Tuple) pixelChanges.elementAt(i);
            int x = ((Integer) pos.x).intValue();
            int y = ((Integer) pos.y).intValue();
            System.out.println("Pixel: " + x + ", " + y);
        }
    }

    protected void keyPressed(int keyCode) {
        super.keyPressed(keyCode);

        if (keyCode <= -1 && keyCode >= -5) {
            lastXpos = xPos;
            lastYpos = yPos;
        }

        switch (keyCode) {
            case -1: // Up
                yPos = (yPos - 1) % mPixels[0].length;
                if (yPos < 0) {
                    yPos = mPixels[0].length - 1;
                }
                break;
            case -2: // Down
                yPos = (yPos + 1) % mPixels[0].length;
                break;
            case -3: // Left
                xPos = (xPos - 1) % mPixels.length;
                if (xPos < 0) {
                    xPos = mPixels.length - 1;
                }
                break;
            case -4: // Right
                xPos = (xPos + 1) % mPixels.length;
                break;
            case -5: // Enter
                setPixel(xPos, yPos, 0xFFFFFF);
                break;

            default:
                break;
        }

        doLogs(keyCode);
    }

    protected void keyRepeated(int keyCode) {
        keyPressed(keyCode);
    }
}