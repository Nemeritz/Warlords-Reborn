package App.Game.Canvas.Ball;

import App.Game.GameModule;
import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by lichk on 26/03/2017.
 */
public class BallComponent {
    private SharedModule shared;
    private GameService game;
    private Image image;

    public BallComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "BallComponent.png"
        );
    }

    public void update(long intervalMs) {
        Point position = this.game.getBall().getPosition();
        Point velocity = this.game.getBall().getVelocity();
        position.translate(
                ((int) velocity.getX()),
                ((int) velocity.getY())
        );

    }

    public void renderOnContext(GraphicsContext context) {
        Point position = this.game.getBall().getPosition();
        context.drawImage(this.image, position.getX(), position.getY(), 20, 20);
    }

}
