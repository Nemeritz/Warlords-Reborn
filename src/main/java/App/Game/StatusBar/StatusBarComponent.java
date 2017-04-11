package App.Game.StatusBar;

import App.Game.GameModule;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by lichk on 12/04/2017.
 */
public class StatusBarComponent extends HBox {

    SharedModule shared;
    GameModule game;

    @FXML
    private Text statusText;

    public StatusBarComponent(SharedModule shared, GameModule game) {
        this.shared = shared;
        this.game = game;
        this.shared.getJFX().loadFXML(this, this.getClass(),
                "StatusBarComponent.fxml");
    }

    public void setStatusText(String value) {
        if (this.statusText != null) {
            this.statusText.setText(value);
        }
    }
}
