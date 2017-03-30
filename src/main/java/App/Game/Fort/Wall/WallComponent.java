package App.Game.Fort.Wall;

import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import warlordstest.IWall;

import java.awt.*;


/**
 * Created by pie on 28/03/17.
 */
public class WallComponent implements IWall {
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
    }

    public void update(Double intervalS) {

    }

    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.model.getPosition();
        Point size = this.model.getSize();
        if (!this.isDestroyed()) {
            context.drawImage(this.image,
                    position.getX() - size.getX() / 2,
                    position.getY() - size.getY() / 2,
                    size.getX(), size.getY()
            );
        }
    }

    public void setDestroyed (Boolean value) {
        this.model.destroyed = true;
    }

    public Point.Double getPosition() {
        return this.model.getPosition();
    }

    public Point getSize() {
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
