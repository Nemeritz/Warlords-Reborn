package App.Game.Ball;

import App.Game.Canvas.CanvasObject;
import App.Game.GameModule;
import App.Game.Physics.Physical;
import App.Shared.Interfaces.Disposable;
import App.Shared.SharedModule;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import warlordstest.IBall;

import java.awt.*;

/**
 * Created by Jerry Fan on 26/03/2017.
 */
public class BallComponent implements IBall, Physical, CanvasObject, Disposable {
    private SharedModule shared;
    private GameModule game;
    private Image image;
    private BallService model;

    /**
     * Constructor for ball
     * @param shared access to JFX scenes and stages
     * @param game access to other services
     */
    public BallComponent(SharedModule shared, GameModule game) {
        this.shared = shared;
        this.game = game;
        this.model = new BallService();
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "assets/ball.png"
        );
        this.game.getCanvas().getCanvasObjects().add(this);
        this.game.getPhysics().getKinetics().add(this);
    }

    /**
     * updates the ball so it moves
     * @param intervalS time from last frame change
     */
    public void updateObject(Double intervalS) {
        Point.Double position = this.model.getPosition();
        Vec2d velocity = this.model.getVelocity();
        position.setLocation(
            position.x + velocity.x * intervalS,
            position.y + velocity.y * intervalS
        );
    }

    /**
     * @param player the player who last touched the ball
     */
    public void setLastDeflectedBy(int player) {
        this.model.lastDeflectedBy = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.model.getPosition();
        Dimension size = this.model.getSize();
        context.drawImage(this.image,
                position.x,
                position.y,
                size.width, size.height
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object) {
        Vec2d velocity = this.getVelocity();

        double reactionX = hitBoxCenter.x - intersectionCenter.x;
        double reactionY = hitBoxCenter.y - intersectionCenter.y;

        double signumX = Math.signum(Math.abs(reactionX) > 0.0001 ? reactionX : 0.0);
        double signumY = Math.signum(Math.abs(reactionY) > 0.0001 ? reactionY : 0.0);

        if (signumX != 0 && signumX != Math.signum(velocity.x)) {
            velocity.x = -velocity.x;
        }

        if (signumY != 0 && signumY != Math.signum(velocity.y)) {
            velocity.y = -velocity.y;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point.Double getPosition() {
        return this.model.getPosition();
    }


    /**
     * @return Gets the velocity of the ball.
     */
    public Vec2d getVelocity() {
        return this.model.getVelocity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getSize() { return this.model.getSize(); }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setXPos(int x) {
        this.model.getPosition().x = x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYPos(int y) {
        this.model.getPosition().y = y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getXPos() {
        return ((int) this.model.getPosition().x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getYPos() {
        return ((int) this.model.getPosition().y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setXVelocity(int dX) {
        this.model.getVelocity().x = dX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYVelocity(int dY) {
        this.model.getVelocity().y = dY;
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public int getXVelocity() {
        return ((int) this.model.getVelocity().x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getYVelocity() {
        return ((int) this.model.getVelocity().y);
    }

    public void dispose() {
        this.game.getCanvas().getCanvasObjects().remove(this);
        this.game.getPhysics().getKinetics().remove(this);
    }
}
