package App.Game.Canvas;

import App.Game.Canvas.Ball.BallComponent;
import App.Game.GameModule;
import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.io.IOException;

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

    private BallComponent ball;

    public CanvasComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.ball = new BallComponent(this.shared, this.game);

        this.shared.getJFX().loadFXML(this, CanvasComponent.class,
                "CanvasComponent.fxml");
    }

    public void renderGameObjects() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        this.ball.renderOnContext(context);
    }
}
