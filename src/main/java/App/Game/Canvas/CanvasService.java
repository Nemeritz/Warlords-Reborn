package App.Game.Canvas;

import javafx.scene.canvas.GraphicsContext;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by lichk on 6/04/2017.
 */
public class CanvasService {
    private Set<CanvasObject> canvasObjects;
    private GraphicsContext context;

    public CanvasService() {
        this.canvasObjects = new CopyOnWriteArraySet<>();
    }

    public Set<CanvasObject> getCanvasObjects() {
        return canvasObjects;
    }

    public void setContext(GraphicsContext context) {
        this.context = context;
    }

    public void render() {
        if (this.context != null) {
            for (CanvasObject canvasObject : this.canvasObjects) {
                canvasObject.renderOnContext(this.context);
            }
        }
    }

    /**
     * Clears the canvas.
     */
    public void clear() {
        this.context.clearRect(0, 0,
                context.getCanvas().getWidth(),
                context.getCanvas().getHeight()
        );
    }
}
