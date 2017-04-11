package App.Menu.Title;

import App.Menu.MenuComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

/**
 * Created by Jerry Fan on 21/03/2017.
 */
public class TitleComponent extends BorderPane {
    private SharedModule shared;
    private MenuComponent menu;
    private MediaPlayer buttonSound;

    @FXML
    private Text play;

    @FXML
    private Text settings;

    @FXML
    private Text exit;

    /**
     * Constructs the title component
     */
    private void construct() {
        this.shared.getJFX().loadFXML(this, TitleComponent.class,
                "TitleComponent.fxml");
        this.buttonSound = this.shared.getJFX().loadMedia(this.shared.getClass(), "Button.mp3");
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
    }

    /**
     * Default Constructor for the title component
     * @param shared
     * @param menu
     */
    public TitleComponent(SharedModule shared, MenuComponent menu) {
        this.shared = shared;
        this.menu = menu;
        this.construct();
    }

    public void playButtonSound() {
        this.buttonSound.stop();
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.buttonSound.play();
    }

    /**
     * transitions scene to match options when clicked
     */
    @FXML
    void onPlayClicked() {
        this.menu.transitionOptions();
        this.playButtonSound();
    }

    /**
     * exits the game when clicked, closing scenes
     */
    @FXML
    void onExitClicked() {
    	this.playButtonSound();
    	this.shared.getJFX().closeStage();
    }

    /**
     * transitions scene to the Game Settings menu when clicked
     */
    @FXML
    void onSettingsClicked() {
        this.menu.transitionSettings();
        this.playButtonSound();
    }
}
