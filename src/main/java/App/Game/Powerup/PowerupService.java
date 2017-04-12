package App.Game.Powerup;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * The model for powerup opjects.
 * Created by Jerry Fan on 9/04/2017.
 */
public class PowerupService {
    private Point.Double position;
    private Dimension size;
    public Power power;
    public double timeSinceSpawn;

    public PowerupService() {
        this.position = new Point2D.Double(0,0);
        this.size = new Dimension(34, 50);
        this.timeSinceSpawn = 0L;
        this.power = null;
    }

    /**
     * @return The position of the powerup.
     */
    public Point2D.Double getPosition() {
        return this.position;
    }

    /**
     * @return The dimensions of the powerup.
     */
    public Dimension getSize() {
        return this.size;
    }

    /**
     * @return The power that this gives.
     */
    public Power getPower() {
        return this.power;
    }

    /**
     * @param power The power to be transferred.
     */
    public void setPower(Power power) {
        this.power = power;
    }
}
