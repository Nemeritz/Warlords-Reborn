package App.Menu.Title;

import App.Game.GameComponent;
import App.Menu.MenuComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.media.MediaPlayer;

/**
 * Created by Jerry Fan on 21/03/2017.
 */
public class TitleComponent extends BorderPane {
    private SharedModule shared;
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
     */
    public TitleComponent() {
        this.construct();
    }

    /**
     * Constructor
     * @param shared access to the JFX scenes and stages
     */
    public TitleComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }

    /**
     * transitions scene to match options when clicked
     */
    @FXML
    void onPlayClicked() {
        this.shared.getJFX().getMenu().transitionOptions();
        this.buttonSound.stop();
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.buttonSound.play();
    }

    /**
     * exits the game when clicked, closing scenes
     */
    @FXML
    void onExitClicked() {
        this.buttonSound.stop();
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.buttonSound.play();
    	this.shared.getJFX().closeStage();
    }

    /**
     * transitions scene to the Game Settings menu when clicked
     */
    @FXML
    void onSettingsClicked() {
        this.shared.getJFX().getMenu().transitionSettings();
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.buttonSound.stop();
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.buttonSound.play();
    }
}
