package App.Game.Canvas;

import javafx.scene.canvas.GraphicsContext;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Provides functions for drawing on a graphics context of a jfx canvas. Set as a service so that any component can
 * access this.
 * Created by Jerry Fan on 6/04/2017.
 */
public class CanvasService {
    private Set<CanvasObject> canvasObjects;
    private GraphicsContext context;

    public CanvasService() {
        this.canvasObjects = new CopyOnWriteArraySet<>();
    }

    /**
     * @return List of listeners to the canvas rendering loop.
     */
    public Set<CanvasObject> getCanvasObjects() {
        return canvasObjects;
    }

    /**
     * @param context Set the graphical context for rendering.
     */
    public void setContext(GraphicsContext context) {
        this.context = context;
    }

    /**
     * Go through each of the listeners and call their render functions.
     */
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
