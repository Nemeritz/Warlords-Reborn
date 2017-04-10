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
import warlordstest.IWarlord;

import java.awt.*;


/**
 * Created by Hanliang Ding(Chris) on 28/03/17.
 */
public class WarlordComponent implements IWarlord, Physical, CanvasObject, Disposable, LooperChild {
    private SharedModule shared;
    private Image image;
    private Image ghostImage;
    private GameModule game;
    private FortService fort;
    private WarlordService model;

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

    public WarlordComponent(SharedModule shared, GameModule game, FortService fort) {
        this.shared = shared;
        this.game = game;
        this.fort = fort;
        this.setStyle();
        this.model = new WarlordService();
        this.game.getCanvas().getCanvasObjects().add(this);
        this.game.getPhysics().getStatics().add(this);
    }

    /**
     * @param intervalS Time since last update, in seconds.
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

            if (this.shared.getSettings().ghosting) {
                this.model.ghost = true;
            }

            this.game.getPhysics().getStatics().remove(this);

            BallComponent ball = (BallComponent) object;
            if (ball.getLastDeflectedBy() != null) {
                this.game.getScore().getScoreKeeper(ball.getLastDeflectedBy()).increaseScore(100);
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

    public void setWinner(Boolean value) {
        this.fort.winner = true;
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

    @Override
    public void dispose() {
        this.game.getCanvas().getCanvasObjects().remove(this);
        this.game.getPhysics().getStatics().remove(this);
    }
}