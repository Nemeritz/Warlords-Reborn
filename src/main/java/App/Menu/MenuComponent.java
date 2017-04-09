package App.Menu;

import App.Menu.Title.TitleComponent;
import App.Menu.GameSettings.GameSettingsComponent;
import App.Menu.MatchOptions.MatchOptionsComponent;
import App.Shared.SharedModule;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Jerry Fan on 25/03/2017.
 */
public class MenuComponent extends Pane {
    private SharedModule shared;//allows access to JFX scenes and stage

    @FXML
    private Pane menuWrapper;

    private TitleComponent title;

    private GameSettingsComponent settings;

    private MatchOptionsComponent options;

    public MediaPlayer menuMusic;

    /**
     * Constructs the menu component with title components as default overlaying it
     */
    private void construct() {
        this.title = new TitleComponent(shared);
        this.settings = new GameSettingsComponent(shared);
        this.options = new MatchOptionsComponent(shared);

        this.shared.getJFX().loadFXML(this, MenuComponent.class,
                "MenuComponent.fxml");


        // Initial Menu music
        this.menuMusic = this.shared.getJFX().loadMedia(this.shared.getClass(), "MenuMusic.mp3");
        this.menuMusic.setVolume(this.shared.getSettings().musicVolume);
        this.menuMusic.setCycleCount(MediaPlayer.INDEFINITE);
        this.menuMusic.play();

        // Load the title by default
        this.transitionTitle();
    }

    /**
     * Default constructor
     */
    public MenuComponent() {
        this.construct();
    }

    /**
     * Constructor
     * @param shared allows access to the current JFX scenes and stage
     */
    public MenuComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }

    /**
     * removes existing components in the menu and add in the title menu component
     */
    public void transitionTitle() {
        this.menuWrapper.getChildren().clear();
        this.menuWrapper.getChildren().removeAll();
        this.menuWrapper.getChildren().add(this.title);
    }

    public void transitionSettings() {
        this.menuWrapper.getChildren().clear();
        this.menuWrapper.getChildren().removeAll();
        this.menuWrapper.getChildren().add(this.settings);
    }

    public void transitionOptions() {
        this.menuWrapper.getChildren().clear();
        this.menuWrapper.getChildren().removeAll();
        this.menuWrapper.getChildren().add(this.options);
    }
}
