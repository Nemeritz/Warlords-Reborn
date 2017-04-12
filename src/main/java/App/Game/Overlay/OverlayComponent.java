package App.Game.Overlay;

import App.Game.GameModule;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Acts as a controller for any of the game overlay components.
 * Created by Jerry Fan on 8/04/2017.
 */
public class OverlayComponent extends VBox {
    private SharedModule shared;
    private GameModule game;
    private OverlayService model;

    @FXML
    private Text largeText;

    @FXML
    private VBox largeTextWrapper;

    @FXML
    private VBox pauseMenu;

    @FXML
    private VBox gameEnd;

    public OverlayComponent(SharedModule shared, GameModule game) {
        this.shared = shared;
        this.game = game;

        this.shared.getJFX().loadFXML(this, this.getClass(),
                "OverlayComponent.fxml");
    }

    public void showLargeText() {
        this.largeTextWrapper.getStyleClass().removeIf((s) -> s.equals("hidden"));
    }

    public void setLargeText(String text) {
        this.largeText.setText(text);
    }

    public void hideLargeText() {
        if (this.largeTextWrapper.getStyleClass().filtered((s) -> s.equals("hidden")).size() == 0) {
            this.largeTextWrapper.getStyleClass().add("hidden");
        }
    }

    public void showPauseMenu() {
        this.pauseMenu.getStyleClass().remove(1);
    }

    public void hidePauseMenu() {
        this.pauseMenu.getStyleClass().add("hidden");
    }

    public void showGameEnd(String message) {

    }
}
