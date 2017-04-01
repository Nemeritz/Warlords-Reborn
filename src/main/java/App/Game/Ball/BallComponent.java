package App.Game.Ball;

import App.Game.GameService;
import App.Game.Physics.Destructor;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import warlordstest.IBall;

import java.awt.*;

/**
 * Created by lichk on 26/03/2017.
 */
public class BallComponent implements IBall{
    private SharedModule shared;
    private GameService game;
    private Image image;
    private BallService model;

    public BallComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "BallComponent.png"
        );

        this.model = game.getBall();
    }

    public void updateObject(Double intervalS) {
        Point.Double position = this.game.getBall().getPosition();
        Point velocity = this.game.getBall().getVelocity();
        position.setLocation(
                position.getX() + velocity.getX() * intervalS,
                position.getY() + velocity.getY() * intervalS
        );
    }

    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.game.getBall().getPosition();
        Dimension size = this.game.getBall().getSize();
        context.drawImage(this.image,
                position.x,
                position.y,
                size.width, size.height
        );
    }

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
        this.velocity.x = dX;
    }

    /***
     *  Set the vertical velocity of the ball to the given value.
     * @param dY
     */
    public void setYVelocity(int dY) {
        this.velocity.y = dY;
    };

    /***
     * @return the horizontal velocity of the ball.
     */
    public int getXVelocity() {
        return ((int) this.velocity.getX());
    }

    /***
     * @return the vertical velocity of the ball.
     */
    public int getYVelocity() {
        return ((int) this.velocity.getY());
    }

}
