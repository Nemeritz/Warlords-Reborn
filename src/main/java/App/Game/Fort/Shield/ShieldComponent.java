package App.Game.Fort.Shield;

import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasObject;
import App.Game.Fort.FortService;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.JFX.EventReceiver;
import App.Shared.SharedModule;
import com.sun.javafx.geom.Vec2d;
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
    private boolean leftPressed; // indicates if left arrow is pressed
    private boolean rightPressed; // indicates if right arrow is pressed

    private void setStyle() {
        if (this.fort.player > 0 && this.fort.player <= 4) {
            this.image = this.shared.getJFX().loadImage(
                    this.getClass(), "assets/shield-" + Integer.toString(this.fort.player) + ".png"
            );
        }
    }

    /**
     * Constructor for shield
     * @param shared shared module controlling all scenes
     * @param game current game containing all services
     */
    public ShieldComponent(SharedModule shared, GameService game, FortService fort) {
        this.leftPressed = false; // shield stays still at the start
        this.rightPressed = false;
        this.shared = shared; // allows access to JFX current scene for adding event handlers
        this.game = game; // allows access to other services in the game
        this.fort = fort; // Allows access to fort services.
        this.setStyle();
        this.model = new ShieldService(); // accessing velocity, dimensions and locations of shield
        this.game.getCanvas().getCanvasObjects().add(this);
        this.game.getPhysics().getKinetics().add(this);
        this.shared.getJFX().addEventReceiver(this);
    }

    /**
     * @param intervalS time difference between the frames, so changes in FPS would not affect velocity of shield
     *                  function changes shield's location based on left or right arrow keys pressed
     */
    public void update(Double intervalS) {
        Point.Double position = this.model.getPosition();

        // Figure out the positioning of the shield on the virtual 'rail' that runs along the center of the fort walls.
        double railMin = -(this.fort.getSize().width - (double) this.model.getSize().width);
        double railMax = this.fort.getSize().height - (double) this.model.getSize().height;

        this.model.railPosition += this.model.railSpeed * (this.fort.mirrorY ? -1 : 1) * intervalS;

        if (this.model.railPosition < railMin) {
            this.model.railPosition = railMin;
            this.model.railSpeed = 0;
        }
        else if (this.model.railPosition > railMax) {
            this.model.railPosition = railMax;
            this.model.railSpeed = 0;
        }

        double railMinMod = railMin * (this.fort.mirrorX ? 0 : 1);
        double railMaxMod = railMax * (this.fort.mirrorY ? 0 : 1);
        double railPositionModX = this.model.railPosition * (this.fort.mirrorX ? -1 : 1);
        double railPositionModY = this.model.railPosition * (this.fort.mirrorY ? -1 : 1);

        position.setLocation(
                this.model.railPosition < 0 ?
                        this.fort.getPosition().x - (railMinMod - railPositionModX) :
                        this.fort.getPosition().x - railMinMod,
                this.model.railPosition > 0 ?
                        this.fort.getPosition().y + (railMaxMod - railPositionModY) :
                        this.fort.getPosition().y + railMaxMod
        );
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
        switch(this.fort.player) {
            case 1:
                if (event.getCode() == KeyCode.Q) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        this.leftPressed = true;
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        this.leftPressed = false;
                    }
                }
                else if (event.getCode() == KeyCode.W) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        this.rightPressed = true;
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        this.rightPressed = false;
                    }
                }
                break;
            case 2:
                if (event.getCode() == KeyCode.V) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        this.leftPressed = true;
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        this.leftPressed = false;
                    }
                }
                else if (event.getCode() == KeyCode.B) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        this.rightPressed = true;
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        this.rightPressed = false;
                    }
                }
                break;
            case 3:
                if (event.getCode() == KeyCode.BRACELEFT) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        this.leftPressed = true;
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        this.leftPressed = false;
                    }
                }
                else if (event.getCode() == KeyCode.BRACERIGHT) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        this.rightPressed = true;
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        this.rightPressed = false;
                    }
                }
                break;
            case 4:
                if (event.getCode() == KeyCode.LEFT) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        this.leftPressed = true;
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        this.leftPressed = false;
                    }
                }
                else if (event.getCode() == KeyCode.RIGHT) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        this.rightPressed = true;
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        this.rightPressed = false;
                    }
                }
                break;
        }

        int newRailSpeed = 0;

        if (leftPressed) {
            newRailSpeed += -400;
        }
        if (rightPressed) {
            newRailSpeed += 400;
        }

        this.model.railSpeed = newRailSpeed;
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
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYPos(int y) {
        this.model.getPosition().y = y;
    };
}
