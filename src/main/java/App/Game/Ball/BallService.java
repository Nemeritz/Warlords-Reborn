package App.Game.Ball;

import warlordstest.IBall;

import java.awt.*;

/**
 * Created by lichk on 28/03/2017.
 */
public class BallService {
    private Point.Double position;
    private Point velocity;
    private Dimension size;

    public BallService() {
        this.position = new Point.Double(0, 0);
        this.velocity = new Point(10, 10);
        this.size = new Dimension(20, 20);
    }

    public Point.Double getPosition() {
        return this.position;
    }

    public Point getVelocity() {
        return this.velocity;
    }

    public Dimension getSize() { return this.size; }
}
