package App.Game.Canvas;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Jerry Fan on 1/04/2017.
 */
public interface CanvasObject {
    /**
     * @param context the 2D graphics context for the game screen
     */
    public void renderOnContext(GraphicsContext context);
}
