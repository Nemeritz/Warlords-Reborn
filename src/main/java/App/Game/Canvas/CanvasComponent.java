package App.Game.Canvas;

import App.Game.Canvas.Ball.BallComponent;
import App.Game.Canvas.Fort.FortComponent;
import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.util.TreeMap;

/**
 * Created by lichk on 24/03/2017.
 */
public class CanvasComponent extends Pane {
    private SharedModule shared;
    private GameService game;

    @FXML
    private Pane canvasWrapper;

    @FXML
    private Canvas canvas;


    public CanvasComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.shared.getJFX().loadFXML(this, CanvasComponent.class,
                "CanvasComponent.fxml");
    }

    public void updateObjects(Double intervalS) {
        this.ball.updateObject(intervalS);
        this.fort.updateObject(intervalS);
    }

    public void renderObjects() {
        // Retrieve the context and clear it for redraw.
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
