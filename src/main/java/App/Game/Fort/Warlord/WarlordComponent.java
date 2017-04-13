package App.Game.Fort.Warlord;

/**
 * Created by Hanliang Ding(Chris) on 30/03/17.
 */

import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasObject;
import App.Game.Fort.FortService;
import App.Game.GameModule;
import App.Game.Loop.LooperChild;
import App.Game.Physics.Physical;
import App.Shared.Interfaces.Disposable;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import warlordstest.IWarlord;

import java.awt.*;


/**
 * The class for the warlord of player forts.
 * Created by Hanliang Ding(Chris) on 28/03/17.
 */
public class WarlordComponent implements IWarlord, Physical, CanvasObject, Disposable, LooperChild {
    private SharedModule shared;
    private Image image;
    private Image ghostImage;
    private GameModule game;
    private FortService fort;
    private WarlordService model;
    private MediaPlayer hitSound;

    private void setStyle() {
        if (this.fort.player > 0 && this.fort.player <= 4) {
            this.image = this.shared.getJFX().loadImage(
                    this.getClass(), "assets/warlord-" + Integer.toString(this.fort.player) + ".png"
            );
            this.ghostImage = this.shared.getJFX().loadImage(
                    this.getClass(), "assets/warlord-" + Integer.toString(this.fort.player) +
                            "-ghost.png"
            );
        }
    }

    /**
     * @param shared Inherited shared module.
     * @param game Inherited game module.
     * @param fort The fort model of the fort the object belongs to.
     */
    public WarlordComponent(SharedModule shared, GameModule game, FortService fort) {
        this.shared = shared;
        this.game = game;
        this.fort = fort;
        this.setStyle();
        this.hitSound = this.shared.getJFX().loadMedia(this.getClass(), "assets/death.mp3");
        this.model = new WarlordService();
        this.game.getCanvas().getCanvasObjects().add(this);
        this.game.getPhysics().getStatics().add(this);
    }

    /**
     * {@inheritDoc}
     */
    public void onGameLoop(Double intervalS) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.model.getPosition();
        Dimension size = this.model.getSize();
        Image image = null;

        if (!this.fort.destroyed) {
            image = this.image;
        }
        else if (this.model.ghost) {
            image = this.ghostImage;
        }
        
        if (image != null) {
            context.drawImage(image,
                    position.x, position.y,
                    size.width, size.height
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object) {
        if (BallComponent.class.isInstance(object)) {
            this.fort.destroyed = true;
            // Play sound on hit
            if (this.hitSound != null) {
                this.hitSound.stop();
                this.hitSound.setVolume(this.shared.getSettings().soundEffectsVolume);
                this.hitSound.play();
            }

            if (this.shared.getSettings().ghosting) {
                // Set to ghost
                this.model.ghost = true;
            }

            this.game.getPhysics().getStatics().remove(this); // Remove collisions in either case

            BallComponent ball = (BallComponent) object;
            if (ball.getLastDeflectedBy() != null) {
                if (ball.getLastDeflectedBy() != this.fort.player) {
                    // Hitting the warlord gives 1000 points.
                    this.game.getScore().getScoreKeeper(ball.getLastDeflectedBy()).increaseScore(1000);
                }
                else if (!this.model.ghost) {
                    // Can't score if no ghosting.
                    ball.setLastDeflectedBy(null);
                }
            }
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
     * {@inheritDoc}
     */
    @Override
    public Dimension getSize() {
        return this.model.getSize();
    }

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
    public boolean hasWon(){
        return this.fort.winner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDead(){
        return this.fort.destroyed;
    }

    public boolean isGhost() {
        return this.model.ghost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        this.game.getCanvas().getCanvasObjects().remove(this);
        this.game.getPhysics().getStatics().remove(this);
    }
}