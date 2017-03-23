package App.Shared;

import App.Shared.JFX.JFXService;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by lichk on 23/03/2017.
 */
public class SharedComponent {
    JFXService jfx;

    public SharedComponent() {
        this.jfx = new JFXService();
    }

    public JFXService getJFX() {
        return this.jfx;
    }
}
