package App.Game.Canvas;

import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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

    private void construct() {
        this.shared.getJFX().loadFXML(this, CanvasComponent.class,
                "CanvasComponent.fxml");

        this.canvas.widthProperty().bind(this.canvasWrapper.widthProperty());
        this.canvas.heightProperty().bind(this.canvasWrapper.heightProperty());
    }

    public CanvasComponent() {
        this.construct();
    }

    public CanvasComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }
}
