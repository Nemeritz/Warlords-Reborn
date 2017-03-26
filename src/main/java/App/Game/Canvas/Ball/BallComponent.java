package App.Game.Canvas.Ball;

import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by lichk on 26/03/2017.
 */
public class BallComponent {
    private SharedModule shared;
    private Image image;

    public BallComponent(SharedModule shared) {
        this.shared = shared;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "BallComponent.png"
        );
    }

    public void renderOnContext(GraphicsContext context) {
        context.drawImage(this.image, 10, 10, 20, 20);
    }

}
