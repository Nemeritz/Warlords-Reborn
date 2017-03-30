package App.Game.Canvas.Fort.Shield;

import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import warlordstest.IPaddle;

import java.awt.*;

/**
 * Created by lichk on 30/03/2017.
 */
public class ShieldComponent {
    private SharedModule shared;
    private GameService game;
    private Image image;

    public ShieldComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "barrier_shield.png"
        );
    }

    public void update(Double intervalS) {
        Point.Double position = this.game.getShield().getPosition();
        Point velocity = this.game.getShield().getVelocity();
        position.setLocation(
                position.getX() + velocity.getX() * intervalS,
                position.getY() + velocity.getY() * intervalS
        );
    }

    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.game.getShield().getPosition();
        Point size = this.game.getShield().getSize();
        context.drawImage(this.image,
                position.getX() - size.getX() / 2,
                position.getY() - size.getY() / 2,
                size.getX(), size.getY()
        );
    }
}
