package App.Game.Fort.Wall;

import warlordstest.IWall;

import java.awt.*;

/**
 * Created by pie on 28/03/17.
 */
public class WallService implements IWall{
    private Point.Double position;
    private Point size;
    private Boolean destroyed;

    public WallService() {
        this.position = new Point.Double(0,0);
        this.destroyed = false;
        this.size = new Point(50,50);
    }

    public Point.Double getPosition() {
        return this.position;
    }

    public Point getSize() {
        return this.size;
    }

    public void setDestroyed (Boolean value) {
        if (!value.equals(this.destroyed)) {
            this.destroyed = value;
        }
    }

    public void setXPos(int x) {
        this.position.x = x;
    }

    public void setYPos(int y) {
        this.position.y = y;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }
}
