
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class RetrospectMIDlet
        extends MIDlet
        implements CommandListener {
    private PixilartCanvas mCanvas = new PixilartCanvas(32, 32);

    public RetrospectMIDlet() {
        mCanvas.setCommandListener(this);
        mCanvas.addCommand(new Command("Exit", Command.EXIT, 0));

    }

    public void startApp() {
        Display.getDisplay(this).setCurrent(mCanvas);

        mCanvas.start();
        mCanvas.run();

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable s) {
        notifyDestroyed();
    }
}