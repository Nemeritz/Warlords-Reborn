package App.Game.Physics;

import java.awt.*;

/**
 * Created by Jerry Fan on 31/03/2017.
 */
public interface Physical {
    public Point.Double getPosition();

    /**
     * @return the dimensions of the object containing width and height in units
     */
    public Dimension getSize();

    /**
     * @param hitBoxCenter center of the hit box for the object the function is in
     * @param intersectionCenter the location of intersection between the ball and the
     * @param object The object which the function is in
     */
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object);
}
