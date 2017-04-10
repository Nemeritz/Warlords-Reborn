package App.Game.Powerup;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by lichk on 9/04/2017.
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

    public Point2D.Double getPosition() {
        return this.position;
    }

    public Dimension getSize() {
        return this.size;
    }

    public Power getPower() {
        return this.power;
    }

    public void setPower(Power power) {
        this.power = power;
    }
}
