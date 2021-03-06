package App.Shared.JFX;

import javafx.scene.input.KeyEvent;

/**
 * Interface for event recievers, currently covers key events.
 * Created by Jerry Fan on 2/04/2017.
 */
public interface EventReceiver {

    /**
     * This method is called when a key is pressed while the focused window is the one containing the active
     * application stage. Any sort of key event handling should be done here.
     * @param event The event generated by the keypress.
     */
    public void onKeyEvent(KeyEvent event);
}
