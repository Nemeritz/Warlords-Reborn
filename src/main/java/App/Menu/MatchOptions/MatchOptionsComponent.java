package App.Menu.MatchOptions;

import App.Game.GameComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * Created by Hanliang Ding on 09/04/2017.
 */
public class MatchOptionsComponent extends BorderPane {
    private SharedModule shared;
    private MediaPlayer buttonSound;

    @FXML
    private Text back;

    @FXML
    private Text cont;

    /**
     * Constructs the Match options component
     */
    private void construct() {
        this.shared.getJFX().loadFXML(this, MatchOptionsComponent.class,
                "MatchOptionsComponent.fxml");

        this.buttonSound = this.shared.getJFX().loadMedia(this.shared.getClass(), "Button.mp3");
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
    }

    /**
     * Default Constructor for the title component
     */
    public MatchOptionsComponent() {
        this.construct();
    }

    /**
     * Constructor
     * @param shared access to the JFX scenes and stages
     */
    public MatchOptionsComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }

    /**
     * transition scene back to main
     */
    @FXML
    void onBackClicked() {
    	this.shared.getJFX().getMenu().transitionTitle();
        this.buttonSound.stop();
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.buttonSound.play();
    }

    /**
     * transition scene to game
     */
    @FXML
    void onContinueClicked() {
    	this.shared.getJFX().setScene("game");
        ((GameComponent) this.shared.getJFX().getScene("game").getKey())
        .startGameCountdown();
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.buttonSound.stop();
        this.buttonSound.play();
//        this.shared.getJFX().loadMusic(this.shared.getClass(), "MenuMusicBackup.mp3");
    }
}
