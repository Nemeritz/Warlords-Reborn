package App.Game.Fort.Wall;

import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasObject;
import App.Game.Fort.FortService;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import warlordstest.IWall;

import java.awt.*;


/**
 * Created by Hanliang Ding(Chris) on 28/03/17.
 */
public class WallComponent implements IWall, Physical, CanvasObject {
    private SharedModule shared;
    private Image image;
    private GameService game;
    private FortService fort;
    private WallService model;


    private void setStyle(Integer style) {
        if (style > 0 && style <= 7) {
            this.model.wallStyle = style;
            this.image = this.shared.getJFX().loadImage(
                    this.getClass(), "assets/wall-" + style.toString() + ".png"
            );
        }
    }

    /**
     * Contructor for Wall
     * @param shared shared module controlling all scenes
     * @param game current game containing all services
     */
    public WallComponent(SharedModule shared, GameService game, FortService fort, Integer wallStyle) {
        this.shared = shared; // the module shared with all other components, allows access to scenes
        this.game = game; // allows access to other services
        this.fort = fort;
        this.model = new WallService(); // creates a new wall service when a wall is created
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
     * @param intervalS Time since last update, in seconds.
     */
    public void update (Double intervalS) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object) {
        if (BallComponent.class.isInstance(object)) {
            this.model.destroyed = true;
            this.game.getPhysics().getStatics().remove(this);
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
}
