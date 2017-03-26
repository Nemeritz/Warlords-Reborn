package App.Game.Canvas;

import App.Game.Canvas.Ball.BallComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by lichk on 24/03/2017.
 */
public class CanvasComponent extends Pane {
    private SharedModule shared;

    @FXML
    private Pane canvasWrapper;

    @FXML
    private Canvas canvas;

    private BallComponent ball;

    public CanvasComponent(SharedModule shared) {
        this.shared = shared;
        this.ball = new BallComponent(shared);

        this.shared.getJFX().loadFXML(this, CanvasComponent.class,
                "CanvasComponent.fxml");

        this.canvas.widthProperty().bind(this.canvasWrapper.widthProperty());
        this.canvas.heightProperty().bind(this.canvasWrapper.heightProperty());
    }

    public void renderGameObjects() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        this.ball.renderOnContext(context);
    }
}
