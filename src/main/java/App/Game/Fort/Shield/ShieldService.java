package App.Game.Fort.Shield;

import java.awt.*;

/**
 * Created by Jerry Fan on 30/03/2017.
 */
public class ShieldService {
    private Point.Double position;
    private Point velocity;
    private Dimension size;
    /**
     * Constructor for ShieldService, initializes size, dimension and destroyed boolean to false
     */
    public ShieldService() {
        this.position = new Point.Double(0, 0);
        this.velocity = new Point(100, 100);
        this.size = new Dimension(72, 72);
    }

    /**
     * @return position of the Shield containing x,y coordinates, 0,0 being top left
     */
    public Point.Double getPosition() {
        return this.position;
    }

    /**
     * @return velocity of the shield, contains x and y velocities
     */
    public Point getVelocity() {
        return this.velocity;
    }

    /**
     * @return dimensions of the shield, containing width and height of the shield
     */
    public Dimension getSize() { return this.size; }
}
