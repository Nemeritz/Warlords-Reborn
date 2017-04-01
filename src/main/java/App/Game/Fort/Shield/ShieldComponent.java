package App.Game.Fort.Shield;

import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasObject;
import App.Game.Fort.FortService;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.JFX.EventReceiver;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import warlordstest.IPaddle;

import java.awt.*;

/**
 * Created by Jerry Fan on 30/03/2017.
 */
public class ShieldComponent implements IPaddle, Physical, CanvasObject, EventReceiver {
    private SharedModule shared;
    private GameService game;
    private FortService fort;
    private Image image;
    private ShieldService model;
    private boolean leftIsPressed; // indicates if left arrow is pressed
    private boolean rightIsPressed; // indicates if right arrow is pressed

    /**
     * Constructor for shield
     * @param shared shared module controlling all scenes
     * @param game current game containing all services
     */
    public ShieldComponent(SharedModule shared, GameService game, FortService fort) {
        this.leftIsPressed = false; // shield stays still at the start
        this.rightIsPressed = false;
        this.shared = shared; // allows access to JFX current scene for adding event handlers
        this.game = game; // allows access to other services in the game
        this.fort = fort; // Allows access to fort services.
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "barrier_shield.png"
        );
        this.model = new ShieldService(); // accessing velocity, dimensions and locations of shield
        this.game.getPhysics().getStatics().add(this);
        this.shared.getJFX().addEventReceiver(this);
    }

    /**
     * @param intervalS time difference between the frames, so changes in FPS would not affect velocity of shield
     *                  function changes shield's location based on left or right arrow keys pressed
     */
    public void update(Double intervalS) {
        Point.Double position = this.model.getPosition();
        Point velocity = this.model.getVelocity();

        if (this.leftIsPressed) {
            position.setLocation(position.x - velocity.x * intervalS, position.y); // moves slider to the left
        }
        else if (this.rightIsPressed) {
            position.setLocation(position.x + velocity.x * intervalS, position.y); // moves slider to the right
        }
        else {
            position.setLocation(position.x, position.y); // no keys pressed so stays still
        }
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
        if (BallComponent.class.isInstance(object)) {
            BallComponent ball = (BallComponent) object;
            ball.setLastDeflectedBy(fort.player);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            leftIsPressed = true; // If left arrow key is pressed
        }
        else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            leftIsPressed = false; // If left arrow key is released
        }
        else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            rightIsPressed = true; // If right arrow key is pressed
        }
        else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            rightIsPressed = false; // If right arrow key is released
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
     * @return velocity of the shield containing x velocity and y velocity
     */
    public Point getVelocity() {
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
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYPos(int y) {
        this.model.getPosition().y = y;
    };
}
