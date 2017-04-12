package App.Game.Fort.Wall;

import java.awt.*;

/**
 * Model for the wall.
 * Created by Hanliang Ding(Chris) on 28/03/17.
 */
public class WallService {
    private Point.Double position;
    private Dimension size;
    boolean destroyed; // variable showing whether the wall has collided with ball or not
    int wallStyle;

    /**
     * Constructor for WallService, initializes size, dimension and destroyed boolean to false
     */
    public WallService() {
        this.position = new Point.Double(0,0);
        this.destroyed = false;
        this.size = new Dimension(36,36);
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
