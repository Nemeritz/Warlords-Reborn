package App.Game.Fort.Shield;

import App.Game.Canvas.CanvasObject;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import warlordstest.IPaddle;
import javafx.scene.Node;

import java.awt.*;
import javafx.event.*;

import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * Created by lichk on 30/03/2017.
 */
public class ShieldComponent implements IPaddle, Physical, CanvasObject {
    private SharedModule shared;
    private GameService game;
    private Image image;
    private ShieldService model;
    private boolean leftIsPressed; // indicates if left arrow is pressed
    private boolean rightIsPressed; // indicates if right arrow is pressed

    /**
     * Constructor for shield
     * @param shared shared module controlling all scenes
     * @param game current game containing all services
     */
    public ShieldComponent(SharedModule shared, GameService game) {
        this.leftIsPressed = false; // shield stays still at the start
        this.rightIsPressed = false;
        this.shared = shared; // allows access to JFX current scene for adding event handlers
        this.game = game; // allows access to other services in the game
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "barrier_shield.png"
        );
        this.model = new ShieldService(); // accessing velocity, dimensions and locations of shield
        this.game.getPhysics().getStatics().add(this);
    }

    /**
     * @param intervalS time difference between the frames, so changes in FPS would not affect velocity of shield
     *                  function changes shield's location based on left or right arrow keys pressed
     */
    public void update(Double intervalS) {
        Point.Double position = this.model.getPosition();
        Point velocity = this.model.getVelocity();

//        EventHandler<KeyEvent> keyEvent = new EventHandler<KeyEvent>(){
//            @Override
//            public void handle(KeyEvent event) {//when listener on Key triggers event
//                if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {
//                    leftIsPressed = true;//if left arrow key is pressed
//                }
//                else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_RELEASED) {
//                    leftIsPressed = false;//if left arrow key is released
//                }
//                else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_PRESSED) {
//                    rightIsPressed = true;//if right arrow key is pressed
//                }
//                else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_RELEASED) {
//                    rightIsPressed = false;//if right arrow key is released
//                }
//            }
//        };
//        if (this.leftIsPressed) {
//            position.setLocation(position.getX() - velocity.getX() * intervalS, position.getY());//moves slider to the left
//        }
//        else if (this.rightIsPressed) {
//            position.setLocation(position.getX() + velocity.getX() * intervalS, position.getY());//moves slider to the right
//        }
//        else {
//            position.setLocation(position.getX(), position.getY()); // no keys pressed so stays still
//        }
//        this.shared.getJFX().getScene("game").getValue()
//                .addEventHandler(KeyEvent.KEY_PRESSED,keyEvent); // adds event handler to the game scene on keys pressed
//        this.shared.getJFX().getScene("game").getValue()
//                .addEventHandler(KeyEvent.KEY_RELEASED,keyEvent); // adds event handler to the game scene on keys
        // released
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object) {

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