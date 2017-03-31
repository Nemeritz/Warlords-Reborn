package App.Game.Fort.Shield;

import warlordstest.IPaddle;

import java.awt.*;

/**
 * Created by lichk on 30/03/2017.
 */
public class ShieldService {
    private Point.Double position;
    private Point velocity;
    private Point size;

    public ShieldService() {
        this.position = new Point.Double(0, 0);
        this.velocity = new Point(100, 100);
        this.size = new Point(100, 20);
    }

    public Point.Double getPosition() {
        return this.position;
    }

    public Point getVelocity() {
        return this.velocity;
    }

    public Point getSize() { return this.size; }
}
