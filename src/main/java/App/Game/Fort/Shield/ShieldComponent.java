package App.Game.Fort.Shield;

import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasObject;
import App.Game.Fort.FortService;
import App.Game.GameModule;
import App.Game.Loop.LooperChild;
import App.Game.Physics.Physical;
import App.Shared.Interfaces.Disposable;
import App.Shared.JFX.EventReceiver;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import warlordstest.IPaddle;

import java.awt.*;

/**
 * Created by Jerry Fan on 30/03/2017.
 */
public class ShieldComponent implements IPaddle, Physical, CanvasObject, EventReceiver, Disposable, LooperChild {
    private SharedModule shared;
    private GameModule game;
    private FortService fort;
    private Image image;
    private ShieldService model;
    private boolean leftPressed; // indicates if left arrow is pressed
    private boolean rightPressed; // indicates if right arrow is pressed
    private MediaPlayer hitSound;

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
    public ShieldComponent(SharedModule shared, GameModule game, FortService fort) {
        this.leftPressed = false; // shield stays still at the start
        this.rightPressed = false;
        this.shared = shared; // allows access to JFX current scene for adding event handlers
        this.game = game; // allows access to other services in the game
        this.fort = fort; // Allows access to fort services.
        this.hitSound = this.shared.getJFX().loadMedia(this.getClass(), "assets/impact.mp3");
        this.hitSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.setStyle();
        this.model = new ShieldService(); // accessing velocity, dimensions and locations of shield
        this.game.getCanvas().getCanvasObjects().add(this);
        this.game.getPhysics().getStatics().add(this);
        this.shared.getJFX().getEventReceivers().add(this);
    }

    /**
     * @param intervalS time difference between the frames, so changes in FPS would not affect velocity of shield
     *                  function changes shield's location based on left or right arrow keys pressed
     */
    public void onGameLoop(Double intervalS) {
        if (!this.fort.destroyed || this.shared.getSettings().ghosting) {
            if (this.model.stunned) {
                if (this.game.gameTime - this.model.lastStunned > 0.5) {
                    this.model.stunned = false;
                }
            } else {

                Point.Double position = this.model.getPosition();

                // Figure out the positioning of the shield on the virtual 'rail' that runs along the center of the fort walls.
                double railMin = -(this.fort.getSize().width - (double) this.model.getSize().width);
                double railMax = this.fort.getSize().height - (double) this.model.getSize().height;

                this.model.railPosition += this.model.railSpeed * (this.fort.mirrorX ? -1 : 1) * intervalS;

                if (this.model.railPosition < railMin) {
                    this.model.railPosition = railMin;
                    this.model.railSpeed = 0;
                } else if (this.model.railPosition > railMax) {
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
        }
        else {
            this.game.getPhysics().getStatics().remove(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderOnContext(GraphicsContext context) {
        if (!this.fort.destroyed || this.shared.getSettings().ghosting) {
            Point.Double position = this.model.getPosition();
            Dimension size = this.model.getSize();
            context.drawImage(this.image,
                    position.x,
                    position.y,
                    size.width, size.height
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object) {
        if (!this.fort.destroyed) {
            if (BallComponent.class.isInstance(object)) {
                BallComponent ball = (BallComponent) object;
                this.hitSound.stop();
                this.hitSound.play();
                ball.setLastDeflectedBy(fort.player);
                this.model.stunned = true;
                this.model.lastStunned = this.game.gameTime;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyEvent(KeyEvent event) {
        if (!this.fort.aiControlled) {
            switch (this.fort.player) {
                case 1:
                    if (event.getCode() == KeyCode.Q) {
                        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                            this.leftPressed = true;
                        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                            this.leftPressed = false;
                        }
                    } else if (event.getCode() == KeyCode.W) {
                        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                            this.rightPressed = true;
                        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                            this.rightPressed = false;
                        }
                    }
                    break;
                case 2:
                    if (event.getCode() == KeyCode.C) {
                        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                            this.leftPressed = true;
                        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                            this.leftPressed = false;
                        }
                    } else if (event.getCode() == KeyCode.V) {
                        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                            this.rightPressed = true;
                        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                            this.rightPressed = false;
                        }
                    }
                    break;
                case 3:
                    if (event.getCode() == KeyCode.U) {
                        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                            this.leftPressed = true;
                        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                            this.leftPressed = false;
                        }
                    } else if (event.getCode() == KeyCode.I) {
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
                    } else if (event.getCode() == KeyCode.RIGHT) {
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point.Double getPosition() {
        return this.model.getPosition();
    }

    public double getRailSpeed() {
        return this.model.railSpeed;
    }

    public void setRailSpeed(double value) {
        this.model.railSpeed = value;
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

    public void dispose() {
        this.game.getCanvas().getCanvasObjects().remove(this);
        this.game.getPhysics().getStatics().remove(this);
        this.shared.getJFX().getEventReceivers().remove(this);
    }
}
