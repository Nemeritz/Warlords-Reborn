package App.Game.Fort.Warlord;

/**
 * Created by pie on 30/03/17.
 */

import App.Game.Canvas.CanvasObject;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import warlordstest.IWarlord;

import java.awt.*;


/**
 * Created by pie on 28/03/17.
 */
public class WarlordComponent implements IWarlord, Physical, CanvasObject {
    private SharedModule shared;
    private Image image;
    private GameService game;
    private WarlordService model;

    public WarlordComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "WarlordComponent.png"
        );
        this.model = new WarlordService();
        this.game.getPhysics().getStatics().add(this);
    }

    public void update(Double intervalS) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderOnContext(GraphicsContext context) {
        if (!this.model.destroyed) {
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
        this.model.destroyed = true;
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

    public void setWon(Boolean value) {
        this.model.won = true;
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
        return this.model.won;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDead(){
        return this.model.destroyed;
    }
}