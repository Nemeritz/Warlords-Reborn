package App.Menu.Title;

import App.Game.GameComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 * Created by Jerry Fan on 21/03/2017.
 */
public class TitleComponent extends BorderPane {
    private SharedModule shared;

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
     * transitions scene to game scene when clicked
     */
    @FXML
    void onPlayClicked() {

        this.shared.getJFX().setScene("game");
        ((GameComponent) this.shared.getJFX().getScene("game").getKey())
                .startGameCountdown();
    }

    /**
     * exits the game when cliced, closing scenes
     */
    @FXML
    void onExitClicked() {
    	this.shared.getJFX().closeStage();
    }

    /**
     * transitions scene to the Game Settings menu when clicked
     */
    @FXML
    void onSettingsClicked() {
        this.shared.getJFX().setScene("settings");
    }
}
