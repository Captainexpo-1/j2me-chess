
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class RetrospectMIDlet
        extends MIDlet
        implements CommandListener {

    private ChessGame mDisplay;

    public RetrospectMIDlet() {
        SimpleBot bot = new SimpleBot();

        mDisplay = new ChessGame(bot);

        mDisplay.addCommand(new Command("Exit", Command.EXIT, 0));
        mDisplay.setCommandListener(this);

        mDisplay.start();
    }

    public void startApp() {
        Display.getDisplay(this).setCurrent(mDisplay);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable s) {
        notifyDestroyed();
    }
}