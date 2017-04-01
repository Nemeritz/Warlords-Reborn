package App.Game.Fort.Wall;

import warlordstest.IWall;

import java.awt.*;

/**
 * Created by pie on 28/03/17.
 */
public class WallService {
    private Point.Double position;
    private Dimension size;
    boolean destroyed; // variable showing whether the wall has collided with ball or not

    /**
     * Constructor for WallService, initializes size, dimension and destroyed boolean to false
     */
    public WallService() {
        this.position = new Point.Double(0,0);
        this.destroyed = false;
        this.size = new Dimension(50,50);
    }

    /**
     * @return Double position of the wall created, containing x and y coordinates 0,0 being top left
     */
    public Point.Double getPosition() {
        return this.position;
    }

    /**
     * @return Dimension size of the wall created, containing width and height of the wall.
     */
    public Dimension getSize() {
        return this.size;
    }
}
