package App.Game.Fort.Shield;

import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import warlordstest.IPaddle;

import java.awt.*;

/**
 * Created by lichk on 30/03/2017.
 */
public class ShieldComponent implements IPaddle {
    private SharedModule shared;
    private GameService game;
    private Image image;
    private ShieldService model;

    public ShieldComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "barrier_shield.png"
        );
        this.model = new ShieldService();
    }

    public void update(Double intervalS) {
        Point.Double position = this.model.getPosition();
        Point velocity = this.model.getVelocity();
        position.setLocation(
                position.getX() + velocity.getX() * intervalS,
                position.getY() + velocity.getY() * intervalS
        );
    }

    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.model.getPosition();
        Point size = this.model.getSize();
        context.drawImage(this.image,
                position.getX() - size.getX() / 2,
                position.getY() - size.getY() / 2,
                size.getX(), size.getY()
        );
    }

    public Point.Double getPosition() {
        return this.model.getPosition();
    }

    public Point getVelocity() {
        return this.model.getVelocity();
    }

    public Point getSize() { return this.model.getSize(); }

    /***
     *  Set the horizontal position of the paddle to the given value.
     * @param x
     */
    public void setXPos(int x) {
        this.model.getPosition().x = x;
    };

    /***
     *  Set the vertical position of the paddle to the given value.
     * @param y
     */
    public void setYPos(int y) {
        this.model.getPosition().y = y;
    };
}
