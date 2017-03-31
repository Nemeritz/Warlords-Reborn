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


    public WallComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "WallComponent.png"
        );
        this.model = new WallService();

        this.game.getPhysics().getStatics().add(this);
    }

    public void update(Double intervalS) {

    }

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

    @Override
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object) {
        this.model.destroyed = true;
        this.game.getPhysics().getStatics().remove(this);
    }

    public void setDestroyed (Boolean value) {
        this.model.destroyed = true;
    }

    public Point.Double getPosition() {
        return this.model.getPosition();
    }

    public Dimension getSize() {
        return this.model.getSize();
    }

    public void setXPos(int x) {
        this.model.getPosition().x = x;
    }

    public void setYPos(int y) {
        this.model.getPosition().y = y;
    }

    public boolean isDestroyed() {
        return this.model.destroyed;
    }
}
