package App.Game.Fort.Wall;

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
import warlordstest.IWall;

import java.awt.*;


/**
 * Created by Hanliang Ding(Chris) on 28/03/17.
 */
public class WallComponent implements IWall, Physical, CanvasObject, Disposable, LooperChild {
    private SharedModule shared;
    private Image image;
    private GameModule game;
    private FortService fort;
    private WallService model;
    private MediaPlayer hitSound;


    private void setStyle(Integer style) {
        if (style > 0 && style <= 7) {
            this.model.wallStyle = style;
            this.image = this.shared.getJFX().loadImage(
                    this.getClass(), "assets/wall-" + style.toString() + ".png"
            );
        }
    }

    /**
     * @param shared The inherited SharedModule.
     * @param game The inherited GameModule.
     * @param fort The fort model of the fort this object belongs to.
     * @param wallStyle The style of the wall (facing)
     */
    public WallComponent(SharedModule shared, GameModule game, FortService fort, Integer wallStyle) {
        this.shared = shared; // the module shared with all other components, allows access to scenes
        this.game = game; // allows access to other services
        this.fort = fort;
        this.model = new WallService(); // creates a new wall service when a wall is created
        this.hitSound = this.shared.getJFX().loadMedia(this.getClass(), "assets/impact.mp3");
        this.hitSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.setStyle(wallStyle);
        this.game.getCanvas().getCanvasObjects().add(this);
        this.game.getPhysics().getStatics().add(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderOnContext(GraphicsContext context) {
        if (!this.model.destroyed) {
            Point.Double position = this.model.getPosition(); // position of the image taken from WallComponentService
            Dimension size = this.model.getSize(); // size of the image taken from WallComponentService
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
    public void onGameLoop(Double intervalS) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object) {
        if (BallComponent.class.isInstance(object)) {
            this.model.destroyed = true;
            // Play the hit sound when the wall is hit.
            this.hitSound.stop();
            this.hitSound.setVolume(this.shared.getSettings().soundEffectsVolume);
            this.hitSound.play();
            this.game.getPhysics().getStatics().remove(this);

            BallComponent ball = (BallComponent) object;
            if (ball.getLastDeflectedBy() != null && ball.getLastDeflectedBy() != this.fort.player) {
                // Wall gives a score of 100 on destroy
                this.game.getScore().getScoreKeeper(ball.getLastDeflectedBy()).increaseScore(100);
            }
        }
    }

    public void setDestroyed (Boolean value) {
        this.model.destroyed = true;
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
    public boolean isDestroyed() {
        return this.model.destroyed;
    }

    public void dispose() {
        this.game.getCanvas().getCanvasObjects().remove(this);
        this.game.getPhysics().getStatics().remove(this);
    }
}
