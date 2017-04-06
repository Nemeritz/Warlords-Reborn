package App.Game.Canvas;

import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

/**
 * Created by Jerry Fan on 24/03/2017.
 */
public class CanvasComponent extends Pane {
    private SharedModule shared;
    private GameService game;

    @FXML
    private Pane canvasWrapper;

    @FXML
    private Canvas canvas;

    /**
     * @param shared contains the JFX scenes
     * @param game contains the current game and allows access to all other services
     */
    public CanvasComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.shared.getJFX().loadFXML(this, CanvasComponent.class,
                "CanvasComponent.fxml");
        if (this.shared.getJFX().active) {
            this.canvas.heightProperty().bind(this.canvasWrapper.heightProperty());
            this.canvas.widthProperty().bind(this.canvasWrapper.widthProperty());
        }
    }

    /**
     * @return The graphics context of this canvas.
     */
    public GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }
}
