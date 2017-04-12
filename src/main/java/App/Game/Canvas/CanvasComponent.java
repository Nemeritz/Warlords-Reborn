package App.Game.Canvas;

import App.Game.GameModule;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.util.Observable;
import java.util.Observer;

/**
 * Controller for the jfx canvas object
 * Created by Jerry Fan on 24/03/2017.
 */
public class CanvasComponent extends Pane implements Observer {
    private SharedModule shared;
    private GameModule game;

    @FXML
    private Pane canvasWrapper;

    @FXML
    private Canvas canvas;

    /**
     * @param shared contains the JFX scenes
     * @param game contains the current game and allows access to all other services
     */
    public CanvasComponent(SharedModule shared, GameModule game) {
        this.shared = shared;
        this.game = game;
        this.shared.getJFX().loadFXML(this, CanvasComponent.class,
                "CanvasComponent.fxml");
        if (this.canvas != null) {
            this.canvas.heightProperty().bind(this.canvasWrapper.heightProperty());
            this.canvas.widthProperty().bind(this.canvasWrapper.widthProperty());
        }
        this.game.getTimer().getFrame().addObserver(this);
    }

    /**
     * @return The graphics context of this canvas.
     */
    public GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }

    /**
     * @return Check if the canvas object exists.
     */
    public boolean hasJFXCanvas() {
        return this.canvas != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Observable obs, Object obj) {
        if (obs == this.game.getTimer().getFrame()) {
            this.game.getCanvas().clear();
            this.game.getCanvas().render();
        }
    }
}
