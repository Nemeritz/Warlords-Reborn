package App.Game.Ball;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;

/**
 * Created by lichk on 28/03/2017.
 */
public class BallService {
    private Point.Double position;
    private Vec2d velocity;
    private Dimension size;
    public int lastDeflectedBy; // Last player to hit the ball

    public BallService() {
        this.position = new Point.Double(0, 0);
        this.velocity = new Vec2d(10, 10);
        this.size = new Dimension(20, 20);
    }

    public Point.Double getPosition() {
        return this.position;
    }

    public Vec2d getVelocity() {
        return this.velocity;
    }

    public Dimension getSize() { return this.size; }
}
