package App.Shared;

import App.Shared.JFX.JFXService;

/**
 * Created by lichk on 23/03/2017.
 */
public class SharedModule {
    JFXService jfx;

    public SharedModule() {
        this.jfx = new JFXService();
    }

    public JFXService getJFX() {
        return this.jfx;
    }
}
