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
    private Text countdown;

    @FXML
    private VBox countdownWrapper;

    @FXML
    private VBox pauseMenu;

    public OverlayComponent(SharedModule shared, GameModule game) {
        this.shared = shared;
        this.game = game;

        this.shared.getJFX().loadFXML(this, OverlayComponent.class,
                "OverlayComponent.fxml");
    }

    public void showCountdown() {
        this.countdownWrapper.getStyleClass().remove(1);
    }

    public void setCountdown(long timeS) {
        if (!Long.toString(timeS).equals(this.countdown.getText())) {
            this.countdown.setText(Long.toString(timeS));
        }
    }

    public void hideCountdown() {
        this.countdownWrapper.getStyleClass().add("hidden");
    }

    public void showPauseMenu() {
        this.pauseMenu.getStyleClass().remove(1);
    }

    public void hidePauseMenu() {
        this.pauseMenu.getStyleClass().add("hidden");
    }
}
