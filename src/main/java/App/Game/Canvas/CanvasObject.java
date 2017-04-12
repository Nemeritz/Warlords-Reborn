package App.Game.Canvas;

import javafx.scene.canvas.GraphicsContext;

/**
 * Implemented wherever a component needs to draw itself on the canvas.
 * Created by Jerry Fan on 1/04/2017.
 */
public interface CanvasObject {
    /**
     * @param context The method is called every render loop, after the canvas is cleared. The graphics context is
     *                provided such that the component can draw itself.
     */
    public void renderOnContext(GraphicsContext context);
}
