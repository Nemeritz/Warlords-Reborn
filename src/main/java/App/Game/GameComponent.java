package App.Game;

import App.Game.Canvas.CanvasComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Created by lichk on 21/03/2017.
 */
public class GameComponent extends BorderPane {
    private SharedModule shared;
    private CanvasComponent canvas;

    private void construct() {
        this.canvas = new CanvasComponent(shared);
        this.shared.getJFX().loadFXML(this, GameComponent.class,
                "GameComponent.fxml");
    }

    public GameComponent() {
        this.construct();
    }

    public GameComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }
}
