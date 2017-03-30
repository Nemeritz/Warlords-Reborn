package App.Game.Fort.Wall;

import warlordstest.IWall;

import java.awt.*;

/**
 * Created by pie on 28/03/17.
 */
public class WallService {
    private Point.Double position;
    private Point size;
    boolean destroyed;

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
}
