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

    @FXML
    private Text largeText;

    @FXML
    private VBox largeTextWrapper;

    @FXML
    private VBox pauseWrapper;

    @FXML
    private Text gameEnd;

    @FXML
    private VBox gameEndWrapper;

    /**
     * @param shared The inherited SharedModule
     * @param game The inherited GameModule
     */
    public OverlayComponent(SharedModule shared, GameModule game) {
        this.shared = shared;
        this.game = game;

        this.shared.getJFX().loadFXML(this, this.getClass(),
                "OverlayComponent.fxml");
    }

    /**
     * Show the large text overlay.
     */
    public void showLargeText() {
        this.largeTextWrapper.getStyleClass().removeIf((s) -> s.equals("hidden"));
    }

    /**
      @param text Text to set for the large text overlay.
     */
    public void setLargeText(String text) {
        this.largeText.setText(text);
    }

    /**
     * Hide the large text overlay.
     */
    public void hideLargeText() {
        if (this.largeTextWrapper.getStyleClass().filtered((s) -> s.equals("hidden")).size() == 0) {
            this.largeTextWrapper.getStyleClass().add("hidden");
        }
    }

    /**
     * Show the pause menu overlay.
     */
    public void showPauseMenu() {
        this.pauseWrapper.getStyleClass().removeIf((s) -> s.equals("hidden"));
    }

    /**
     * Hide the pause menu overlay.
     */
    public void hidePauseMenu() {
        if (this.pauseWrapper.getStyleClass().filtered((s) -> s.equals("hidden")).size() == 0) {
            this.pauseWrapper.getStyleClass().add("hidden");
        }
    }

    /**
     * Show the game end overlay.
     */
    public void showGameEnd() {
        this.gameEndWrapper.getStyleClass().removeIf((s) -> s.equals("hidden"));
    }

    /**
     * @param message Set the game end message.
     */
    public void setGameEnd(String message) {
        this.gameEnd.setText(message);
    }

    /**
     * Hide the game end panel.
     */
    public void hideGameEnd() {
        if (this.gameEndWrapper.getStyleClass().filtered((s) -> s.equals("hidden")).size() == 0) {
            this.gameEndWrapper.getStyleClass().add("hidden");
        }
    }
}
