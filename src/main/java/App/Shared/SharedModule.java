package App.Shared;

import App.Shared.JFX.JFXService;

/**
 * Created by lichk on 23/03/2017.
 */
public class SharedModule {
    JFXService jfx;

    /**
     * Constructor for a shared module, creates a new JFX service
     */
    public SharedModule() {
        this.jfx = new JFXService();
    }

    /**
     * @return the JFX service in the shared module
     */
    public JFXService getJFX() {
        return this.jfx;
    }
}
