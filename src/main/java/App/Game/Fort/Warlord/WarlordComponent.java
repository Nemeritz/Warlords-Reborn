package App.Game.Fort.Warlord;

/**
 * Created by Hanliang Ding(Chris) on 30/03/17.
 */

import App.Game.Canvas.CanvasObject;
import App.Game.Fort.FortService;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import warlordstest.IWarlord;

import java.awt.*;


/**
 * Created by Hanliang Ding(Chris) on 28/03/17.
 */
public class WarlordComponent implements IWarlord, Physical, CanvasObject {
    private SharedModule shared;
    private Image image;
    private GameService game;
    private FortService fort;
    private WarlordService model;

    public WarlordComponent(SharedModule shared, GameService game, FortService fort) {
        this.shared = shared;
        this.game = game;
        this.fort = fort;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "WarlordComponent.png"
        );
        this.model = new WarlordService();
        this.game.getPhysics().getStatics().add(this);
    }

    /**
     * @param intervalS Time since last update, in seconds.
     */
    public void update(Double intervalS) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderOnContext(GraphicsContext context) {
        if (!this.fort.destroyed) {
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
        this.fort.destroyed = true;
        this.game.getPhysics().getStatics().remove(this);
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
}