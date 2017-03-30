package App.Game.Canvas.Ball;

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

    public void update(Double intervalS) {
        Point.Double position = this.game.getBall().getPosition();
        Point velocity = this.game.getBall().getVelocity();
        position.setLocation(
                position.getX() + velocity.getX() * intervalS,
                position.getY() + velocity.getY() * intervalS
        );
    }

    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.game.getBall().getPosition();
        Point size = this.game.getBall().getSize();
        context.drawImage(this.image,
                position.getX() - size.getX() / 2,
                position.getY() - size.getY() / 2,
                size.getX(), size.getY()
        );
    }

}
