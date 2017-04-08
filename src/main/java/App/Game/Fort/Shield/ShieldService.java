package App.Game.Fort.Shield;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;

/**
 * Created by Jerry Fan on 30/03/2017.
 */
public class ShieldService {
    public double railPosition;
    public double railSpeed;
    private Point.Double position;
    private Vec2d velocity;
    private Dimension size;
    /**
     * Constructor for ShieldService, initializes size, dimension and destroyed boolean to false
     */
    public ShieldService() {
        this.railPosition = 0;
        this.railSpeed = 0;
        this.position = new Point.Double(0, 0);
        this.velocity = new Vec2d(0, 0);
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
    public Vec2d getVelocity() {
        return this.velocity;
    }

    /**
     * @return dimensions of the shield, containing width and height of the shield
     */
    public Dimension getSize() { return this.size; }
}
