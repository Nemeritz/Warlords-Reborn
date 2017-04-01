package App.Game.Ball;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;

/**
 * Created by Jerry Fan on 28/03/2017.
 */
public class BallService {
    private Point.Double position;
    private Vec2d velocity;
    private Dimension size;
    public int lastDeflectedBy; // Last player to hit the ball

    public BallService() {
        this.position = new Point.Double(0, 0);
        this.velocity = new Vec2d(0, 0);
        this.size = new Dimension(20, 20);
    }

    /**
     * @return position of the ball with x,y coords, 0,0 being top left
     */
    public Point.Double getPosition() {
        return this.position;
    }

    /**
     * @return velocity of the ball with x and y components
     */
    public Vec2d getVelocity() {
        return this.velocity;
    }

    /**
     * @return size of the ball with width and height
     */
    public Dimension getSize() { return this.size; }
}
