package App.Shared.JFX;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by lichk on 2/04/2017.
 */
public interface EventReceiver {
    public void onKeyEvent(KeyEvent event);
}
