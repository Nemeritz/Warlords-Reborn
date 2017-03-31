package App.Game.Ball;

import App.Game.Canvas.CanvasObject;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.SharedModule;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import warlordstest.IBall;

import java.awt.*;

/**
 * Created by lichk on 26/03/2017.
 */
public class BallComponent implements IBall, Physical, CanvasObject {
    private SharedModule shared;
    private GameService game;
    private Image image;
    private BallService model;

    public BallComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.model = new BallService();
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "BallComponent.png"
        );
        this.game.getPhysics().getKinetics().add(this);
    }

    public void updateObject(Double intervalS) {
        Point.Double position = this.model.getPosition();
        Vec2d velocity = this.model.getVelocity();
        position.setLocation(
                position.x + velocity.x * intervalS,
                position.y + velocity.y * intervalS
        );
    }

    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.model.getPosition();
        Dimension size = this.model.getSize();
        context.drawImage(this.image,
                position.x,
                position.y,
                size.width, size.height
        );
    }

    @Override
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object) {
        Vec2d velocity = this.getVelocity();
        velocity.x = -velocity.x;
        velocity.y = -velocity.y;
    }

    public Point.Double getPosition() {
        return this.model.getPosition();
    }

    public Vec2d getVelocity() {
        return this.model.getVelocity();
    }

    public Dimension getSize() { return this.model.getSize(); }

    /***
     *  Set the horizontal position of the ball to the given value.
     * @param x
     */
    public void setXPos(int x) {
        this.model.getPosition().x = x;
    }

    /***
     * Set the vertical position of the ball to the given value.
     * @param y
     */
    public void setYPos(int y) {
        this.model.getPosition().y = y;
    }

    /***
     * @return the horizontal position of the ball.
     */
    public int getXPos() {
        return ((int) this.model.getPosition().x);
    }

    /***
     * @return the vertical position of the ball.
     */
    public int getYPos() {
        return ((int) this.model.getPosition().y);
    }

    /***
     *  Set the horizontal velocity of the ball to the given value.
     * @param dX
     */
    public void setXVelocity(int dX) {
        this.model.getVelocity().x = dX;
    }

    /***
     *  Set the vertical velocity of the ball to the given value.
     * @param dY
     */
    public void setYVelocity(int dY) {
        this.model.getVelocity().y = dY;
    };

    /***
     * @return the horizontal velocity of the ball.
     */
    public int getXVelocity() {
        return ((int) this.model.getVelocity().x);
    }

    /***
     * @return the vertical velocity of the ball.
     */
    public int getYVelocity() {
        return ((int) this.model.getVelocity().y);
    }

}
