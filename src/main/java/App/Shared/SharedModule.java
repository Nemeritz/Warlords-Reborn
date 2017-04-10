package App.Shared;

import App.Shared.JFX.JFXService;
import App.Shared.Settings.SettingsService;

/**
 * Created by Jerry Fan on 23/03/2017.
 */
public class SharedModule {
    private SettingsService settings;
    private JFXService jfx;

    /**
     * Constructor for a shared module, creates a new JFX service and Settings service
     */
    public SharedModule() {
        this.jfx = new JFXService();
        this.settings = new SettingsService();
    }

    /**
     * @return the JFX service in the shared module
     */
    public JFXService getJFX() {
        return this.jfx;
    }

    /**
     * @return the settings service in the shared module
     */
    public SettingsService getSettings() {
        return this.settings;
    }
}
