package App.Game.Fort.Wall;

import App.Game.GameService;
import App.Shared.SharedModule;
import App.Game.Canvas.Ball.BallService;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;


/**
 * Created by pie on 28/03/17.
 */
public class WallComponent {
    private SharedModule shared;
    private Image image;
    private GameService game;
    private WallService model;


    public WallComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "WallComponent.png"
        );
    }

    public void update(Double intervalS) {

    }

    public void checkCollisionWall(BallService ball) {

    }

    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.model.getPosition();
        Point size = this.model.getSize();
        if (!this.model.isDestroyed()) {
            context.drawImage(this.image,
                    position.getX() - size.getX() / 2,
                    position.getY() - size.getY() / 2,
                    size.getX(), size.getY()
            );
        }
    }
}
