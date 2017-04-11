package App.Menu;

import App.Menu.GameSettings.GameSettingsComponent;
import App.Menu.ModeSelection.ModeSelectionComponent;
import App.Menu.MatchOptions.MatchOptionsComponent;
import App.Menu.Title.TitleComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;

/**
 * Created by Jerry Fan on 25/03/2017.
 */
public class MenuComponent extends Pane {
    private SharedModule shared;//allows access to JFX scenes and stage

    private MediaPlayer music;

    @FXML
    private Pane menuWrapper;

    private TitleComponent title;

    private GameSettingsComponent settings;

    private MatchOptionsComponent options;

    private ModeSelectionComponent mode;

    private int currentMenu;

//    private HighscoresComponent highscores;

    /**
     * Constructs the menu component with title components as default overlaying it
     */
    private void construct() {
        // Initial Menu music
        this.music = this.shared.getJFX().loadMedia(this.getClass(), "assets/MenuMusic.mp3");
        this.music.setVolume(this.shared.getSettings().musicVolume);
        this.music.setCycleCount(MediaPlayer.INDEFINITE);
        this.music.play();

        this.title = new TitleComponent(shared, this);
        this.settings = new GameSettingsComponent(shared, this);
        this.options = new MatchOptionsComponent(shared, this);
        this.mode = new ModeSelectionComponent(shared, this);
 //      this.highscores = new HighscoresComponent(shared);

        this.shared.getJFX().loadFXML(this, MenuComponent.class,
                "MenuComponent.fxml");
        // Load the title by default
        this.transitionTitle();

        this.currentMenu = 0;
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
        this.title.resetCurrentButton();
        this.currentMenu = 0;
    }

    /**
     * removes existing components in the menu and add in the settings menu component
     */
    public void transitionSettings() {
        this.menuWrapper.getChildren().clear();
        this.menuWrapper.getChildren().removeAll();
        this.menuWrapper.getChildren().add(this.settings);
        this.settings.resetCurrentButton();
        this.currentMenu = 1;
    }

    /**
     * removes existing components in the menu and add in the options menu component
     */
    public void transitionOptions() {
        this.menuWrapper.getChildren().clear();
        this.menuWrapper.getChildren().removeAll();
        this.menuWrapper.getChildren().add(this.options);
        this.options.resetCurrentButton();
        this.currentMenu = 2;
    }

    /**
     * removes existing components in the menu and add in the mode selection menu component
     */
    public void transitionMode() {
        this.menuWrapper.getChildren().clear();
        this.menuWrapper.getChildren().removeAll();
        this.menuWrapper.getChildren().add(this.mode);
        this.mode.resetCurrentButton();
        this.currentMenu = 3;
    }

    public int getCurrentMenu() {
    	return this.currentMenu;
    }

    public MediaPlayer getMusic() {
        return this.music;
    }
}
