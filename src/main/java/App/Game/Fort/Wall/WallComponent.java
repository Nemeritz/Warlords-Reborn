package App.Game.Fort.Wall;

import App.Game.Canvas.CanvasObject;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import warlordstest.IWall;

import java.awt.*;


/**
 * Created by pie on 28/03/17.
 */
public class WallComponent implements IWall, Physical, CanvasObject {
    private SharedModule shared;
    private Image image;
    private GameService game;
    private WallService model;


    /**
     * Contructor for Wall
     * @param shared shared module controlling all scenes
     * @param game current game containing all services
     */
    public WallComponent(SharedModule shared, GameService game) {
        this.shared = shared;//the module shared with all other components, allows access to scenes
        this.game = game;//allows access to other services
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "WallComponent.png"
        );
        this.model = new WallService();//creates a new wall service when a wall is created
        this.model = new WallService();

        this.game.getPhysics().getStatics().add(this);
    }

    /**
     * @param context renders the WallComponent.png on the game 2D graphics context in the canvas
     */
    public void renderOnContext(GraphicsContext context) {
        if (!this.model.destroyed) {
            Point.Double position = this.model.getPosition();
            Dimension size = this.model.getSize();
        Point.Double position = this.model.getPosition();//position of the image taken from WallComponentService
        Dimension size = this.model.getSize();//size of the image taken from WallComponentService
        if (!this.isDestroyed()) {
            context.drawImage(this.image,
                    position.x,
                    position.y,
                    size.width, size.height
            );
        }
    }


    /**
     * checks for collisons and set to destroy wall if collision is detected
     */
    public void update () {

    }

    /**
     * @param value set the destroyed variable, true if the model is to be destroyed
     */
    @Override
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object) {
        this.model.destroyed = true;
        this.game.getPhysics().getStatics().remove(this);
    }

    public void setDestroyed (Boolean value) {
        this.model.destroyed = true;
    }

    /**
     * @return the Double position of the wall which contains the x and y coordinate
     */
    public Point.Double getPosition() {
        return this.model.getPosition();
    }

    /**
     * @return the Dimension size of the wall which contains the height and width of the wall
     */
    public Dimension getSize() {
        return this.model.getSize();
    }

    /**
     * @param x Sets the x axis position of the wall in the game, 0,0 being top left
     */
    public void setXPos(int x) {
        this.model.getPosition().x = x;
    }

    /**
     * @param y Sets the y axis position of the wall in the game, 0,0 being top left
     */
    public void setYPos(int y) {
        this.model.getPosition().y = y;
    }

    /**
     * @return whether the wall is destroyed, true if collision with ball, false if not
     */
    public boolean isDestroyed() {
        return this.model.destroyed;
    }
}
